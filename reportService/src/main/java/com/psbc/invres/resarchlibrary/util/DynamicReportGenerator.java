package com.psbc.invres.resarchlibrary.util;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.psbc.invres.resarchlibrary.mapper.doris.DynamicReportMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 动态多维报表生成器（多 config 版本）
 *
 * 关键点（方便后续维护时快速回忆整体设计）：
 * - 输入是一个 JSON config，包含：
 *   leftDimensions：左侧维度树（行标题）
 *   topDimensions ：顶部维度树（列标题）
 *   metrics       ：数据指标（每个叶子列下有多个指标，如"笔数 / 规模"）
 *   globalParams  ：全局参数，所有 SQL 内的 #{xxx} 都可以用这里的值替换
 *   dataHeaderRowIndex：指标行（metrics displayName）的表头行号（0-based）
 *
 * - 左侧维度：
 *   第 1 个 leftDimension 决定总行数（可以返回 N 行）；
 *   后续 leftDimension 对每个"父行"最多只能返回 0 或 1 行，超出即抛异常，避免行数错乱。
 *   每层查询可以 SELECT 多个字段（如 coop_name1, coop_name2, coop_name3），
 *   最终只展示"最后一个非空字段"，并在前面加 (列序号-1) 个 "  " 作为缩进前缀。
 *
 * - 顶部维度：
 *   递归执行 topDimensions，生成 rawTopHeaders（每一行对应一维）和 topPaths（每个叶子列的参数组合）。
 *
 * - 表头构建：
 *   把左侧 displayName、顶部 rawTopHeaders 和 metrics.displayName 按 dataHeaderRowIndex 合成最终 headLists，
 *   然后对表头做"向下填充"，保证每列在所有表头行都有非空值，便于后续合并单元格。
 *
 * - 数据填充：
 *   遍历每个左侧行（LeftRowContext）和每个顶部路径（topPath），
 *   对每个 metric 组合参数，使用 SqlBuilder 构建最终 SQL 并查询 value。
 *
 * - SQL 构建：
 *   SqlBuilder.build                    ：普通占位符替换（用于维度 SQL）
 *   SqlBuilder.buildDataSqlWithEmptyParamHandling：
 *      数据 SQL 专用，占位符没有值时把 "xxx = #{param}" / "#{param} = xxx" 替换为 "1 = 1"，
 *      然后清理多余 AND/OR/WHERE，避免拼接出非法 SQL。
 */
@Slf4j
@Component
public class DynamicReportGenerator {

    private final DynamicReportMapper dynamicReportMapper;

    public DynamicReportGenerator(DynamicReportMapper dynamicReportMapper) {
        this.dynamicReportMapper = dynamicReportMapper;
    }

