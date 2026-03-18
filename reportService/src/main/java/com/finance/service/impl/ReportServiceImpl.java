package com.finance.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.finance.entity.ReportConfig;
import com.finance.entity.Response;
import com.finance.entity.req.ExportWithMergeReqVO;
import com.finance.entity.req.ReportPreviewReqVO;
import com.finance.entity.req.ReportSummaryReqVO;
import com.finance.entity.resp.HeaderNodeVO;
import com.finance.entity.resp.PreviewTableRespVO;
import com.finance.entity.resp.SummaryItemVO;
import com.finance.entity.resp.SummaryOverviewRespVO;
import com.finance.entity.enums.SummaryTimeType;
import com.finance.mapper.ReportConfigMapper;
import com.finance.mapper.DynamicReportMapper;
import com.finance.exception.BizException;
import com.finance.service.ReportService;
import com.finance.util.DynamicReportGenerator;
import com.finance.util.SmartExcelExporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final DynamicReportGenerator dynamicReportGenerator;
    private final ReportConfigMapper reportConfigMapper;
    private final DynamicReportMapper dynamicReportMapper;

    @Override
    public void exportMerged(ExportWithMergeReqVO req, HttpServletResponse response) throws IOException {
        String branches = req == null ? null : req.getBranches();
        String startDate = req == null ? null : req.getStartDate();
        String endDate = req == null ? null : req.getEndDate();
        // 参数校验
        if (!StringUtils.hasText(branches) || !StringUtils.hasText(startDate) || !StringUtils.hasText(endDate)) {
            throw new BizException(400, "branches/startDate/endDate 不能为空");
        }

        LocalDate s;
        LocalDate e;
        try {
            s = LocalDate.parse(startDate);
            e = LocalDate.parse(endDate);
        } catch (Exception ex) {
            throw new BizException(400, "startDate/endDate 格式必须为 yyyy-MM-dd");
        }
        if (s.isAfter(e)) {
            throw new BizException(400, "startDate 不能大于 endDate");
        }

        String[] branchArr = Arrays.stream(branches.split(","))
                .map(String::trim)
                .filter(StringUtils::hasText)
                .toArray(String[]::new);
        if (branchArr.length == 0) {
            throw new BizException(400, "branches 不能为空");
        }

        // 读取 PG report_config（每条记录：一个业务品种，req_para_val 中含 common + config）
        List<ReportConfig> templates = reportConfigMapper.selectList(null);
        if (templates == null || templates.isEmpty()) {
            throw new BizException(500, "report_config 无模板数据");
        }

        // 解析模板并校验 common 一致性（文件名模板/titleTemplate/dataHeaderRowIndex/leftDimensions 等都应该在 common 中统一配置）
        TemplateBundle bundle = parseAndValidateTemplates(templates);
        JSONObject common = bundle.common;

        // 从 common 中读取文件命名模板（不允许写死在代码里）
        String excelFileNameTemplate = common.getString("excelFileNameTemplate");
        String zipFileNameTemplate = common.getString("zipFileNameTemplate");
        String zipEntryFileNameTemplate = common.getString("zipEntryFileNameTemplate");
        if (!StringUtils.hasText(excelFileNameTemplate)
                || !StringUtils.hasText(zipFileNameTemplate)
                || !StringUtils.hasText(zipEntryFileNameTemplate)) {
            throw new BizException(400, "模板 common 缺少文件名模板配置：excelFileNameTemplate/zipFileNameTemplate/zipEntryFileNameTemplate");
        }

        // 全局参数：由前端传入，并补充中文日期参数，供 title/fileName 模板替换
        String startDateCn = toCnDate(startDate);
        String endDateCn = toCnDate(endDate);

        if (branchArr.length == 1) {
            String branch = branchArr[0];
            JSONObject gp = buildGlobalParams(startDate, endDate, branch, branches, startDateCn, endDateCn);
            byte[] bytes = buildExcelForBranch(branch, gp, common, bundle.configs);
            String fileName = applyTemplate(excelFileNameTemplate, gp);
            writeExcelResponse(response, bytes, fileName);
            return;
        }

        // 多分行：生成多个 excel 并打 zip
        JSONObject gpZip = buildGlobalParams(startDate, endDate, null, branches, startDateCn, endDateCn);
        String zipName = applyTemplate(zipFileNameTemplate, gpZip);
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" +
                URLEncoder.encode(zipName, StandardCharsets.UTF_8).replaceAll("\\+", "%20"));

        try (OutputStream os = response.getOutputStream(); ZipOutputStream zos = new ZipOutputStream(os)) {
            for (String b : branchArr) {
                JSONObject gp = buildGlobalParams(startDate, endDate, b, branches, startDateCn, endDateCn);
                byte[] bytes = buildExcelForBranch(b, gp, common, bundle.configs);
                String entryName = applyTemplate(zipEntryFileNameTemplate, gp);
                zos.putNextEntry(new ZipEntry(entryName));
                zos.write(bytes);
                zos.closeEntry();
            }
            zos.finish();
        } catch (IOException ex) {
            throw ex; // 仍然让 IO 异常向上抛，由容器处理
        } catch (Exception ex) {
            throw new BizException(500, "生成压缩包失败：" + ex.getMessage());
        }
    }

    /**
     * 预览（仅保留一个接口 /preview）：
     * 1) 从 PG(report_config) 找到指定 modelName 的模板
     * 2) 严格使用 field_content_old_val（查看 JSON）作为渲染模板（不使用导出 JSON 兜底）
     * 3) 调用 DynamicReportGenerator 生成二维 headLists/dataLists
     * 4) 将二维表头转换为树形表头（tableHeaderList），并将二维数据转换为行对象（tableContentList）
     */
    @Override
    public Response<PreviewTableRespVO> preview(ReportPreviewReqVO req) {
        PreviewContext ctx = parseAndValidatePreviewReq(req);

        JSONObject viewTpl = parseViewTemplate(ctx.modelName);
        JSONObject common = requireObject(viewTpl, "common", "查看 JSON 必须包含 common：modelName=" + ctx.modelName);
        JSONObject cfg = requireObject(viewTpl, "config", "查看 JSON 必须包含 config：modelName=" + ctx.modelName);

        // 生成器依赖的关键配置必须来自 JSON（避免代码写死）
        int dataHeaderRowIndex = common.getIntValue("dataHeaderRowIndex");
        if (dataHeaderRowIndex <= 0) throw new BizException(400, "模板缺少 dataHeaderRowIndex：modelName=" + ctx.modelName);
        JSONArray leftDimensions = common.getJSONArray("leftDimensions");
        if (leftDimensions == null) throw new BizException(400, "模板缺少 leftDimensions：modelName=" + ctx.modelName);

        JSONObject gp = buildPreviewGlobalParams(ctx.startDate, ctx.endDate, ctx.branch);

        // 注入生成器所需字段：生成器只接受一个 config JSON 字符串
        cfg.put("dataHeaderRowIndex", dataHeaderRowIndex);
        cfg.put("leftDimensions", leftDimensions);
        cfg.put("globalParams", gp);

        Map<String, Object> report;
        try {
            report = dynamicReportGenerator.generateReportData(cfg.toJSONString());
        } catch (Exception ex) {
            throw new BizException(500, "生成预览数据失败：" + ex.getMessage());
        }

        String title = buildPreviewTitle(common, gp);
        List<List<String>> headLists = cast2d(report.get("headLists"));
        List<List<String>> dataLists = cast2d(report.get("dataLists"));

        int leftLevels = inferLeftLevels(headLists);
        HeaderBuildResult hdr = buildTableHeaderTree(headLists, leftLevels);
        List<Map<String, String>> tableContentList = buildTableContentList(dataLists, hdr.pkByColIndex);

        PreviewTableRespVO res = new PreviewTableRespVO();
        res.setModelName(ctx.modelName);
        res.setTitle(title);
        res.setTableHeaderList(hdr.headerTree);
        res.setTableContentList(tableContentList);
        res.setHeadLists(headLists);
        res.setDataLists(dataLists);
        return Response.success(res);
    }

    /**
     * 汇总页面查询：
     * - 入参：branch/startDate/endDate（全部必填，日期需满足 startDate <= endDate）
     * - 数据来源：遍历 report_config 表，解析 field_content_new_val 中的汇总配置
     * - 每条配置会执行一个带 #{modelName}/#{startDate}/#{endDate}/#{branch}/#{type} 的 SQL，
     *   并按照 types 拆分出多行结果，最终聚合为 SummaryItemVO 列表返回。
     */
    @Override
    public Response<SummaryOverviewRespVO> summary(ReportSummaryReqVO req) {
        // 1. 入参校验
        if (!StringUtils.hasText(req.getBranch()) || !StringUtils.hasText(req.getStartDate()) || !StringUtils.hasText(req.getEndDate())) {
            throw new BizException(400, "branch/startDate/endDate 不能为空");
        }
        LocalDate s, e;
        try {
            s = LocalDate.parse(req.getStartDate());
            e = LocalDate.parse(req.getEndDate());
        } catch (Exception ex) {
            throw new BizException(400, "startDate/endDate 格式必须为 yyyy-MM-dd");
        }
        if (s.isAfter(e)) {
            throw new BizException(400, "startDate 不能大于 endDate");
        }

        // 2. 遍历所有 report_config，挑出配置了汇总 JSON 的记录
        List<ReportConfig> configs = reportConfigMapper.selectList(null);
        if (configs == null || configs.isEmpty()) {
            throw new BizException(500, "report_config 无模板数据");
        }

        List<JSONObject> jsons = new ArrayList<>();
        for (ReportConfig rc : configs) {
            if (rc == null || !StringUtils.hasText(rc.getFieldContentNewVal())) {
                continue; // 未配置汇总 JSON 的业务品种跳过
            }

            JSONObject cfg;
            try {
                cfg = JSONObject.parseObject(rc.getFieldContentNewVal());
            } catch (Exception ex) {
                throw new BizException(400, "汇总 JSON(field_content_new_val) 格式不正确：modelName=" + rc.getModelName());
            }
            if (cfg == null) {
                throw new BizException(400, "汇总 JSON(field_content_new_val) 不能为空：modelName=" + rc.getModelName());
            }

            String modelName = rc.getModelName();
            String rawSql = cfg.getString("querySql");
            String timeTypeStr = cfg.getString("summaryType");
            String typesStr = cfg.getString("types");

            if (!StringUtils.hasText(rawSql)) {
                throw new BizException(400, "汇总 JSON 缺少 querySql：modelName=" + modelName);
            }
            if (!StringUtils.hasText(timeTypeStr)) {
                throw new BizException(400, "汇总 JSON 缺少 summaryType：modelName=" + modelName);
            }
            if (!StringUtils.hasText(typesStr)) {
                throw new BizException(400, "汇总 JSON 缺少 types：modelName=" + modelName);
            }

            SummaryTimeType summaryType;
            try {
                summaryType = SummaryTimeType.valueOf(timeTypeStr.trim().toUpperCase());
            } catch (Exception ex) {
                throw new BizException(400, "summaryType 非法，只能为 POINT 或 RANGE：modelName=" + modelName);
            }

            String[] typesArr = Arrays.stream(typesStr.split(","))
                    .map(String::trim)
                    .filter(StringUtils::hasText)
                    .toArray(String[]::new);
            if (typesArr.length == 0) {
                throw new BizException(400, "types 解析后为空：modelName=" + modelName);
            }

            // 3. 按 types 循环执行 SQL
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (String type : typesArr) {
                Map<String, String> params = new HashMap<>();
                params.put("modelName", modelName);
                params.put("startDate", req.getStartDate());
                params.put("endDate", req.getEndDate());
                params.put("branch", req.getBranch());
                params.put("type", type);

                // 所有参数都必须非空，否则报错（不允许自动 1=1）
                for (Map.Entry<String, String> eParam : params.entrySet()) {
                    if (!StringUtils.hasText(eParam.getValue())) {
                        throw new BizException(400, "汇总 SQL 参数不能为空：" + eParam.getKey() + "，modelName=" + modelName);
                    }
                }

                // 汇总 SQL 的占位符替换规则：
                // - 所有 #{param} 都按“字符串参数”处理：自动 SQL 转义并加单引号
                // - 参数为空：直接报错（不允许替换为 1=1）
                String sql = buildSqlWithQuotedParams(rawSql, params, modelName);
                List<Map<String, Object>> rows;
                try {
                    rows = dynamicReportMapper.executeSelect(sql);
                } catch (Exception ex) {
                    throw new BizException(500, "执行汇总 SQL 失败：modelName=" + modelName + "，type=" + type + "，原因：" + ex.getMessage());
                }
                if (rows != null && !rows.isEmpty()) {
                    // 每一行补充：type / firstRow
                    boolean firstRow = type.equals(modelName);
                    for (Map<String, Object> row : rows) {
                        Map<String, Object> r = (row == null) ? new LinkedHashMap<>() : new LinkedHashMap<>(row);
                        r.put("type", type);
                        r.put("firstRow", firstRow);
                        dataList.add(r);
                    }
                }
            }

            // 4. 单个 modelName 的返回 JSON（你要求的结构）
            JSONObject one = new JSONObject();
            one.put("modelName", modelName);
            one.put("summaryType", summaryType.name());
            one.put("dataList", dataList);
            jsons.add(one);
        }

        SummaryOverviewRespVO vo = new SummaryOverviewRespVO();
        vo.setJsons(jsons);
        return Response.success(vo);
    }

    /**
     * 将 SQL 模板中的 #{param} 替换为 'value'（自动转义单引号）。
     *
     * 为什么这里要“强制加引号”：
     * - 汇总配置来自 JSON，绝大多数入参（branch/startDate/endDate/type/modelName）都是字符串；
     * - 若不加引号会出现：branch = 北京（北京被当成字段名）从而报错。
     *
     * 约束：
     * - params 中必须包含模板里出现的所有占位符，且值必须非空；否则抛 BizException。
     */
    private static String buildSqlWithQuotedParams(String sqlTemplate, Map<String, String> params, String modelName) {
        if (!StringUtils.hasText(sqlTemplate)) {
            throw new BizException(400, "querySql 不能为空：modelName=" + modelName);
        }
        if (params == null) {
            throw new BizException(400, "汇总 SQL 参数不能为空：modelName=" + modelName);
        }

        // 先收集模板参数名（避免直接 replace 时遗漏）
        Set<String> keys = new LinkedHashSet<>();
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("#\\{([^}]+)}").matcher(sqlTemplate);
        while (m.find()) {
            String k = m.group(1);
            if (k != null) {
                k = k.trim();
                if (!k.isEmpty()) keys.add(k);
            }
        }

        String out = sqlTemplate;
        for (String k : keys) {
            String v = params.get(k);
            if (!StringUtils.hasText(v)) {
                throw new BizException(400, "汇总 SQL 参数不能为空：" + k + "，modelName=" + modelName);
            }
            String escaped = v.replace("'", "''");
            out = out.replace("#{" + k + "}", "'" + escaped + "'");
        }
        return out;
    }

    /**
     * 预览请求的“最小上下文”：
     * - 在这里完成参数校验 + 日期校验
     * - 避免后续逻辑反复写 req.getXxx() 空判断
     */
    private static class PreviewContext {
        final String startDate;
        final String endDate;
        final String branch;
        final String modelName;

        private PreviewContext(String startDate, String endDate, String branch, String modelName) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.branch = branch;
            this.modelName = modelName;
        }
    }

    private static PreviewContext parseAndValidatePreviewReq(ReportPreviewReqVO req) {
        String startDate = req == null ? null : req.getStartDate();
        String endDate = req == null ? null : req.getEndDate();
        String branch = req == null ? null : req.getBranch();
        String modelName = req == null ? null : req.getModelName();

        if (!StringUtils.hasText(startDate) || !StringUtils.hasText(endDate) || !StringUtils.hasText(branch) || !StringUtils.hasText(modelName)) {
            throw new BizException(400, "startDate/endDate/branch/modelName 不能为空");
        }
        LocalDate s;
        LocalDate e;
        try {
            s = LocalDate.parse(startDate);
            e = LocalDate.parse(endDate);
        } catch (Exception ex) {
            throw new BizException(400, "startDate/endDate 格式必须为 yyyy-MM-dd");
        }
        if (s.isAfter(e)) {
            throw new BizException(400, "startDate 不能大于 endDate");
        }
        return new PreviewContext(startDate, endDate, branch, modelName);
    }

    private JSONObject parseViewTemplate(String modelName) {
        ReportConfig rc = reportConfigMapper.selectOne(new QueryWrapper<ReportConfig>().eq("model_name", modelName));
        if (rc == null) throw new BizException(404, "未找到模板：modelName=" + modelName);
        if (!StringUtils.hasText(rc.getFieldContentOldVal())) {
            throw new BizException(400, "查看 JSON(field_content_old_val) 为空：modelName=" + modelName);
        }
        try {
            JSONObject viewTpl = JSONObject.parseObject(rc.getFieldContentOldVal());
            if (viewTpl == null) throw new BizException(400, "查看 JSON 不能为空：modelName=" + modelName);
            return viewTpl;
        } catch (BizException be) {
            throw be;
        } catch (Exception ex) {
            throw new BizException(400, "查看 JSON 格式不正确：modelName=" + modelName);
        }
    }

    private static JSONObject requireObject(JSONObject parent, String key, String errMsg) {
        if (parent == null) throw new BizException(400, errMsg);
        JSONObject obj = parent.getJSONObject(key);
        if (obj == null) throw new BizException(400, errMsg);
        return obj;
    }

    private static JSONObject buildPreviewGlobalParams(String startDate, String endDate, String branch) {
        JSONObject gp = new JSONObject();
        gp.put("startDate", startDate);
        gp.put("endDate", endDate);
        gp.put("branch", branch);
        gp.put("startDateCn", toCnDate(startDate));
        gp.put("endDateCn", toCnDate(endDate));
        return gp;
    }

    private static String buildPreviewTitle(JSONObject common, JSONObject gp) {
        if (common == null) return "";
        String titleTemplate = common.getString("titleTemplate");
        if (!StringUtils.hasText(titleTemplate)) return "";
        return buildTitleFromTemplate(titleTemplate, gp);
    }

    @SuppressWarnings("unchecked")
    private static List<List<String>> cast2d(Object o) {
        if (o == null) return Collections.emptyList();
        try {
            return (List<List<String>>) o;
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    private static JSONObject toJson(Map<String, String> params) {
        JSONObject o = new JSONObject();
        if (params != null) {
            for (Map.Entry<String, String> e : params.entrySet()) {
                o.put(e.getKey(), e.getValue());
            }
        }
        return o;
    }

    private static int inferLeftLevels(List<List<String>> headLists) {
        if (headLists == null || headLists.isEmpty()) return 0;
        List<String> row0 = headLists.get(0);
        if (row0 == null || row0.isEmpty()) return 0;
        // 简单策略：左侧列一般是配置的维度 displayName，后面才进入顶部维度值（年/季/月/业务类型等）
        // 由于无法从这里直接获取 leftDimensions.size()，用“连续重复/空值变化点”估计：
        // - 从第0列开始，统计连续的非空列；遇到明显的顶部值（比如数字年/季度/月）也无法可靠判断
        // 因此这里选择更稳的方式：当表头行数 >=2 时，左侧列在每一行都相同，
        // 找到最小的 N，使得对所有表头行 r：headLists[r][0..N-1] 都与 row0 相等。
        int cols = row0.size();
        int rows = headLists.size();
        int n = 0;
        for (int c = 0; c < cols; c++) {
            String base = safeGet(headLists, 0, c);
            if (base == null) base = "";
            boolean sameAll = true;
            for (int r = 0; r < rows; r++) {
                String v = safeGet(headLists, r, c);
                if (v == null) v = "";
                if (!base.equals(v)) {
                    sameAll = false;
                    break;
                }
            }
            if (sameAll) {
                n++;
            } else {
                break;
            }
        }
        return n;
    }

    private static HeaderBuildResult buildTableHeaderTree(List<List<String>> headLists, int leftLevels) {
        List<HeaderNodeVO> headerTree = new ArrayList<>();
        Map<Integer, String> pkByColIndex = new HashMap<>();

        if (headLists == null || headLists.isEmpty() || headLists.get(0) == null) {
            return new HeaderBuildResult(headerTree, pkByColIndex);
        }
        int headerRows = headLists.size();
        int totalCols = headLists.get(0).size();

        // 生成每列 pk：a,b,c...（包含左侧列）
        for (int c = 0; c < totalCols; c++) {
            pkByColIndex.put(c, pkForIndex(c));
        }

        // 1) 左侧列：单层叶子（label=displayName, pk=...）
        for (int c = 0; c < Math.min(leftLevels, totalCols); c++) {
            HeaderNodeVO node = new HeaderNodeVO();
            node.setLabel(safeGet(headLists, 0, c));
            node.setPk(pkByColIndex.get(c));
            headerTree.add(node);
        }

        // 2) 右侧数据列：按表头行路径构建树
        for (int c = leftLevels; c < totalCols; c++) {
            List<String> path = new ArrayList<>();
            for (int r = 0; r < headerRows; r++) {
                path.add(safeGet(headLists, r, c));
            }
            insertPath(headerTree, path, 0, pkByColIndex.get(c));
        }

        return new HeaderBuildResult(headerTree, pkByColIndex);
    }

    private static void insertPath(List<HeaderNodeVO> siblings, List<String> path, int depth, String pk) {
        if (path == null || path.isEmpty() || depth >= path.size()) return;
        String label = path.get(depth);
        if (label == null) label = "";

        HeaderNodeVO node = null;
        for (HeaderNodeVO s : siblings) {
            String l = s.getLabel() == null ? "" : s.getLabel();
            if (label.equals(l)) {
                node = s;
                break;
            }
        }
        if (node == null) {
            node = new HeaderNodeVO();
            node.setLabel(label);
            siblings.add(node);
        }

        if (depth == path.size() - 1) {
            // 叶子
            node.setPk(pk);
            node.set_children(null);
            return;
        }

        List<HeaderNodeVO> children = node.get_children();
        if (children == null) {
            children = new ArrayList<>();
            node.set_children(children);
        }
        insertPath(children, path, depth + 1, pk);
    }

    private static List<Map<String, String>> buildTableContentList(List<List<String>> dataLists, Map<Integer, String> pkByColIndex) {
        List<Map<String, String>> out = new ArrayList<>();
        if (dataLists == null || dataLists.isEmpty()) return out;

        for (List<String> row : dataLists) {
            Map<String, String> obj = new LinkedHashMap<>();
            if (row != null) {
                for (int c = 0; c < row.size(); c++) {
                    String pk = pkByColIndex.get(c);
                    if (pk == null) continue;
                    obj.put(pk, row.get(c));
                }
            }
            out.add(obj);
        }
        return out;
    }

    private static String safeGet(List<List<String>> m, int r, int c) {
        if (m == null || r < 0 || r >= m.size()) return "";
        List<String> row = m.get(r);
        if (row == null || c < 0 || c >= row.size()) return "";
        String v = row.get(c);
        return v == null ? "" : v;
    }

    private static String pkForIndex(int idx) {
        // a..z, aa..az, ba..bz ...
        int n = idx;
        StringBuilder sb = new StringBuilder();
        do {
            int rem = n % 26;
            sb.append((char) ('a' + rem));
            n = n / 26 - 1;
        } while (n >= 0);
        return sb.reverse().toString();
    }

    private static class HeaderBuildResult {
        final List<HeaderNodeVO> headerTree;
        final Map<Integer, String> pkByColIndex;

        HeaderBuildResult(List<HeaderNodeVO> headerTree, Map<Integer, String> pkByColIndex) {
            this.headerTree = headerTree;
            this.pkByColIndex = pkByColIndex;
        }
    }

    /**
     * 单个分行导出：使用已校验一致性的 common + configs，生成合并后的 Excel bytes
     */
    private byte[] buildExcelForBranch(String branch, JSONObject gp, JSONObject common, JSONArray configs) throws IOException {
        int dataHeaderRowIndex = common.getIntValue("dataHeaderRowIndex");
        String titleTemplate = common.getString("titleTemplate");
        JSONArray leftDimensions = common.getJSONArray("leftDimensions");

        // 逐个 config 生成，再横向合并
        List<Map<String, Object>> reportMaps = new ArrayList<>();
        for (int i = 0; i < configs.size(); i++) {
            JSONObject cfg = configs.getJSONObject(i);
            if (cfg == null) continue;

            // 把公共配置下发到每个业务 config（避免每条都重复存 leftDimensions / titleTemplate 等）
            cfg.put("dataHeaderRowIndex", dataHeaderRowIndex);
            cfg.put("titleTemplate", titleTemplate);
            cfg.put("leftDimensions", leftDimensions);
            cfg.put("globalParams", gp);

            try {
                reportMaps.add(dynamicReportGenerator.generateReportData(cfg.toJSONString()));
            } catch (Exception ex) {
                throw new BizException(500, "生成业务[" + branch + "]报表数据失败：" + ex.getMessage());
            }
        }

        Map<String, Object> merged = mergeReportsSideBySide(reportMaps);
        merged.put("topDimCount", dataHeaderRowIndex);

        String title = buildTitleFromTemplate(titleTemplate, gp);
        try {
            return SmartExcelExporter.buildExcelBytesWithHeaderAreaMerge(
                    (List<List<String>>) merged.get("headLists"),
                    (List<List<String>>) merged.get("dataLists"),
                    "报表",
                    (Integer) merged.get("topDimCount"),
                    (Integer) merged.get("leftDimCount"),
                    title
            );
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception ex) {
            throw new BizException(500, "生成 Excel 文件失败：" + ex.getMessage());
        }
    }

    private static JSONObject buildGlobalParams(String startDate, String endDate, String branch, String branches,
                                                String startDateCn, String endDateCn) {
        JSONObject gp = new JSONObject();
        gp.put("startDate", startDate);
        gp.put("endDate", endDate);
        gp.put("branch", branch);
        gp.put("branches", branches);
        gp.put("startDateCn", startDateCn);
        gp.put("endDateCn", endDateCn);
        return gp;
    }

    private static String toCnDate(String yyyyMmDd) {
        int[] ymd = parseYmd(yyyyMmDd);
        if (ymd == null) return yyyyMmDd;
        return ymd[0] + "年" + ymd[1] + "月" + ymd[2] + "日";
    }

    private static String applyTemplate(String template, JSONObject params) {
        if (!StringUtils.hasText(template)) return template;
        if (params == null || params.isEmpty()) return template;
        String t = template;
        for (Map.Entry<String, Object> e : params.entrySet()) {
            if (e.getKey() == null || e.getValue() == null) continue;
            t = t.replace("#{" + e.getKey() + "}", String.valueOf(e.getValue()));
        }
        return t;
    }

    private static String buildTitleFromTemplate(String template, JSONObject globalParams) {
        if (!StringUtils.hasText(template) || globalParams == null || globalParams.isEmpty()) return template;
        String t = template;
        for (Map.Entry<String, Object> e : globalParams.entrySet()) {
            if (e.getKey() == null || e.getValue() == null) continue;
            t = t.replace("#{" + e.getKey() + "}", String.valueOf(e.getValue()));
        }
        return t;
    }

    private static int[] parseYmd(String s) {
        if (s == null) return null;
        String t = s.trim();
        if (t.isEmpty()) return null;
        String[] parts = t.split("[-/]");
        if (parts.length < 3) return null;
        try {
            int y = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            int d = Integer.parseInt(parts[2]);
            return new int[]{y, m, d};
        } catch (Exception ex) {
            return null;
        }
    }

    private static void writeExcelResponse(HttpServletResponse response, byte[] bytes, String fileName) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" +
                URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20"));
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
    }

    /**
     * 横向拼接多个业务品种报表（左侧列复用第一个，右侧数据区追加）
     */
    private static Map<String, Object> mergeReportsSideBySide(List<Map<String, Object>> reportMaps) {
        if (reportMaps == null || reportMaps.isEmpty()) {
            Map<String, Object> res = new HashMap<>();
            res.put("headLists", Collections.emptyList());
            res.put("dataLists", Collections.emptyList());
            res.put("topDimCount", 0);
            res.put("leftDimCount", 0);
            return res;
        }

        Map<String, Object> first = reportMaps.get(0);
        List<List<String>> mergedHeads = deepCopy2D((List<List<String>>) first.get("headLists"));
        List<List<String>> mergedData = deepCopy2D((List<List<String>>) first.get("dataLists"));
        int leftDimCount = (Integer) first.getOrDefault("leftDimCount", 0);

        for (int i = 1; i < reportMaps.size(); i++) {
            Map<String, Object> cur = reportMaps.get(i);
            if (cur == null) continue;

            List<List<String>> curHeads = (List<List<String>>) cur.get("headLists");
            List<List<String>> curData = (List<List<String>>) cur.get("dataLists");

            int headRows = Math.min(mergedHeads.size(), curHeads == null ? 0 : curHeads.size());
            for (int r = 0; r < headRows; r++) {
                List<String> srcRow = curHeads.get(r);
                if (srcRow == null || srcRow.size() <= leftDimCount) continue;
                mergedHeads.get(r).addAll(srcRow.subList(leftDimCount, srcRow.size()));
            }

            int dataRows = Math.min(mergedData.size(), curData == null ? 0 : curData.size());
            for (int r = 0; r < dataRows; r++) {
                List<String> srcRow = curData.get(r);
                if (srcRow == null || srcRow.size() <= leftDimCount) continue;
                mergedData.get(r).addAll(srcRow.subList(leftDimCount, srcRow.size()));
            }
        }

        Map<String, Object> res = new HashMap<>();
        res.put("headLists", mergedHeads);
        res.put("dataLists", mergedData);
        res.put("leftDimCount", leftDimCount);
        return res;
    }

    private static List<List<String>> deepCopy2D(List<List<String>> src) {
        if (src == null) return new ArrayList<>();
        List<List<String>> out = new ArrayList<>(src.size());
        for (List<String> row : src) {
            out.add(row == null ? new ArrayList<>() : new ArrayList<>(row));
        }
        return out;
    }

    /**
     * 从 PG 模板中解析并校验 common 一致性：
     * - common 必须一致（不一致直接报错）
     * - 收集每条模板的 config 到 configs 数组
     */
    private static TemplateBundle parseAndValidateTemplates(List<ReportConfig> templates) {
        JSONObject common = null;
        JSONArray configs = new JSONArray();

        for (ReportConfig rc : templates) {
            if (rc == null) continue;
            if (!StringUtils.hasText(rc.getReqParaVal())) {
                throw new BizException(400, "导出配置(req_para_val)不允许为空：modelName=" + rc.getModelName());
            }
            JSONObject tpl = JSONObject.parseObject(rc.getReqParaVal());
            JSONObject c = tpl.getJSONObject("common");
            JSONObject cfg = tpl.getJSONObject("config");
            if (c == null || cfg == null) {
                throw new BizException(400, "模板 JSON 必须包含 common 和 config：modelName=" + rc.getModelName());
            }
            if (common == null) {
                common = c;
            } else if (!common.equals(c)) {
                throw new BizException(400, "模板 common 配置不一致：modelName=" + rc.getModelName());
            }
            configs.add(cfg);
        }

        if (common == null) {
            throw new BizException(500, "未读取到任何有效模板 common 配置");
        }
        return new TemplateBundle(common, configs);
    }

    private static class TemplateBundle {
        final JSONObject common;
        final JSONArray configs;

        TemplateBundle(JSONObject common, JSONArray configs) {
            this.common = common;
            this.configs = configs;
        }
    }
}