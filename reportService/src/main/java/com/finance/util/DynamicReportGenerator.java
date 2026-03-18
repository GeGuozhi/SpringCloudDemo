package com.finance.util;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.finance.mapper.DynamicReportMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
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
 *   metrics       ：数据指标（每个叶子列下有多个指标，如“笔数 / 规模”）
 *   globalParams  ：全局参数，所有 SQL 内的 #{xxx} 都可以用这里的值替换
 *   dataHeaderRowIndex：指标行（metrics displayName）的表头行号（0-based）
 *
 * - 左侧维度：
 *   第 1 个 leftDimension 决定总行数（可以返回 N 行）；
 *   后续 leftDimension 对每个“父行”最多只能返回 0 或 1 行，超出即抛异常，避免行数错乱。
 *   每层查询可以 SELECT 多个字段（如 coop_name1, coop_name2, coop_name3），
 *   最终只展示“最后一个非空字段”，并在前面加 (列序号-1) 个 "  " 作为缩进前缀。
 *
 * - 顶部维度：
 *   递归执行 topDimensions，生成 rawTopHeaders（每一行对应一维）和 topPaths（每个叶子列的参数组合）。
 *
 * - 表头构建：
 *   把左侧 displayName、顶部 rawTopHeaders 和 metrics.displayName 按 dataHeaderRowIndex 合成最终 headLists，
 *   然后对表头做“向下填充”，保证每列在所有表头行都有非空值，便于后续合并单元格。
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
@Component
public class DynamicReportGenerator {