    /**
     * 单个 config 的主入口：
     * - 解析 JSON
     * - 构建左侧维度、顶部维度、表头和数据
     * - 返回导出 Excel 所需的 headLists / dataLists 以及合并信息
     */
    public Map<String, Object> generateReportData(String configJson) throws Exception {
        JSONObject config = JSONObject.parseObject(configJson);
        JSONArray leftDims = config.getJSONArray("leftDimensions");
        JSONArray topDims = config.getJSONArray("topDimensions");
        JSONArray metrics = config.getJSONArray("metrics");

        // 全局参数（完全自定义：可在任意 SQL 中使用 #{param}，比如 startDate / endDate / branch）
        Map<String, String> baseParams = new HashMap<>();
        JSONObject globalParams = config.getJSONObject("globalParams");
        if (globalParams != null && !globalParams.isEmpty()) {
            for (Map.Entry<String, Object> e : globalParams.entrySet()) {
                if (e.getKey() == null) {
                    continue;
                }
                Object v = e.getValue();
                if (v == null) {
                    continue;
                }
                String s = String.valueOf(v);
                if (StringUtils.isNotBlank(s)) {
                    baseParams.put(e.getKey(), s);
                }
            }
        }

        int leftLevels = leftDims.size();
        int metricsCount = metrics.size();
        List<JSONObject> metricList = metrics.toJavaList(JSONObject.class);

        // 调试日志：打印传入的 branch 参数
        String branchInParams = baseParams.get("branch");
        log.info("[DynamicReportGenerator] 开始生成报表，branch={}, leftLevels={}, metricsCount={}",
                branchInParams, leftLevels, metricsCount);

        // 请求内局部缓存：同一分行的左侧维度数据只查询一次
        Map<String, List<LeftRowContext>> requestCache = new HashMap<>();

        // 1. 构建左侧维度数据（使用请求内缓存，同一分行的左侧数据只查询一次）
        //    - 第一列允许多行，决定最终数据行数
        //    - 第二列及之后：每个"父节点"至多 0 或 1 行，超出即抛异常
        List<LeftRowContext> leftRows = buildLeftDataWithCache(leftDims, baseParams, branchInParams, requestCache);
        log.info("[DynamicReportGenerator] branch={} 构建左侧数据完成，leftRows.size={}", branchInParams, leftRows.size());

        // 2. 构建顶部维度表头和路径（合并查询，避免重复执行维度 SQL）
        TopDimensionsResult topResult = buildTopDimensionsWithMerge(topDims, new HashMap<>(baseParams));
        List<List<String>> rawTopHeaders = topResult.headers;
        if (rawTopHeaders.isEmpty()) {
            rawTopHeaders.add(new ArrayList<>());
        }
        int leafCount = rawTopHeaders.get(rawTopHeaders.size() - 1).size();
        List<Map<String, String>> topPaths = topResult.paths;

        // 指定"数据列（指标）表头所在行"（0-based）。
        // 未配置时，默认 = rawTopHeaders.size()（即顶部维度行数之后的下一行）
        int configuredDataHeaderRowIndex = config.getIntValue("dataHeaderRowIndex");
        int dataHeaderRowIndex = configuredDataHeaderRowIndex > 0 ? configuredDataHeaderRowIndex : rawTopHeaders.size();
        // 为避免丢失顶部维度行，至少要 >= rawTopHeaders.size()
        if (dataHeaderRowIndex < rawTopHeaders.size()) {
            dataHeaderRowIndex = rawTopHeaders.size();
        }

        // 左侧 displayName 填充到数据列表头行（包含该行）
        int displayNameFillRows = dataHeaderRowIndex + 1;

        // 3. 组装表头：左侧 displayName + 顶部维度值 + 指标行
        int dataCols = leafCount * metricsCount;
        int totalCols = leftLevels + dataCols;
        List<List<String>> finalHeads = new ArrayList<>();

        int totalHeaderRows = dataHeaderRowIndex + 1; // 指标行固定在 dataHeaderRowIndex

        for (int i = 0; i < totalHeaderRows; i++) {
            List<String> row = new ArrayList<>(totalCols);

            // 填充左侧维度列
            for (int j = 0; j < leftLevels; j++) {
                if (i < displayNameFillRows) {
                    row.add(leftDims.getJSONObject(j).getString("displayName"));
                } else {
                    row.add("");
                }
            }

            // 填充顶部维度列
            if (i < dataHeaderRowIndex) {
                List<String> topRowCells;
                if (i < rawTopHeaders.size()) {
                    topRowCells = rawTopHeaders.get(i);
                } else {
                    // 需要补齐的"空行"：先填空，后续会用"上方非空值"向下填充
                    topRowCells = new ArrayList<>(Collections.nCopies(leafCount, ""));
                }
                for (String cell : topRowCells) {
                    for (int k = 0; k < metricsCount; k++) {
                        row.add(cell);
                    }
                }
            } else { // i == dataHeaderRowIndex -> 指标行
                for (int k = 0; k < leafCount; k++) {
                    for (JSONObject m : metricList) {
                        row.add(m.getString("displayName"));
                    }
                }
            }
            finalHeads.add(row);
        }

        // 3.1 表头空白单元格向下填充：
        //     方便后续做"整块合并单元格"，避免出现中间有空洞的标题
        for (int col = 0; col < totalCols; col++) {
            String lastNonBlank = null;
            for (int r = 0; r < finalHeads.size(); r++) {
                String cur = finalHeads.get(r).get(col);
                if (cur != null && !cur.trim().isEmpty()) {
                    lastNonBlank = cur;
                } else if (lastNonBlank != null) {
                    finalHeads.get(r).set(col, lastNonBlank);
                }
            }
        }

        // 4. 组装数据行：左侧维度值 + 右侧每个 (topPath, metric) 的查询结果
        // 4.1 可选：批量 metrics 查询（强烈建议用于 preview/export 提速）
        //     配置方式：在 metrics 的每个元素里增加：
        //     - batchQuerySql：一次性查全的 SQL（需要返回 keyCols + value 列）
        //     - keyCols     ：用于定位单元格的字段名数组（必须与 SQL 返回列一致）
        //     - valueColumn ：（可选）值列名，默认 "value"
        //
        //     批量模式能把"逐格查询（几千次）"降为"每个 metric 1 次查询"。
        Map<Integer, Map<String, String>> metricBatchCache = buildMetricBatchCache(metricList, baseParams);
        List<List<String>> finalData = new ArrayList<>();
        List<String> prevLeftDisplayValues = null;
        for (int lrIdx = 0; lrIdx < leftRows.size(); lrIdx++) {
            LeftRowContext lrCtx = leftRows.get(lrIdx);
            // 初始化数据行，包含左侧维度值和右侧数据区的占位符
            List<String> dRow = new ArrayList<>(Collections.nCopies(totalCols, ""));

            // 设置左侧维度值
            List<String> leftDisplayValues = lrCtx.getDisplayValues();
            if (leftDisplayValues != null && !leftDisplayValues.isEmpty()) {
                int firstDiff = 0;
                if (prevLeftDisplayValues != null && !prevLeftDisplayValues.isEmpty()) {
                    int min = Math.min(prevLeftDisplayValues.size(), leftDisplayValues.size());
                    while (firstDiff < min) {
                        String prev = prevLeftDisplayValues.get(firstDiff);
                        String cur = leftDisplayValues.get(firstDiff);
                        if (Objects.equals(prev, cur)) {
                            firstDiff++;
                        } else {
                            break;
                        }
                    }
                }

                for (int i = 0; i < leftDisplayValues.size(); i++) {
                    String cellVal;
                    if (prevLeftDisplayValues != null && i < firstDiff) {
                        // 多级展示：前面重复的层级用两个空格占位，只展示变化的最后层级
                        cellVal = "  ";
                    } else {
                        cellVal = leftDisplayValues.get(i);
                    }
                    dRow.set(i, (cellVal == null) ? "" : cellVal);
                }
            }

            // 填充右侧数据区
            int dataStartCol = leftLevels;
            for (int pIdx = 0; pIdx < topPaths.size(); pIdx++) {
                Map<String, String> topPath = topPaths.get(pIdx);
                for (int mIdx = 0; mIdx < metricList.size(); mIdx++) {
                    JSONObject metric = metricList.get(mIdx);

                    String sqlTemplate = metric.getString("querySql");
                    Map<String, String> queryParams = new HashMap<>();
                    queryParams.putAll(lrCtx.getParamValues());
                    queryParams.putAll(topPath);

                    String value = "-";
                    // 优先走批量缓存：命中则 O(1) 取值；未配置批量则回退到逐格 SQL
                    Map<String, String> batchMap = metricBatchCache.get(mIdx);
                    if (batchMap != null) {
                        String cellKey = buildCellKey(metric, queryParams);
                        if (StringUtils.isNotBlank(cellKey)) {
                            String v = batchMap.get(cellKey);
                            if (v != null) {
                                value = v;
                            }
                        }
                    } else {
                        String finalSql = SqlBuilder.buildDataSqlWithEmptyParamHandling(sqlTemplate, queryParams);
                        try {
                            List<Map<String, String>> queryResult = executeQuery(finalSql);
                            if (!queryResult.isEmpty() && queryResult.get(0).containsKey("value")) {
                                value = queryResult.get(0).get("value");
                            }
                        } catch (Exception e) {
                            log.error("数据查询失败，SQL: " + finalSql, e);
                        }
                    }
                    int absoluteColIndex = dataStartCol + pIdx * metricsCount + mIdx;
                    dRow.set(absoluteColIndex, value);
                }
            }
            finalData.add(dRow);
            prevLeftDisplayValues = leftDisplayValues;
        }

        Map<String, Object> res = new HashMap<>();
        res.put("headLists", finalHeads);
        res.put("dataLists", finalData);
        // 需要横向合并的表头行：按 topDimensions 的层级数合并（不包含最后的指标行）
        List<Integer> mergeRows = new ArrayList<>();
        int topMergeRowCount = (topDims == null) ? 0 : topDims.size();
        for (int i = 0; i < topMergeRowCount; i++) {
            mergeRows.add(i);
        }
        // 需要纵向合并的左侧列：按 leftDimensions 的列数合并
        List<Integer> mergeColumns = new ArrayList<>();
        for (int i = 0; i < leftLevels; i++) {
            mergeColumns.add(i);
        }

        res.put("mergeRows", mergeRows);
        res.put("mergeColumns", mergeColumns);
        res.put("topDimCount", topMergeRowCount);
        res.put("leftDimCount", leftLevels);
        return res;
    }

    /**
     * 左侧一行的上下文：
     * - displayValues：这一行在左侧每一列要展示的文字
     * - paramValues  ：这一行对应的参数集合（会参与后续 metrics 的 SQL 拼接）
     */
    static class LeftRowContext {

        List<String> displayValues = new ArrayList<>();
        Map<String, String> paramValues = new HashMap<>();

        public List<String> getDisplayValues() {
            return displayValues;
        }

        public Map<String, String> getParamValues() {
            return paramValues;
        }
    }

    // ================= 核心构建逻辑 =================

    private List<LeftRowContext> buildLeftData(JSONArray dims, Map<String, String> params, boolean isFirstDim) throws Exception {
        if (dims == null || dims.isEmpty()) {
            LeftRowContext ctx = new LeftRowContext();
            ctx.paramValues.putAll(params);
            return Collections.singletonList(ctx);
        }

        JSONObject currentDimConfig = dims.getJSONObject(0);
        if (currentDimConfig == null) {
            throw new IllegalArgumentException("leftDimensions 配置错误：第 1 个元素不能为空，且必须是一个 JSON 对象。");
        }

        String fieldName = currentDimConfig.getString("fieldName");
        if (StringUtils.isBlank(fieldName)) {
            throw new IllegalArgumentException("leftDimensions 配置错误：字段 fieldName 不能为空，请检查第一个维度配置。");
        }

        JSONArray next = (dims.size() > 1) ? new JSONArray(dims.subList(1, dims.size())) : new JSONArray();
        // 使用"智能数据 SQL 构建"，这样当可选参数（如 coop_name2/3）为空时，
        // 像 "AND coop_name2 = #{coop_name2}" 这种条件会自动被替换成 "1 = 1"，避免 SQL 语法错误。
        String sql = SqlBuilder.buildDataSqlWithEmptyParamHandling(currentDimConfig.getString("querySql"), params);
        if (StringUtils.isBlank(sql)) {
            return new ArrayList<>();
        }

        List<Map<String, String>> rows = executeQuery(sql);

        // 规则：整个 Excel 的行数由第一列（第一个 leftDimension）的查询结果决定。
        // 所以从第二列（及之后）开始，每个父节点下只能返回 0 或 1 条记录，若 >1 说明"该列行数会超过第一列" -> 抛错。
        if (!isFirstDim && rows.size() > 1) {
            String displayName = currentDimConfig.getString("displayName");
            String colName = StringUtils.isNotBlank(displayName) ? displayName : fieldName;
            throw new IllegalStateException("左侧维度列【" + colName + "】查询结果行数为 " + rows.size()
                    + "，应为 0 或 1，且必须与第一列的行数保持一致，请检查该列的 SQL 配置。");
        }

        List<LeftRowContext> result = new ArrayList<>();

        // ========== 优化：第二维度及之后使用批量查询 ==========
        // 只有当有后续维度时，才进入批量查询方法
        // buildLeftDataWithBatchQuery 内部会处理 next 的计算
        if (!next.isEmpty()) {
            // 将第一维度 + 后续维度一起传入
            return buildLeftDataWithBatchQuery(rows, dims, params);
        }

        // 无后续维度，按原有逻辑处理
        for (Map<String, String> row : rows) {
            String displayVal = buildIndentedLastNonEmptyDisplayValue(row);

            Map<String, String> nextParams = new HashMap<>(params);
            injectAllNonEmptyParams(nextParams, row);

            if (StringUtils.isNotBlank(displayVal)) {
                nextParams.put(fieldName, displayVal);
            }

            LeftRowContext currentCtx = new LeftRowContext();
            currentCtx.displayValues.add(displayVal);
            currentCtx.paramValues.putAll(nextParams);
            result.add(currentCtx);
        }
        return result;
    }

    /**
     * 单次请求内的左侧数据构建方法
     * 使用局部缓存保证同一次请求中多个 config 复用同一份左侧数据
     */
    private List<LeftRowContext> buildLeftDataWithCache(JSONArray leftDims, Map<String, String> baseParams, String branch, Map<String, List<LeftRowContext>> requestCache) {
        // 生成缓存 key：branch + leftDims 结构
        String leftDimsJson = leftDims.toJSONString();
        String cacheKey = branch + ":" + leftDimsJson;

        return requestCache.computeIfAbsent(cacheKey, k -> {
            log.info("[DynamicReportGenerator] branch={} 未命中请求内缓存，执行左侧数据查询...", branch);
            try {
                List<LeftRowContext> leftRows = buildLeftData(leftDims, new HashMap<>(baseParams), true);
                log.info("[DynamicReportGenerator] branch={} 请求内缓存左侧数据，leftRows.size={}", branch, leftRows.size());
                return deepCopyLeftRows(leftRows);
            } catch (Exception e) {
                throw new RuntimeException("构建左侧维度数据失败: " + e.getMessage(), e);
            }
        });
    }