    @Autowired
    private DynamicReportMapper dynamicReportMapper;
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
                if (e.getKey() == null) continue;
                Object v = e.getValue();
                if (v == null) continue;
                String s = String.valueOf(v);
                if (StringUtils.isNotBlank(s)) {
                    baseParams.put(e.getKey(), s);
                }
            }
        }

        int leftLevels = leftDims.size();
        int metricsCount = metrics.size();
        List<JSONObject> metricList = metrics.toJavaList(JSONObject.class);

        // 1. 构建左侧维度数据：
        //    - 第一列允许多行，决定最终数据行数
        //    - 第二列及之后：每个“父节点”至多 0 或 1 行，超出即抛异常
        List<LeftRowContext> leftRows = buildLeftData(leftDims, new HashMap<>(baseParams), true);

        // 2. 构建顶部维度表头和路径
        List<List<String>> rawTopHeaders = buildTopHeadersRecursive(topDims, new HashMap<>(baseParams));
        if (rawTopHeaders.isEmpty()) rawTopHeaders.add(new ArrayList<>());
        int leafCount = rawTopHeaders.get(rawTopHeaders.size() - 1).size();
        List<Map<String, String>> topPaths = buildTopPaths(topDims, new HashMap<>(baseParams));

        // 指定“数据列（指标）表头所在行”（0-based）。
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
                    // 需要补齐的“空行”：先填空，后续会用“上方非空值”向下填充
                    topRowCells = new ArrayList<>(Collections.nCopies(leafCount, ""));
                }
                for (String cell : topRowCells) {
                    for (int k = 0; k < metricsCount; k++) row.add(cell);
                }
            } else { // i == dataHeaderRowIndex -> 指标行
                for (int k = 0; k < leafCount; k++) {
                    for (JSONObject m : metricList) row.add(m.getString("displayName"));
                }
            }
            finalHeads.add(row);
        }

        // 3.1 表头空白单元格向下填充：
        //     方便后续做“整块合并单元格”，避免出现中间有空洞的标题
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
        List<List<String>> finalData = new ArrayList<>();
        List<String> prevLeftDisplayValues = null;
        for (int lrIdx = 0; lrIdx < leftRows.size(); lrIdx++) { // 添加行索引用于调试
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
                    dRow.set(i, cellVal == null ? "" : cellVal);
                }
            }

            // 填充右侧数据区
            int dataStartCol = leftLevels;
            for (int pIdx = 0; pIdx < topPaths.size(); pIdx++) { // 添加列索引用于调试
                Map<String, String> topPath = topPaths.get(pIdx);
                for (int mIdx = 0; mIdx < metricList.size(); mIdx++) {
                    JSONObject metric = metricList.get(mIdx);

                    String sqlTemplate = metric.getString("querySql");
                    Map<String, String> queryParams = new HashMap<>();
                    queryParams.putAll(lrCtx.getParamValues());
                    queryParams.putAll(topPath);

                    String finalSql = SqlBuilder.buildDataSqlWithEmptyParamHandling(sqlTemplate, queryParams);

                    String value = "N/A";
                    try {
                        List<Map<String, String>> queryResult = executeQuery(finalSql);
                        if (!queryResult.isEmpty() && queryResult.get(0).containsKey("value")) {
                            value = queryResult.get(0).get("value");
                        }
                    } catch (Exception e) {
                        System.err.println("数据查询失败，SQL: " + finalSql);
                        e.printStackTrace();
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
        public List<String> getDisplayValues() { return displayValues; }
        public Map<String, String> getParamValues() { return paramValues; }
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
        String displayFormat = currentDimConfig.getString("displayFormat");

        JSONArray next = (dims.size() > 1) ? new JSONArray(dims.subList(1, dims.size())) : new JSONArray();
        // 使用“智能数据 SQL 构建”，这样当可选参数（如 coop_name2/3）为空时，
        // 像 "AND coop_name2 = #{coop_name2}" 这种条件会自动被替换成 "1 = 1"，避免 SQL 语法错误。
        String sql = SqlBuilder.buildDataSqlWithEmptyParamHandling(currentDimConfig.getString("querySql"), params);
        if (StringUtils.isBlank(sql)) return new ArrayList<>();

        List<Map<String, String>> rows = executeQuery(sql);

        // 规则：整个 Excel 的行数由第一列（第一个 leftDimension）的查询结果决定。
        // 所以从第二列（及之后）开始，每个父节点下只能返回 0 或 1 条记录，若 >1 说明“该列行数会超过第一列” -> 抛错。
        if (!isFirstDim && rows.size() > 1) {
            String displayName = currentDimConfig.getString("displayName");
            String colName = StringUtils.isNotBlank(displayName) ? displayName : fieldName;
            throw new IllegalStateException("左侧维度列【" + colName + "】查询结果行数为 " + rows.size()
                    + "，应为 0 或 1，且必须与第一列的行数保持一致，请检查该列的 SQL 配置。");
        }

        List<LeftRowContext> result = new ArrayList<>();

        for (Map<String, String> row : rows) {
            // 左侧展示逻辑：
            // - SQL 可以 SELECT 多列（例如 coop_name1, coop_name2, coop_name3）
            // - 仅取“最后一个非空字段”作为展示值
            // - 在前面加 (列序号-1) 个 "  " 作为缩进，用于体现层级
            String displayVal = buildIndentedLastNonEmptyDisplayValue(row);

            Map<String, String> nextParams = new HashMap<>(params);
            injectAllNonEmptyParams(nextParams, row);

            // 关键修复：将格式化后的 displayVal 作为一个虚拟参数注入
            if (StringUtils.isNotBlank(displayVal)) {
                nextParams.put(fieldName, displayVal);
            }

            LeftRowContext currentCtx = new LeftRowContext();
            currentCtx.displayValues.add(displayVal);
            currentCtx.paramValues.putAll(nextParams);

            List<LeftRowContext> subs = buildLeftData(next, nextParams, false);
            if (subs.isEmpty()) {
                result.add(currentCtx);
            } else {
                for (LeftRowContext sub : subs) {
                    sub.displayValues.addAll(0, currentCtx.displayValues);
                    sub.paramValues.putAll(currentCtx.paramValues);
                    result.add(sub);
                }
            }
        }
        return result;
    }

    /**
     * 构建顶部表头的“值矩阵”：
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
        if (StringUtils.isBlank(builtSql)) return new ArrayList<>();
        JSONArray nextDims = (dims.size() > 1) ? new JSONArray(dims.subList(1, dims.size())) : new JSONArray();
        List<Map<String, String>> rows = executeQuery(builtSql);
        if (rows.isEmpty()) return new ArrayList<>();

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
                    for (int k = 0; k < width; k++) resultRows.get(i).add("");
                }
            }
            for (int k = 0; k < width; k++) {
                resultRows.get(0).add(val);
            }
        }
        return resultRows;
    }

    /**
     * 构建顶部“路径列表”：
     * - 每个叶子列对应一个 Map，包含这一列所有 topDimensions 的取值
     *   例如：{year_val=2025, quarter_val=Q3, month_val=2025-07}
     * - 后续数据查询会把这些参数与左侧参数合并，用于 metrics 的 SQL
     */
    private List<Map<String, String>> buildTopPaths(JSONArray dims, Map<String, String> params) throws Exception {
        if (dims == null || dims.isEmpty()) {
            List<Map<String, String>> res = new ArrayList<>();
            res.add(new HashMap<>(params));
            return res;
        }
        JSONObject dim = dims.getJSONObject(0);
        JSONArray next = (dims.size() > 1) ? new JSONArray(dims.subList(1, dims.size())) : new JSONArray();
        String sql = SqlBuilder.build(dim.getString("querySql"), params);
        if (StringUtils.isBlank(sql)) return new ArrayList<>();
        List<Map<String, String>> rows = executeQuery(sql);
        List<Map<String, String>> result = new ArrayList<>();
        for (Map<String, String> row : rows) {
            Map<String, String> nextParams = new HashMap<>(params);
            injectAllNonEmptyParams(nextParams, row);
            result.addAll(buildTopPaths(next, nextParams));
        }
        return result;
    }

    // ================= 辅助方法 =================

    /**
     * 左侧多字段展示规则：
     * - row 中字段按查询顺序（LinkedHashMap）遍历
     * - 找到最后一个非空字段的位置 pos（1-based）和值 val
     * - 返回 (pos-1) 个 "  " + val，用于在 Excel 左侧做层级缩进
     */
    private static String buildIndentedLastNonEmptyDisplayValue(Map<String, String> row) {
        if (row == null || row.isEmpty()) return "";

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

        if (lastNonBlankPos <= 0 || StringUtils.isBlank(lastNonBlankVal)) return "";

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
        if (source == null) return;
        for (Map.Entry<String, String> entry : source.entrySet()) {
            // 不再过滤空值，确保 coop_name1/2/3 等字段总是作为查询条件存在于 params 中
            target.put(entry.getKey(), entry.getValue());
        }
    }

    private static String extractDisplayValue(Map<String, String> row) {
        if (row == null) return "";
        return row.values().stream().filter(StringUtils::isNotBlank).collect(Collectors.joining("-"));
    }

    /**
     * 统一的查询入口：通过 MyBatis Mapper 执行 SELECT，返回 List<Map<String,String>>
     */
    private List<Map<String, String>> executeQuery(String sql) throws Exception {
        if (StringUtils.isBlank(sql)) return new ArrayList<>();
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
        if (rows == null || rows.isEmpty()) return new ArrayList<>();
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
        if (v == null) return "";
        if (v instanceof java.math.BigDecimal) {
            return ((java.math.BigDecimal) v).toPlainString();
        }
        return String.valueOf(v);
    }

    static class SqlBuilder {
        /**
         * 通用 SQL 构建方法，用于“维度查询 SQL”：
         * - 简单把 #{param} 替换为 'value'
         * - 不做任何 AND/OR/WHERE 清理，适合 leftDimensions/topDimensions 这种简单条件
         */
        public static String build(String sql, Map<String, String> params) {
            if (sql == null || params == null) return sql;
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
         * 构建“数据查询 SQL”的智能方法：
         * - 先扫描模板中所有 #{param}
         * - 对于没有值 / 空值的 param：
         *      把 "xxx = #{param}" 或 "#{param} = xxx" 替换为 "1 = 1"
         * - 对于有值的 param：
         *      做转义后替换为 'value'
         * - 最后统一清理多余 AND/OR/WHERE，避免尾部出现 "WHERE" 或 "AND"
         */
        public static String buildDataSqlWithEmptyParamHandling(String sqlTemplate, Map<String, String> params) {
            if (sqlTemplate == null) return null;
            if (params == null) return sqlTemplate;

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
                if (!missingOrBlank) continue;

                String ph = "#\\{" + Pattern.quote(paramName) + "\\}";
                // 左侧为字段/表达式（支持下划线/点）
                result = result.replaceAll("(?i)([\\w.]+)\\s*=\\s*" + ph, "1 = 1");
                // 右侧为字段/表达式
                result = result.replaceAll("(?i)" + ph + "\\s*=\\s*([\\w.]+)", "1 = 1");
            }

            // 2) 对有值参数：替换 #{param} 为 'value'（做单引号转义）
            for (String paramName : templateParams) {
                String val = params.get(paramName);
                if (val == null || val.trim().isEmpty()) continue;

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