    /**
     * 深拷贝 LeftRowContext 列表，避免缓存被外部修改
     */
    private List<LeftRowContext> deepCopyLeftRows(List<LeftRowContext> original) {
        List<LeftRowContext> copy = new ArrayList<>();
        for (LeftRowContext ctx : original) {
            LeftRowContext newCtx = new LeftRowContext();
            newCtx.displayValues = new ArrayList<>(ctx.displayValues);
            newCtx.paramValues = new HashMap<>(ctx.paramValues);
            copy.add(newCtx);
        }
        return copy;
    }

    /**
     * 优化后的构建方法：第二维度及之后使用批量查询，避免指数级查询
     *
     * 思路：
     * 1. 第一维度查询返回 N 行（如 coop_name1 的值）
     * 2. 第二维度一次性查询所有数据（不带 coop_name1 条件），按 coop_name1 分组
     * 3. 按 coop_name1 匹配，每个父节点取最多 1 行
     * 4. 如果某个父节点没有对应数据，补充空行以保持行数一致
     *
     * 层级区分：
     * - 第一维度行：可能 SELECT 多列，用 buildIndentedLastNonEmptyDisplayValue 做层级缩进
     * - 第二维度行：直接取关联字段的值，不做层级缩进
     */
    private List<LeftRowContext> buildLeftDataWithBatchQuery(
            List<Map<String, String>> parentRows,
            JSONArray dims,
            Map<String, String> baseParams) throws Exception {

        if (parentRows == null || parentRows.isEmpty()) {
            return new ArrayList<>();
        }
        if (dims == null || dims.isEmpty()) {
            LeftRowContext ctx = new LeftRowContext();
            ctx.paramValues.putAll(baseParams);
            return Collections.singletonList(ctx);
        }

        // dims[0] 是当前处理的维度（由调用方已经处理过的）
        // dims[1] 是第二个维度（需要批量处理的）
        // ...
        JSONObject currentDimConfig = dims.getJSONObject(0);
        if (currentDimConfig == null) {
            throw new IllegalArgumentException("leftDimensions 配置错误：维度配置不能为空");
        }
        String fieldName = currentDimConfig.getString("fieldName");

        // 计算后续维度
        JSONArray next = dims.size() > 1 ? new JSONArray(dims.subList(1, dims.size())) : new JSONArray();

        // 确定第一维度返回的实际列名（用于关联后续维度）
        Map<String, String> firstRow = parentRows.get(0);
        List<String> selectColumns = new ArrayList<>();
        for (Map.Entry<String, String> entry : firstRow.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue())) {
                selectColumns.add(entry.getKey());
            }
        }

        // 用第一个非 fieldName 的列作为关联字段
        // 防御：如果 selectColumns 为空，直接返回（无法进行批量匹配）
        if (selectColumns.isEmpty()) {
            // 降级：无法确定关联字段，按原有逐行逻辑处理
            return buildLeftDataOriginalFallback(parentRows, dims, baseParams);
        }

        String linkField = selectColumns.get(0);
        for (String col : selectColumns) {
            if (!col.equals(fieldName)) {
                linkField = col;
                break;
            }
        }

        // 获取第一维度查询结果中用于关联后续维度的字段值
        List<String> parentKeys = new ArrayList<>();
        for (Map<String, String> row : parentRows) {
            String key = row.get(linkField);
            parentKeys.add(key != null ? key : "");
        }

        // ========== 第二维度批量查询 ==========
        JSONObject nextDimConfig = next.getJSONObject(0);
        String nextFieldName = nextDimConfig.getString("fieldName");
        String nextSql = nextDimConfig.getString("querySql");
        
        // 批量查询：去掉父节点条件（#{coop_name1}），查询所有数据
        // 需要 SQL 中 SELECT 出 linkField（coop_name1）才能匹配
        String batchSql = SqlBuilder.buildDataSqlWithEmptyParamHandling(nextSql, baseParams);
        
        List<Map<String, String>> allNextRows = new ArrayList<>();
        if (StringUtils.isNotBlank(batchSql)) {
            allNextRows = executeQuery(batchSql);
        }

        // 按关联字段分组（用 linkField 对应的值）
        Map<String, Map<String, String>> groupedNextRows = new LinkedHashMap<>();
        for (Map<String, String> nextRow : allNextRows) {
            String parentKey = nextRow.get(linkField);
            if (parentKey != null && !groupedNextRows.containsKey(parentKey)) {
                groupedNextRows.put(parentKey, nextRow);
            }
        }

        // ========== 为每个第一维度行匹配第二维度数据 ==========
        List<LeftRowContext> result = new ArrayList<>();
        for (int i = 0; i < parentRows.size(); i++) {
            Map<String, String> parentRow = parentRows.get(i);
            String parentKey = parentKeys.get(i);

            // 第一维度行展示值：可能有多列，做层级缩进
            String displayVal = buildIndentedLastNonEmptyDisplayValue(parentRow);

            // 构建当前行的参数
            Map<String, String> currentParams = new HashMap<>(baseParams);
            injectAllNonEmptyParams(currentParams, parentRow);
            if (StringUtils.isNotBlank(displayVal)) {
                currentParams.put(fieldName, displayVal);
            }

            // 匹配第二维度数据
            Map<String, String> matchedNextRow = groupedNextRows.get(parentKey);

            if (next.size() <= 1) {
                // 只有一个后续维度，第二维度数据直接作为最终行
                LeftRowContext ctx = new LeftRowContext();
                // 第一维度展示值（可能有层级缩进）
                ctx.displayValues.add(displayVal);

                if (matchedNextRow != null) {
                    // 第二维度展示值：直接取关联字段的值，不做层级缩进
                    // 如果 SQL SELECT 了多列，取最后一个非空列的值
                    String nextDisplayVal = getLastNonEmptyValue(matchedNextRow);
                    ctx.displayValues.add(nextDisplayVal);
                    // 注入第二维度查询返回的所有字段
                    injectAllNonEmptyParams(currentParams, matchedNextRow);
                    if (StringUtils.isNotBlank(nextDisplayVal)) {
                        currentParams.put(nextFieldName, nextDisplayVal);
                    }
                } else {
                    // 没有匹配的第二个维度数据，添加空行以保持行数一致
                    ctx.displayValues.add("");
                }
                ctx.paramValues.putAll(currentParams);
                result.add(ctx);
            } else {
                // 有多个后续维度，递归处理
                LeftRowContext currentCtx = new LeftRowContext();
                currentCtx.displayValues.add(displayVal);
                currentCtx.paramValues.putAll(currentParams);

                if (matchedNextRow != null) {
                    injectAllNonEmptyParams(currentParams, matchedNextRow);

                    // 递归处理剩余维度
                    JSONArray remainingDims = new JSONArray(next.subList(1, next.size()));
                    Map<String, String> recursiveParams = new HashMap<>(currentParams);
                    List<LeftRowContext> subs = buildLeftDataWithBatchQuery(
                            Collections.singletonList(matchedNextRow),
                            remainingDims,
                            recursiveParams
                    );

                    for (LeftRowContext sub : subs) {
                        // 先添加第一维度展示值
                        sub.displayValues.addAll(0, currentCtx.displayValues);
                        // params 合并：保留递归结果中的所有参数（包括第二维度及之后的参数）
                        for (Map.Entry<String, String> entry : currentCtx.paramValues.entrySet()) {
                            if (!sub.paramValues.containsKey(entry.getKey())) {
                                sub.paramValues.put(entry.getKey(), entry.getValue());
                            }
                        }
                        result.add(sub);
                    }
                } else {
                    // 没有匹配的第二个维度数据，补充空行
                    LeftRowContext emptyCtx = new LeftRowContext();
                    emptyCtx.displayValues.addAll(currentCtx.displayValues);
                    for (int j = 1; j < next.size(); j++) {
                        emptyCtx.displayValues.add("");
                    }
                    emptyCtx.paramValues.putAll(currentCtx.paramValues);
                    result.add(emptyCtx);
                }
            }
        }

        return result;
    }

    /**
     * 获取 map 中最后一个非空值（用于第二维度展示，不做层级缩进）
     */
    private static String getLastNonEmptyValue(Map<String, String> row) {
        if (row == null || row.isEmpty()) {
            return "";
        }
        String lastVal = "";
        for (String v : row.values()) {
            if (StringUtils.isNotBlank(v)) {
                lastVal = v.trim();
            }
        }
        return lastVal;
    }

    /**
     * 降级处理：当无法确定关联字段时，使用原有逐行逻辑
     */
    private List<LeftRowContext> buildLeftDataOriginalFallback(
            List<Map<String, String>> parentRows,
            JSONArray dims,
            Map<String, String> baseParams) throws Exception {

        JSONObject currentDimConfig = dims.getJSONObject(0);
        String fieldName = currentDimConfig.getString("fieldName");
        JSONArray next = dims.size() > 1 ? new JSONArray(dims.subList(1, dims.size())) : new JSONArray();

        List<LeftRowContext> result = new ArrayList<>();
        for (Map<String, String> row : parentRows) {
            String displayVal = buildIndentedLastNonEmptyDisplayValue(row);

            Map<String, String> nextParams = new HashMap<>(baseParams);
            injectAllNonEmptyParams(nextParams, row);
            if (StringUtils.isNotBlank(displayVal)) {
                nextParams.put(fieldName, displayVal);
            }

            LeftRowContext currentCtx = new LeftRowContext();
            currentCtx.displayValues.add(displayVal);
            currentCtx.paramValues.putAll(nextParams);

            // 递归处理后续维度（使用原有逐行逻辑）
            List<LeftRowContext> subs = buildLeftData(next, nextParams, false);
            if (subs.isEmpty()) {
                result.add(currentCtx);
            } else {
                for (LeftRowContext sub : subs) {
                    // 先添加当前维度展示值
                    sub.displayValues.addAll(0, currentCtx.displayValues);
                    // params 合并：保留递归结果中的所有参数
                    for (Map.Entry<String, String> entry : currentCtx.paramValues.entrySet()) {
                        if (!sub.paramValues.containsKey(entry.getKey())) {
                            sub.paramValues.put(entry.getKey(), entry.getValue());
                        }
                    }
                    result.add(sub);
                }
            }
        }
        return result;
    }

    /**
     * 构建顶部表头的"值矩阵"：
     * - 每一层 topDimension 扩展一行
     * - 多层递归后，得到类似：
     *   [ 年 年 年 ... ]
     *   [ 季 季 季 ... ]
     *   [ 月 月 月 ... ]
     */
    private List<List<String>> buildTopHeadersRecursive(JSONArray dims, Map<String, String> params) throws Exception {
        if (dims == null || dims.isEmpty()) {
            return new ArrayList<>();
        }
        JSONObject currentDim = dims.getJSONObject(0);
        String sqlTemplate = currentDim.getString("querySql");
        String builtSql = SqlBuilder.build(sqlTemplate, params);
        if (StringUtils.isBlank(builtSql)) {
            return new ArrayList<>();
        }
        JSONArray nextDims = (dims.size() > 1) ? new JSONArray(dims.subList(1, dims.size())) : new JSONArray();
        List<Map<String, String>> rows = executeQuery(builtSql);
        if (rows.isEmpty()) {
            return new ArrayList<>();
        }

        List<List<String>> resultRows = new ArrayList<>();
        resultRows.add(new ArrayList<>());

        for (Map<String, String> row : rows) {
            String val = extractDisplayValue(row);
            Map<String, String> nextParams = new HashMap<>(params);
            injectAllNonEmptyParams(nextParams, row);

            List<List<String>> realSubRows = buildTopHeadersRecursive(nextDims, nextParams);
            int width = realSubRows.isEmpty() ? 1 : realSubRows.get(0).size();
            int requiredHeight = 1 + (realSubRows.isEmpty() ? 0 : realSubRows.size());
            while (resultRows.size() < requiredHeight) {
                resultRows.add(new ArrayList<>());
            }

            for (int i = 1; i < resultRows.size(); i++) {
                if (i - 1 < realSubRows.size()) {
                    resultRows.get(i).addAll(realSubRows.get(i - 1));
                } else {
                    for (int k = 0; k < width; k++) {
                        resultRows.get(i).add("");
                    }
                }
            }
            for (int k = 0; k < width; k++) {
                resultRows.get(0).add(val);
            }
        }
        return resultRows;
    }

    /**
     * 顶部维度构建结果：同时包含表头和路径
     */
    static class TopDimensionsResult {
        List<List<String>> headers;  // 表头矩阵
        List<Map<String, String>> paths;  // 叶子列参数

        TopDimensionsResult(List<List<String>> headers, List<Map<String, String>> paths) {
            this.headers = headers;
            this.paths = paths;
        }
    }

    /**
     * 合并构建顶部维度的表头和路径（避免 buildTopHeadersRecursive 和 buildTopPaths 重复查询）
     * 一次性递归遍历 topDimensions，同时返回：
     * - headers：表头值矩阵
     * - paths：每个叶子列的参数组合
     */
    private TopDimensionsResult buildTopDimensionsWithMerge(JSONArray dims, Map<String, String> params) throws Exception {
        if (dims == null || dims.isEmpty()) {
            // 叶子节点：没有子维度了，返回空表头（表示不再有后续行），但返回一个路径
            return new TopDimensionsResult(new ArrayList<>(), Collections.singletonList(new HashMap<>(params)));
        }

        // 先执行维度查询一次
        JSONObject dim = dims.getJSONObject(0);
        String sqlTemplate = dim.getString("querySql");
        String builtSql = SqlBuilder.build(sqlTemplate, params);
        if (StringUtils.isBlank(builtSql)) {
            return new TopDimensionsResult(new ArrayList<>(), new ArrayList<>());
        }
        List<Map<String, String>> rows = executeQuery(builtSql);
        if (rows.isEmpty()) {
            return new TopDimensionsResult(new ArrayList<>(), new ArrayList<>());
        }

        JSONArray nextDims = dims.size() > 1 ? new JSONArray(dims.subList(1, dims.size())) : new JSONArray();

        // 先收集所有子结果
        List<SubResultEntry> subResultEntries = new ArrayList<>();
        for (Map<String, String> row : rows) {
            String val = extractDisplayValue(row);
            Map<String, String> nextParams = new HashMap<>(params);
            injectAllNonEmptyParams(nextParams, row);

            TopDimensionsResult subResult = buildTopDimensionsWithMerge(nextDims, nextParams);
            // 子维度的宽度：如果子表头为空（没有后续维度），则宽度为 1
            int subWidth = subResult.headers.isEmpty() ? 1 : subResult.headers.get(0).size();
            subResultEntries.add(new SubResultEntry(val, nextParams, subResult.headers, subResult.paths, subWidth));
        }

        // 计算总宽度和最大高度
        int totalWidth = 0;
        int maxHeight = 0;
        for (SubResultEntry entry : subResultEntries) {
            totalWidth += entry.width;
            maxHeight = Math.max(maxHeight, entry.headers.size() + 1);  // +1 因为第一行是当前维度
        }

        // 构建最终表头
        List<List<String>> resultHeaders = new ArrayList<>();
        for (int i = 0; i < maxHeight; i++) {
            resultHeaders.add(new ArrayList<>());
        }

        // 合并所有子结果
        List<Map<String, String>> allPaths = new ArrayList<>();
        for (SubResultEntry entry : subResultEntries) {
            // 合并路径
            for (Map<String, String> sp : entry.paths) {
                Map<String, String> mergedPath = new HashMap<>(entry.params);
                mergedPath.putAll(sp);
                allPaths.add(mergedPath);
            }

            // 填充表头
            List<List<String>> subHeaders = entry.headers;
            int width = entry.width;
            for (int rowIdx = 0; rowIdx < maxHeight; rowIdx++) {
                if (rowIdx == 0) {
                    // 第一行：填入维度值（重复 width 次）
                    for (int k = 0; k < width; k++) {
                        resultHeaders.get(0).add(entry.val);
                    }
                } else {
                    // 后续行：填入子维度的表头值
                    int subRowIdx = rowIdx - 1;
                    if (subRowIdx < subHeaders.size()) {
                        resultHeaders.get(rowIdx).addAll(subHeaders.get(subRowIdx));
                    } else {
                        // 子维度高度不足，填空
                        for (int k = 0; k < width; k++) {
                            resultHeaders.get(rowIdx).add("");
                        }
                    }
                }
            }
        }

        return new TopDimensionsResult(resultHeaders, allPaths);
    }

    /**
     * 子维度结果条目（用于合并计算）
     */
    private static class SubResultEntry {
        String val;                           // 当前维度的展示值
        Map<String, String> params;           // 当前维度的参数
        List<List<String>> headers;             // 子维度的表头
        List<Map<String, String>> paths;        // 子维度的路径
        int width;                             // 子维度的宽度（叶子列数）

        SubResultEntry(String val, Map<String, String> params, List<List<String>> headers,
                       List<Map<String, String>> paths, int width) {
            this.val = val;
            this.params = params;
            this.headers = headers;
            this.paths = paths;
            this.width = width;
        }
    }

    /**
     * 构建顶部"路径列表"：
     * - 每个叶子列对应一个 Map，包含这一列所有 topDimensions 的取值
     *   例如：{year_val=2025, quarter_val=Q3, month_val=2025-07}
     * - 后续数据查询会把这些参数与左侧参数合并，用于 metrics 的 SQL
     * @deprecated 请使用 buildTopDimensionsWithMerge 替代，避免重复查询
     */
    @Deprecated
    private List<Map<String, String>> buildTopPaths(JSONArray dims, Map<String, String> params) throws Exception {
        if (dims == null || dims.isEmpty()) {
            List<Map<String, String>> res = new ArrayList<>();
            res.add(new HashMap<>(params));
            return res;
        }
        JSONObject dim = dims.getJSONObject(0);
        JSONArray next = (dims.size() > 1) ? new JSONArray(dims.subList(1, dims.size())) : new JSONArray();
        String sql = SqlBuilder.build(dim.getString("querySql"), params);
        if (StringUtils.isBlank(sql)) {
            return new ArrayList<>();
        }
        List<Map<String, String>> rows = executeQuery(sql);
        List<Map<String, String>> result = new ArrayList<>();
        for (Map<String, String> row : rows) {
            Map<String, String> nextParams = new HashMap<>(params);
            injectAllNonEmptyParams(nextParams, row);
            result.addAll(buildTopPaths(next, nextParams));
        }
        return result;
    }

    /**
     * 左侧多字段展示规则：
     * - row 中字段按查询顺序（LinkedHashMap）遍历
     * - 找到最后一个非空字段的位置 pos（1-based）和值 val
     * - 返回 (pos-1) 个 "  " + val，用于在 Excel 左侧做层级缩进
     */
    private static String buildIndentedLastNonEmptyDisplayValue(Map<String, String> row) {
        if (row == null || row.isEmpty()) {
            return "";
        }

        int lastNonBlankPos = -1; // 1-based
        String lastNonBlankVal = "";
        int pos = 0;
        for (String v : row.values()) {
            pos++;
            if (StringUtils.isNotBlank(v)) {
                lastNonBlankPos = pos;
                lastNonBlankVal = v.trim();
            }
        }

        if (lastNonBlankPos <= 0 || StringUtils.isBlank(lastNonBlankVal)) {
            return "";
        }

        // 前缀为 N-1 个 "  "（两个短横构成一个单位），其中 N = 列序号（1-based）
        int dashCount = Math.max(0, lastNonBlankPos - 1);
        String prefix = "  ".repeat(dashCount);
        return prefix + lastNonBlankVal;
    }

    /**
     * 把当前 SQL 行返回的所有字段都注入到参数 Map 中：
     * - 即便值为 null / 空串，也要放进去，后续数据 SQL 里用到这些占位符时，
     *   会由 buildDataSqlWithEmptyParamHandling 统一把 "xxx = #{param}" 替换为 "1 = 1"。
     */
    private static void injectAllNonEmptyParams(Map<String, String> target, Map<String, String> source) {
        if (source == null) {
            return;
        }
        for (Map.Entry<String, String> entry : source.entrySet()) {
            // 不再过滤空值，确保 coop_name1/2/3 等字段总是作为查询条件存在于 params 中
            target.put(entry.getKey(), entry.getValue());
        }
    }

    private static String extractDisplayValue(Map<String, String> row) {
        if (row == null) {
            return "";
        }
        return row.values().stream().filter(StringUtils::isNotBlank).collect(Collectors.joining("-"));
    }

    /**
     * 构建每个 metric 的批量查询缓存：
     * - key: metric 索引（mIdx）
     * - value: Map<cellKey, value>
     *
     * 约束：
     * - metrics[mIdx] 必须包含 batchQuerySql + keyCols，否则不启用批量模式
     * - SQL 返回必须包含 keyCols 对应的列名，以及 valueColumn 对应的值列
     */
    private Map<Integer, Map<String, String>> buildMetricBatchCache(List<JSONObject> metricList, Map<String, String> baseParams) {
        Map<Integer, Map<String, String>> cache = new HashMap<>();
        if (metricList == null || metricList.isEmpty()) {
            return cache;
        }

        for (int mIdx = 0; mIdx < metricList.size(); mIdx++) {
            JSONObject metric = metricList.get(mIdx);
            if (metric == null) {
                continue;
            }
            String batchSqlTpl = metric.getString("batchQuerySql");
            JSONArray keyCols = metric.getJSONArray("keyCols");
            if (StringUtils.isBlank(batchSqlTpl) || keyCols == null || keyCols.isEmpty()) {
                continue; // 未配置批量 SQL
            }
            String valueColumn = metric.getString("valueColumn");
            if (StringUtils.isBlank(valueColumn)) {
                valueColumn = "value";
            }

            try {
                String batchSql = SqlBuilder.buildDataSqlWithEmptyParamHandling(batchSqlTpl, baseParams);
                List<Map<String, String>> rows = executeQuery(batchSql);
                Map<String, String> map = new HashMap<>();
                for (Map<String, String> r : rows) {
                    String k = buildKeyFromRow(r, keyCols);
                    if (StringUtils.isBlank(k)) {
                        continue;
                    }
                    map.put(k, r.getOrDefault(valueColumn, ""));
                }
                cache.put(mIdx, map);
            } catch (Exception ex) {
                // 批量查询失败时不影响主流程：回退到逐格查询
                log.warn("批量查询失败，metric={}，原因：{}", metric.getString("name"), ex.getMessage());
            }
        }
        return cache;
    }

    /**
     * 生成单元格 key：
     * - 从 metric.keyCols 读取 key 列名数组
     * - 按顺序拼接 queryParams 中对应值，用 | 连接
     */
    private static String buildCellKey(JSONObject metric, Map<String, String> queryParams) {
        if (metric == null || queryParams == null) {
            return "";
        }
        JSONArray keyCols = metric.getJSONArray("keyCols");
        if (keyCols == null || keyCols.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyCols.size(); i++) {
            String k = keyCols.getString(i);
            String v = queryParams.get(k);
            if (v == null) {
                v = "";
            }
            if (i > 0) {
                sb.append('|');
            }
            sb.append(v);
        }
        return sb.toString();
    }

    private static String buildKeyFromRow(Map<String, String> row, JSONArray keyCols) {
        if (row == null || keyCols == null || keyCols.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyCols.size(); i++) {
            String k = keyCols.getString(i);
            String v = row.get(k);
            if (v == null) {
                v = "";
            }
            if (i > 0) {
                sb.append('|');
            }
            sb.append(v);
        }
        return sb.toString();
    }

    /**
     * 统一的查询入口：通过 DynamicReportMapper 执行 SELECT，返回 List<Map<String,String>>
     */
    private List<Map<String, String>> executeQuery(String sql) throws Exception {
        if (StringUtils.isBlank(sql)) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> rows = dynamicReportMapper.executeSelect(sql);
        return mapRowsToString(rows);
    }

    /**
     * 将 MyBatis 返回的 Map<String,Object> 统一转换为 Map<String,String>：
     * - BigDecimal/Number：使用字符串表示（BigDecimal 使用 toPlainString）
     * - 其他类型：String.valueOf
     * - null：转为空字符串，避免后续拼接/展示 NPE
     */
    private static List<Map<String, String>> mapRowsToString(List<Map<String, Object>> rows) {
        if (rows == null || rows.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map<String, String>> out = new ArrayList<>(rows.size());
        for (Map<String, Object> r : rows) {
            Map<String, String> m = new LinkedHashMap<>();
            if (r != null) {
                for (Map.Entry<String, Object> e : r.entrySet()) {
                    String k = e.getKey();
                    Object v = e.getValue();
                    m.put(k, toCellString(v));
                }
            }
            out.add(m);
        }
        return out;
    }

    private static String toCellString(Object v) {
        if (v == null) {
            return "";
        }
        if (v instanceof java.math.BigDecimal) {
            return ((java.math.BigDecimal) v).toPlainString();
        }
        return String.valueOf(v);
    }

    static class SqlBuilder {

        /**
         * 通用 SQL 构建方法，用于"维度查询 SQL"：
         * - 简单把 #{param} 替换为 'value'
         * - 不做任何 AND/OR/WHERE 清理，适合 leftDimensions/topDimensions 这种简单条件
         */
        public static String build(String sql, Map<String, String> params) {
            if (sql == null || params == null) {
                return sql;
            }
            String result = sql;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String placeholder = "#{" + entry.getKey() + "}";
                String value = entry.getValue();
                if (value != null && !value.trim().isEmpty()) {
                    String escapedValue = value.replace("'", "''");
                    result = result.replace(placeholder, "'" + escapedValue + "'");
                }
            }
            return result;
        }

        /**
         * 构建"数据查询 SQL"的智能方法：
         * - 先扫描模板中所有 #{param}
         * - 对于没有值 / 空值的 param：
         *      把 "xxx = #{param}" 或 "#{param} = xxx" 替换为 "1 = 1"
         * - 对于有值的 param：
         *      做转义后替换为 'value'
         * - 最后统一清理多余 AND/OR/WHERE，避免尾部出现 "WHERE" 或 "AND"
         */
        public static String buildDataSqlWithEmptyParamHandling(String sqlTemplate, Map<String, String> params) {
            if (sqlTemplate == null) {
                return null;
            }
            if (params == null) {
                return sqlTemplate;
            }

            String result = sqlTemplate;

            // 先从模板中提取所有 #{param}，这样即便 params 里没有该 key 也能处理（例如 region 缺失）
            Set<String> templateParams = new HashSet<>();
            Matcher m = Pattern.compile("#\\{([^}]+)}").matcher(sqlTemplate);
            while (m.find()) {
                String name = m.group(1);
                if (name != null && !name.trim().isEmpty()) {
                    templateParams.add(name.trim());
                }
            }

            // 1) 对缺失/空值参数：把 "xxx = #{param}"（或 "#{param} = xxx"）替换为 1 = 1
            for (String paramName : templateParams) {
                String val = params.get(paramName);
                boolean missingOrBlank = (val == null || val.trim().isEmpty());
                if (!missingOrBlank) {
                    continue;
                }

                String ph = "#\\{" + Pattern.quote(paramName) + "\\}";
                // 左侧为字段/表达式（支持下划线/点）
                result = result.replaceAll("(?i)([\\w.]+)\\s*=\\s*" + ph, "1 = 1");
                // 右侧为字段/表达式
                result = result.replaceAll("(?i)" + ph + "\\s*=\\s*([\\w.]+)", "1 = 1");
            }

            // 2) 对有值参数：替换 #{param} 为 'value'（做单引号转义）
            for (String paramName : templateParams) {
                String val = params.get(paramName);
                if (val == null || val.trim().isEmpty()) {
                    continue;
                }

                String escaped = val.replace("'", "''");
                String ph = "#\\{" + Pattern.quote(paramName) + "\\}";
                result = result.replaceAll(ph, "'" + escaped + "'");
            }

            // 3) 清理连接词与空格（避免出现 WHERE AND / 多余 AND）
            result = result.replaceAll("\\s+", " ").trim();
            result = result.replaceAll("(?i)WHERE\\s+(AND|OR)\\s+", "WHERE ");
            result = result.replaceAll("(?i)\\b(AND|OR)\\b\\s+\\b(AND|OR)\\b", "$1");
            result = result.replaceAll("(?i)\\b(AND|OR)\\b\\s*(?=($|\\)))", "");
            result = result.replaceAll("(?i)\\s+WHERE\\s*$", "");

            return result.trim();
        }
    }
}
