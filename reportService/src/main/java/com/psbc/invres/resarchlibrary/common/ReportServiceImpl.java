package com.psbc.invres.resarchlibrary.common;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportBranchVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportBranchesRespVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportConfigDO;
import com.psbc.invres.resarchlibrary.entity.Response;
import com.psbc.invres.resarchlibrary.enums.ResarchLibraryResponseCodeEnum;
import com.psbc.invres.resarchlibrary.enums.SummaryTimeType;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ExportWithMergeReqVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportBranchesReqVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportPreviewReqVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportSummaryReqVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.HeaderNodeVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.PreviewTableRespVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.SummaryOverviewRespVO;
import com.psbc.invres.resarchlibrary.exception.BizException;
import com.psbc.invres.resarchlibrary.mapper.doris.DynamicReportMapper;
import com.psbc.invres.resarchlibrary.mapper.doris.ReportConfigMapper;
import com.psbc.invres.resarchlibrary.mapper.doris.OdsdbMapper;
import com.psbc.invres.resarchlibrary.service.excelconfig.ReportService;
import com.psbc.invres.resarchlibrary.util.DynamicReportGenerator;
import com.psbc.invres.resarchlibrary.util.IndexCompletabFutureUtil;
import com.psbc.invres.resarchlibrary.util.SmartExcelExporter;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 报表服务实现类
 *
 * 核心功能：
 * 1. /summary - 汇总查询：查询各业务品种的汇总数据
 * 2. /preview - 报表预览：根据 modelName 生成预览数据（树形表头 + 行数据）
 * 3. /download - 下载 Excel/Zip：导出合并后的报表文件
 *
 * 数据来源：report_config 表
 * - field_content_old_val：预览模板（查看 JSON）
 * - field_content_new_val：汇总配置（汇总 JSON）
 * - req_para_val：导出配置（导出 JSON）
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final DynamicReportGenerator dynamicReportGenerator;
    private final ReportConfigMapper reportConfigMapper;
    private final DynamicReportMapper dynamicReportMapper;
    private final OdsdbMapper odsdbMapper;
    private final ThreadPoolExecutor exportExecutor;

    // ==================== /download 接口：下载 Excel/Zip ====================

    /**
     * 下载 Excel/Zip 接口
     *
     * 业务逻辑：
     * - 单个分行：直接生成一个 Excel 文件
     * - 多个分行：将每个分行的 Excel 打成一个 Zip 包返回
     *
     * 数据处理：
     * - 从 report_config 表读取所有模板配置（req_para_val 字段）
     * - 每个模板包含 common（公共配置）和 config（业务配置）
     * - 多个 config 会横向合并到同一个 sheet
     *
     * @param req     前端请求：branches（逗号分隔）、startDate、endDate
     * @param response HTTP 响应
     */
    @Override
    public void exportMerged(ExportWithMergeReqVO req, HttpServletResponse response) throws IOException {
        // ========== 1. 参数基础校验 ==========
        String branches = (req == null) ? null : req.getBranches();
        String startDate = (req == null) ? null : req.getStartDate();
        String endDate = (req == null) ? null : req.getEndDate();

        if (!StringUtils.hasText(branches) || !StringUtils.hasText(startDate) || !StringUtils.hasText(endDate)) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_PARAM_MISSING);
        }

        // ========== 2. 日期合法性校验 ==========
        LocalDate s;
        LocalDate e;
        try {
            s = LocalDate.parse(startDate);
            e = LocalDate.parse(endDate);
        } catch (Exception ex) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_DATE_FORMAT_INVALID);
        }
        if (s.isAfter(e)) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_DATE_RANGE_INVALID);
        }

        // ========== 3. 解析分行列表（逗号分隔）==========
        String[] branchArr = Arrays.stream(branches.split(","))
                .map(String::trim)
                .filter(StringUtils::hasText)
                .toArray(String[]::new);
        if (branchArr.length == 0) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_BRANCHES_MISSING);
        }

        // ========== 4. 读取数据库模板配置 ==========
        List<ReportConfigDO> templates = reportConfigMapper.selectList(null);
        if (templates == null || templates.isEmpty()) {
            throw new BizException(ResarchLibraryResponseCodeEnum.DB_TEMPLATE_NOT_FOUND);
        }

        // ========== 5. 解析模板并校验 common 一致性 ==========
        TemplateBundle bundle = parseAndValidateTemplates(templates);
        JSONObject common = bundle.common;

        // ========== 6. 从 common 中读取文件名模板 ==========
        String excelFileNameTemplate = common.getString("excelFileNameTemplate");
        String zipFileNameTemplate = common.getString("zipFileNameTemplate");
        String zipEntryFileNameTemplate = common.getString("zipEntryFileNameTemplate");
        if (!StringUtils.hasText(excelFileNameTemplate)
                || !StringUtils.hasText(zipFileNameTemplate)
                || !StringUtils.hasText(zipEntryFileNameTemplate)) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_TEMPLATE_FILENAME_MISSING);
        }

        // ========== 7. 构建全局参数（供模板变量替换使用）==========
        String startDateCn = toCnDate(startDate);
        String endDateCn = toCnDate(endDate);

        // ========== 分支判断：单分行 vs 多分行 ==========
        if (branchArr.length == 1) {
            // 单个分行：直接返回 Excel 文件
            String branch = branchArr[0];
            JSONObject gp = buildGlobalParams(startDate, endDate, branch, branches, startDateCn, endDateCn);
            byte[] bytes = buildExcelForBranch(branch, gp, common, bundle.configs);
            String fileName = buildTitleFromTemplate(excelFileNameTemplate, gp);
            writeExcelResponse(response, bytes, fileName);
            return;
        }

        // 多个分行：并行生成所有分行的 Excel，最后打包成 Zip
        JSONObject gpZip = buildGlobalParams(startDate, endDate, null, branches, startDateCn, endDateCn);
        String zipName = buildTitleFromTemplate(zipFileNameTemplate, gpZip);
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" +
                URLEncoder.encode(zipName, StandardCharsets.UTF_8).replaceAll("\\+", "%20"));

        // 并行生成每个分行的 Excel
        try {
            List<BranchExcel> branchExcels = IndexCompletabFutureUtil.asynActEach(
                    Arrays.asList(branchArr),
                    branch -> {
                        try {
                            log.info(">>> [{}] 开始生成 Excel，configs.size={}", branch, bundle.configs.size());
                            // 每次都重新深拷贝 configs，避免多线程共享
                            JSONArray configsCopy = new JSONArray();
                            for (Object cfgObj : bundle.configs) {
                                if (cfgObj instanceof JSONObject) {
                                    configsCopy.add(JSONObject.parseObject(((JSONObject) cfgObj).toJSONString()));
                                }
                            }
                            JSONObject gp = buildGlobalParams(startDate, endDate, branch, branches, startDateCn, endDateCn);
                            log.info(">>> [{}] 开始处理，configsCopy.size={}, common hashCode={}", 
                                    branch, configsCopy.size(), System.identityHashCode(common));
                            byte[] bytes = buildExcelForBranch(branch, gp, common, configsCopy);
                            String entryName = buildTitleFromTemplate(zipEntryFileNameTemplate, gp);
                            log.info(">>> [{}] Excel 生成完成，size={}", branch, bytes.length);
                            return new BranchExcel(entryName, bytes);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    },
                    exportExecutor
            );

            // 写入 Zip
            try (OutputStream os = response.getOutputStream();
                 ZipOutputStream zos = new ZipOutputStream(os)) {
                for (BranchExcel be : branchExcels) {
                    zos.putNextEntry(new ZipEntry(be.entryName));
                    zos.write(be.bytes);
                    zos.closeEntry();
                }
                zos.finish();
            }
        } catch (BizException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BizException(ResarchLibraryResponseCodeEnum.AT_ZIP_CREATE_FAILED, ex.getMessage());
        }
    }

    // ==================== /preview 接口：报表预览 ====================

    /**
     * 预览接口：根据 modelName 生成报表预览数据
     *
     * 流程：
     * 1. 从数据库查询指定 modelName 的模板（field_content_old_val）
     * 2. 调用 DynamicReportGenerator 生成二维表头和数据
     * 3. 将二维表头转换为树形结构，便于前端渲染
     * 4. 将二维数据转换为行对象（key=列标识，value=单元格值）
     *
     * 注意：预览只生成单个业务品种，不合并多个 config
     */
    @Override
    public Response<PreviewTableRespVO> preview(ReportPreviewReqVO req) {
        // ========== 1. 解析并校验请求参数 ==========
        PreviewContext ctx = parseAndValidatePreviewReq(req);

        // ========== 2. 读取模板配置 ==========
        JSONObject viewTpl = parseViewTemplate(ctx.modelName);
        JSONObject common = requireObject(viewTpl, "common", ResarchLibraryResponseCodeEnum.BL_VIEW_COMMON_MISSING, ctx.modelName);
        JSONObject cfg = requireObject(viewTpl, "config", ResarchLibraryResponseCodeEnum.BL_VIEW_CONFIG_MISSING, ctx.modelName);

        // ========== 3. 提取关键配置 ==========
        int dataHeaderRowIndex = common.getIntValue("dataHeaderRowIndex");
        if (dataHeaderRowIndex <= 0) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_TEMPLATE_HEADER_ROW_MISSING, ctx.modelName);
        }
        JSONArray leftDimensions = common.getJSONArray("leftDimensions");
        if (leftDimensions == null) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_TEMPLATE_LEFT_DIM_MISSING, ctx.modelName);
        }

        // ========== 4. 构建全局参数 ==========
        JSONObject gp = buildPreviewGlobalParams(ctx.startDate, ctx.endDate, ctx.branch);

        // ========== 5. 调用生成器 ==========
        cfg.put("dataHeaderRowIndex", dataHeaderRowIndex);
        cfg.put("leftDimensions", leftDimensions);
        cfg.put("globalParams", gp);

        Map<String, Object> report;
        try {
            report = dynamicReportGenerator.generateReportData(cfg.toJSONString());
        } catch (Exception ex) {
            throw new BizException(ResarchLibraryResponseCodeEnum.AT_PREVIEW_GENERATE_FAILED, ex.getMessage());
        }

        // ========== 6. 组装返回数据 ==========
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

    // ==================== /summary 接口：汇总查询 ====================

    /**
     * 汇总查询接口：查询各业务品种的汇总数据
     *
     * 流程：
     * 1. 遍历 report_config 表，筛选出配置了汇总 JSON（field_content_new_val）的记录
     * 2. 每条配置包含 querySql、summaryType、types
     * 3. 按 types 循环执行 SQL，替换占位符 #{modelName}/#{startDate}/#{endDate}/#{branch}/#{type}
     * 4. 将查询结果聚合返回
     *
     * @return 汇总数据列表，每条包含 modelName、summaryType、dataList
     */
    @Override
    public Response<SummaryOverviewRespVO> summary(ReportSummaryReqVO req) {
        // ========== 1. 入参校验 ==========
        if (!StringUtils.hasText(req.getBranch()) || !StringUtils.hasText(req.getStartDate()) || !StringUtils.hasText(req.getEndDate())) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_PARAM_MISSING);
        }
        LocalDate s;
        LocalDate e;
        try {
            s = LocalDate.parse(req.getStartDate());
            e = LocalDate.parse(req.getEndDate());
        } catch (Exception ex) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_DATE_FORMAT_INVALID);
        }
        if (s.isAfter(e)) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_DATE_RANGE_INVALID);
        }

        // ========== 2. 读取汇总配置 ==========
        List<ReportConfigDO> configs = reportConfigMapper.selectList(null);
        if (configs == null || configs.isEmpty()) {
            throw new BizException(ResarchLibraryResponseCodeEnum.DB_TEMPLATE_NOT_FOUND);
        }

        // ========== 3. 遍历处理每个业务品种 ==========
        List<JSONObject> jsons = new ArrayList<>();
        for (ReportConfigDO rc : configs) {
            // 跳过未配置汇总 JSON 的业务品种
            if (rc == null || !StringUtils.hasText(rc.getFieldContentNewVal())) {
                continue;
            }

            // 解析汇总 JSON
            JSONObject cfg;
            try {
                cfg = JSONObject.parseObject(rc.getFieldContentNewVal());
            } catch (Exception ex) {
                throw new BizException(ResarchLibraryResponseCodeEnum.BL_JSON_FORMAT_INVALID, rc.getModelName());
            }
            if (cfg == null) {
                throw new BizException(ResarchLibraryResponseCodeEnum.BL_JSON_CONTENT_EMPTY, rc.getModelName());
            }

            String modelName = rc.getModelName();
            String rawSql = cfg.getString("querySql");
            String timeTypeStr = cfg.getString("summaryType");
            String typesStr = cfg.getString("types");

            // 必填字段校验
            if (!StringUtils.hasText(rawSql)) {
                throw new BizException(ResarchLibraryResponseCodeEnum.BL_SUMMARY_QUERY_SQL_MISSING, modelName);
            }
            if (!StringUtils.hasText(timeTypeStr)) {
                throw new BizException(ResarchLibraryResponseCodeEnum.BL_SUMMARY_TYPE_MISSING, modelName);
            }
            if (!StringUtils.hasText(typesStr)) {
                throw new BizException(ResarchLibraryResponseCodeEnum.BL_SUMMARY_TYPES_MISSING, modelName);
            }

            // 解析 summaryType
            SummaryTimeType summaryType;
            try {
                summaryType = SummaryTimeType.valueOf(timeTypeStr.trim().toUpperCase());
            } catch (Exception ex) {
                throw new BizException(ResarchLibraryResponseCodeEnum.BL_SUMMARY_TYPE_INVALID, modelName);
            }

            // ========== 4. 按 types 循环执行 SQL ==========
            String[] typesArr = Arrays.stream(typesStr.split(","))
                    .map(String::trim)
                    .filter(StringUtils::hasText)
                    .toArray(String[]::new);
            if (typesArr.length == 0) {
                throw new BizException(ResarchLibraryResponseCodeEnum.BL_SUMMARY_TYPES_EMPTY, modelName);
            }

            List<Map<String, Object>> dataList = new ArrayList<>();
            for (String type : typesArr) {
                // 构建 SQL 参数
                Map<String, String> params = new HashMap<>();
                params.put("modelName", modelName);
                params.put("startDate", req.getStartDate());
                params.put("endDate", req.getEndDate());
                params.put("branch", req.getBranch());
                params.put("type", type);

                // 校验参数非空
                for (Map.Entry<String, String> eParam : params.entrySet()) {
                    if (!StringUtils.hasText(eParam.getValue())) {
                        throw new BizException(ResarchLibraryResponseCodeEnum.BL_SUMMARY_PARAM_MISSING, eParam.getKey() + "，modelName=" + modelName);
                    }
                }

                // 替换 SQL 占位符（加单引号防注入）
                String sql = buildSqlWithQuotedParams(rawSql, params, modelName);

                // 执行查询
                List<Map<String, Object>> rows;
                try {
                    rows = dynamicReportMapper.executeSelect(sql);
                } catch (Exception ex) {
                    throw new BizException(ResarchLibraryResponseCodeEnum.DB_SUMMARY_SQL_FAILED, "modelName=" + modelName + "，type=" + type + "，原因：" + ex.getMessage());
                }

                // 补充 type 和 firstRow 字段
                if (rows != null && !rows.isEmpty()) {
                    boolean firstRow = type.equals(modelName);
                    for (Map<String, Object> row : rows) {
                        Map<String, Object> r = (row == null) ? new LinkedHashMap<>() : new LinkedHashMap<>(row);
                        r.put("type", type);
                        r.put("firstRow", firstRow);
                        dataList.add(r);
                    }
                }
            }

            // ========== 5. 组装返回数据 ==========
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

    // ==================== /searchBranches 接口：查询分行列表 ====================

    /**
     * 查询分行列表
     *
     * @param req 请求参数，包含 branchName（可选，模糊匹配）
     * @return 分行列表
     */
    @Override
    public Response<ReportBranchesRespVO> searchBranches(ReportBranchesReqVO req) {
        List<Map<String, String>> lists = odsdbMapper.searchBranches(req.getBranchName());

        ReportBranchesRespVO respVOS = new ReportBranchesRespVO();
        List<ReportBranchVO> branchVOS = new ArrayList<>();

        lists.forEach(map->{
            ReportBranchVO branchVO = new ReportBranchVO();
            branchVO.setBranch(map.get("branch"));
            branchVO.setBranchValue(map.get("branchValue"));
            branchVOS.add(branchVO);
        });
        respVOS.setBranches(branchVOS);
        return Response.success(respVOS);
    }

    // ==================== 内部工具方法：SQL 处理 ====================

    /**
     * 将 SQL 模板中的 #{param} 占位符替换为 'value'（带单引号，自动转义）
     *
     * 为什么需要"强制加引号"：
     * - 汇总配置的入参（branch/startDate/endDate/type/modelName）都是字符串类型
     * - 如果不加引号，SQL 会把字符串值当成列名，导致语法错误
     * - 示例：branch = 北京 → 报错（因为北京会被当成字段名）
     *
     * 安全措施：
     * - 单引号转义：' 替换为 ''（SQL 标准转义）
     * - 参数校验：所有占位符必须有值，不允许为空
     */
    private static String buildSqlWithQuotedParams(String sqlTemplate, Map<String, String> params, String modelName) {
        if (!StringUtils.hasText(sqlTemplate)) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_QUERY_SQL_MISSING, modelName);
        }
        if (params == null) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_SUMMARY_PARAM_MISSING, "params，modelName=" + modelName);
        }

        // 使用正则提取模板中的占位符参数名
        Set<String> keys = new LinkedHashSet<>();
        Pattern pattern = Pattern.compile("#\\{([^}]+)}");
        java.util.regex.Matcher m = pattern.matcher(sqlTemplate);
        while (m.find()) {
            String k = m.group(1);
            if (k != null) {
                k = k.trim();
                if (!k.isEmpty()) {
                    keys.add(k);
                }
            }
        }

        // 逐个替换占位符
        String out = sqlTemplate;
        for (String k : keys) {
            String v = params.get(k);
            if (!StringUtils.hasText(v)) {
                throw new BizException(ResarchLibraryResponseCodeEnum.BL_SUMMARY_PARAM_MISSING, k + "，modelName=" + modelName);
            }
            // SQL 单引号转义：' → ''
            String escaped = v.replace("'", "''");
            out = out.replace("#{" + k + "}", "'" + escaped + "'");
        }
        return out;
    }

    // ==================== 内部工具方法：预览相关 ====================

    /**
     * 预览请求的上下文对象：封装校验后的请求参数
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

    /**
     * 解析并校验预览请求
     */
    private static PreviewContext parseAndValidatePreviewReq(ReportPreviewReqVO req) {
        String startDate = (req == null) ? null : req.getStartDate();
        String endDate = (req == null) ? null : req.getEndDate();
        String branch = (req == null) ? null : req.getBranch();
        String modelName = (req == null) ? null : req.getModelName();

        if (!StringUtils.hasText(startDate) || !StringUtils.hasText(endDate) || !StringUtils.hasText(branch) || !StringUtils.hasText(modelName)) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_PREVIEW_PARAM_MISSING);
        }
        LocalDate s;
        LocalDate e;
        try {
            s = LocalDate.parse(startDate);
            e = LocalDate.parse(endDate);
        } catch (Exception ex) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_DATE_FORMAT_INVALID);
        }
        if (s.isAfter(e)) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_DATE_RANGE_INVALID);
        }
        return new PreviewContext(startDate, endDate, branch, modelName);
    }

    /**
     * 从数据库读取指定 modelName 的预览模板
     */
    private JSONObject parseViewTemplate(String modelName) {
        ReportConfigDO rc = reportConfigMapper.selectOne(new QueryWrapper<ReportConfigDO>().eq("model_name", modelName));
        if (rc == null) {
            throw new BizException(ResarchLibraryResponseCodeEnum.DC_TEMPLATE_NOT_FOUND, modelName);
        }
        if (!StringUtils.hasText(rc.getFieldContentOldVal())) {
            throw new BizException(ResarchLibraryResponseCodeEnum.DC_VIEW_JSON_EMPTY, modelName);
        }
        try {
            JSONObject viewTpl = JSONObject.parseObject(rc.getFieldContentOldVal());
            if (viewTpl == null) {
                throw new BizException(ResarchLibraryResponseCodeEnum.BL_JSON_CONTENT_EMPTY, modelName);
            }
            return viewTpl;
        } catch (BizException be) {
            throw be;
        } catch (Exception ex) {
            throw new BizException(ResarchLibraryResponseCodeEnum.BL_JSON_FORMAT_INVALID, modelName);
        }
    }

    /**
     * 安全获取 JSON 对象，必填字段不存在则抛异常
     */
    private static JSONObject requireObject(JSONObject parent, String key, ResarchLibraryResponseCodeEnum code, String modelName) {
        if (parent == null) {
            throw new BizException(code, modelName);
        }
        JSONObject obj = parent.getJSONObject(key);
        if (obj == null) {
            throw new BizException(code, modelName);
        }
        return obj;
    }

    /**
     * 构建预览用的全局参数
     */
    private static JSONObject buildPreviewGlobalParams(String startDate, String endDate, String branch) {
        JSONObject gp = new JSONObject();
        gp.put("startDate", startDate);
        gp.put("endDate", endDate);
        gp.put("branch", branch);
        gp.put("startDateCn", toCnDate(startDate));
        gp.put("endDateCn", toCnDate(endDate));
        return gp;
    }

    /**
     * 构建预览标题
     */
    private static String buildPreviewTitle(JSONObject common, JSONObject gp) {
        if (common == null) {
            return "";
        }
        String titleTemplate = common.getString("titleTemplate");
        if (!StringUtils.hasText(titleTemplate)) {
            return "";
        }
        return buildTitleFromTemplate(titleTemplate, gp);
    }

    /**
     * 安全类型转换：Object → List<List<String>>
     */
    @SuppressWarnings("unchecked")
    private static List<List<String>> cast2d(Object o) {
        if (o == null) {
            return Collections.emptyList();
        }
        try {
            return (List<List<String>>) o;
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    // ==================== 内部工具方法：表头处理 ====================

    /**
     * 推断左侧维度层级数
     *
     * 策略：找到最小的 N，使得对所有表头行 r，headLists[r][0..N-1] 都与第0行相等
     * 这是因为左侧维度列在所有表头行中都是相同的值
     */
    private static int inferLeftLevels(List<List<String>> headLists) {
        if (headLists == null || headLists.isEmpty()) {
            return 0;
        }
        List<String> row0 = headLists.get(0);
        if (row0 == null || row0.isEmpty()) {
            return 0;
        }

        int cols = row0.size();
        int rows = headLists.size();
        int n = 0;

        for (int c = 0; c < cols; c++) {
            String base = safeGet(headLists, 0, c);
            if (base == null) {
                base = "";
            }
            boolean sameAll = true;
            for (int r = 0; r < rows; r++) {
                String v = safeGet(headLists, r, c);
                if (v == null) {
                    v = "";
                }
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

    /**
     * 将二维表头转换为树形结构
     *
     * 处理逻辑：
     * 1. 左侧列：直接作为叶子节点（单层）
     * 2. 右侧数据列：按表头行路径构建多层树
     *
     * 同时生成列索引到 pk 的映射（用于数据行转换）
     */
    private static HeaderBuildResult buildTableHeaderTree(List<List<String>> headLists, int leftLevels) {
        List<HeaderNodeVO> headerTree = new ArrayList<>();
        Map<Integer, String> pkByColIndex = new HashMap<>();

        if (headLists == null || headLists.isEmpty() || headLists.get(0) == null) {
            return new HeaderBuildResult(headerTree, pkByColIndex);
        }
        int headerRows = headLists.size();
        int totalCols = headLists.get(0).size();

        for (int c = 0; c < totalCols; c++) {
            pkByColIndex.put(c, pkForIndex(c));
        }

        // 左侧列：单层叶子节点
        for (int c = 0; c < Math.min(leftLevels, totalCols); c++) {
            HeaderNodeVO node = new HeaderNodeVO();
            node.setLabel(safeGet(headLists, 0, c));
            node.setPk(pkByColIndex.get(c));
            headerTree.add(node);
        }

        // 右侧数据列：按路径构建树
        for (int c = leftLevels; c < totalCols; c++) {
            List<String> path = new ArrayList<>();
            for (int r = 0; r < headerRows; r++) {
                path.add(safeGet(headLists, r, c));
            }
            insertPath(headerTree, path, 0, pkByColIndex.get(c));
        }

        return new HeaderBuildResult(headerTree, pkByColIndex);
    }

    /**
     * 递归插入表头路径到树中
     */
    private static void insertPath(List<HeaderNodeVO> siblings, List<String> path, int depth, String pk) {
        if (path == null || path.isEmpty() || depth >= path.size()) {
            return;
        }
        String label = path.get(depth);
        if (label == null) {
            label = "";
        }

        // 查找或创建节点
        HeaderNodeVO node = null;
        for (HeaderNodeVO s : siblings) {
            String l = (s.getLabel() == null) ? "" : s.getLabel();
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

        // 叶子节点：设置 pk
        if (depth == path.size() - 1) {
            node.setPk(pk);
            node.set_children(null);
            return;
        }

        // 非叶子节点：递归处理子节点
        List<HeaderNodeVO> children = node.get_children();
        if (children == null) {
            children = new ArrayList<>();
            node.set_children(children);
        }
        insertPath(children, path, depth + 1, pk);
    }

    /**
     * 将二维数据行转换为行对象（key=pk，value=单元格值）
     */
    private static List<Map<String, String>> buildTableContentList(List<List<String>> dataLists, Map<Integer, String> pkByColIndex) {
        List<Map<String, String>> out = new ArrayList<>();
        if (dataLists == null || dataLists.isEmpty()) {
            return out;
        }

        for (List<String> row : dataLists) {
            Map<String, String> obj = new LinkedHashMap<>();
            if (row != null) {
                for (int c = 0; c < row.size(); c++) {
                    String pk = pkByColIndex.get(c);
                    if (pk == null) {
                        continue;
                    }
                    obj.put(pk, row.get(c));
                }
            }
            out.add(obj);
        }
        return out;
    }

    /**
     * 安全获取二维列表中的值
     */
    private static String safeGet(List<List<String>> m, int r, int c) {
        if (m == null || r < 0 || r >= m.size()) {
            return "";
        }
        List<String> row = m.get(r);
        if (row == null || c < 0 || c >= row.size()) {
            return "";
        }
        String v = row.get(c);
        return (v == null) ? "" : v;
    }

    /**
     * 将列索引转换为 Excel 列标识（a, b, ..., z, aa, ab, ...）
     */
    private static String pkForIndex(int idx) {
        int n = idx;
        StringBuilder sb = new StringBuilder();
        do {
            int rem = n % 26;
            sb.append((char) ('a' + rem));
            n = n / 26 - 1;
        } while (n >= 0);
        return sb.reverse().toString();
    }

    /**
     * 表头构建结果封装
     */
    private static class HeaderBuildResult {

        final List<HeaderNodeVO> headerTree;
        final Map<Integer, String> pkByColIndex;

        HeaderBuildResult(List<HeaderNodeVO> headerTree, Map<Integer, String> pkByColIndex) {
            this.headerTree = headerTree;
            this.pkByColIndex = pkByColIndex;
        }
    }

    /**
     * Zip 打包用的分行 Excel 封装（文件名 + 字节数组）
     */
    private static class BranchExcel {
        final String entryName;
        final byte[] bytes;

        BranchExcel(String entryName, byte[] bytes) {
            this.entryName = entryName;
            this.bytes = bytes;
        }
    }

    // ==================== 内部工具方法：Excel 导出 ====================

    /**
     * 生成单个分行的 Excel 文件
     *
     * 流程：
     * 1. 遍历每个 config，调用 DynamicReportGenerator 生成报表数据
     * 2. 横向合并多个 config 的报表数据
     * 3. 使用 SmartExcelExporter 生成 Excel bytes
     *
     * @param branch   分行名称
     * @param gp       全局参数（日期、机构等）
     * @param common   公共配置
     * @param configs  业务配置数组
     * @return Excel 文件字节数组
     */
    private byte[] buildExcelForBranch(String branch, JSONObject gp, JSONObject common, JSONArray configs) throws IOException {
        int dataHeaderRowIndex = common.getIntValue("dataHeaderRowIndex");
        String titleTemplate = common.getString("titleTemplate");
        JSONArray leftDimensions = common.getJSONArray("leftDimensions");

        // 调试日志：验证 common 和 leftDimensions 的状态
        log.info("[{}] buildExcelForBranch 开始，common identity={}, leftDimensions identity={}, leftDimensions.size={}",
                branch, System.identityHashCode(common), System.identityHashCode(leftDimensions),
                leftDimensions != null ? leftDimensions.size() : 0);

        // 逐个 config 生成报表数据
        List<Map<String, Object>> reportMaps = new ArrayList<>();
        for (int i = 0; i < configs.size(); i++) {
            JSONObject cfg = configs.getJSONObject(i);
            if (cfg == null) {
                continue;
            }

            // 深拷贝整个 config，避免数据污染
            JSONObject cfgCopy = JSONObject.parseObject(cfg.toJSONString());
            
            // 深拷贝 leftDimensions（必须完全独立，因为第一列的行数由它决定）
            JSONArray leftDimsCopy = new JSONArray();
            JSONArray leftDimsOriginal = common.getJSONArray("leftDimensions");
            if (leftDimsOriginal != null) {
                for (Object dimObj : leftDimsOriginal) {
                    if (dimObj instanceof JSONObject) {
                        leftDimsCopy.add(JSONObject.parseObject(((JSONObject) dimObj).toJSONString()));
                    } else {
                        leftDimsCopy.add(dimObj);
                    }
                }
            }
            cfgCopy.put("leftDimensions", leftDimsCopy);
            
            // 深拷贝 topDimensions（如果有的话）
            JSONArray topDimsOriginal = cfgCopy.getJSONArray("topDimensions");
            if (topDimsOriginal != null) {
                JSONArray topDimsCopy = new JSONArray();
                for (Object dimObj : topDimsOriginal) {
                    if (dimObj instanceof JSONObject) {
                        topDimsCopy.add(JSONObject.parseObject(((JSONObject) dimObj).toJSONString()));
                    } else {
                        topDimsCopy.add(dimObj);
                    }
                }
                cfgCopy.put("topDimensions", topDimsCopy);
            }
            
            // 深拷贝 metrics（如果有的话）
            JSONArray metricsOriginal = cfgCopy.getJSONArray("metrics");
            if (metricsOriginal != null) {
                JSONArray metricsCopy = new JSONArray();
                for (Object mObj : metricsOriginal) {
                    if (mObj instanceof JSONObject) {
                        metricsCopy.add(JSONObject.parseObject(((JSONObject) mObj).toJSONString()));
                    } else {
                        metricsCopy.add(mObj);
                    }
                }
                cfgCopy.put("metrics", metricsCopy);
            }
            
            cfgCopy.put("dataHeaderRowIndex", dataHeaderRowIndex);
            cfgCopy.put("titleTemplate", titleTemplate);
            cfgCopy.put("globalParams", gp);

            try {
                log.info(">>> [{}] 开始处理第 {} 个配置，leftDimsCopy.size={}", gp.getString("branch"), i, leftDimsCopy.size());
                Map<String, Object> reportMap = dynamicReportGenerator.generateReportData(cfgCopy.toJSONString());
                log.info(">>> [{}] 第 {} 个配置完成，dataLists.size={}", gp.getString("branch"), i, 
                        ((List<?>)reportMap.get("dataLists")).size());
                reportMaps.add(reportMap);
            } catch (Exception ex) {
                throw new BizException(ResarchLibraryResponseCodeEnum.AT_REPORT_GENERATE_FAILED, "业务[" + gp.getString("branch") + "]报表：" + ex.getMessage());
            }
        }

        // 横向合并多个报表
        Map<String, Object> merged = mergeReportsSideBySide(reportMaps);
        merged.put("topDimCount", dataHeaderRowIndex);

        // 读取 sheet 名称模板
        String sheetNameTemplate = common.getString("sheetNameTemplate");
        String sheetName = StringUtils.hasText(sheetNameTemplate)
                ? buildTitleFromTemplate(sheetNameTemplate, gp)
                : "Sheet1";

        String title = buildTitleFromTemplate(titleTemplate, gp);

        // 生成 Excel
        try {
            return SmartExcelExporter.buildExcelBytesWithHeaderAreaMerge(
                    (List<List<String>>) merged.get("headLists"),
                    (List<List<String>>) merged.get("dataLists"),
                    sheetName,
                    (Integer) merged.get("topDimCount"),
                    (Integer) merged.get("leftDimCount"),
                    title
            );
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception ex) {
            throw new BizException(ResarchLibraryResponseCodeEnum.AT_EXCEL_CREATE_FAILED, ex.getMessage());
        }
    }

    /**
     * 横向拼接多个业务品种报表
     *
     * 合并策略：
     * - 左侧维度列：使用第一个报表的左侧列（所有报表左侧维度相同）
     * - 右侧数据列：将后续报表的右侧列追加到第一个报表的右侧
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

        // 以第一个报表为基础
        Map<String, Object> first = reportMaps.get(0);
        List<List<String>> mergedHeads = deepCopy2D((List<List<String>>) first.get("headLists"));
        List<List<String>> mergedData = deepCopy2D((List<List<String>>) first.get("dataLists"));
        int leftDimCount = (Integer) first.getOrDefault("leftDimCount", 0);

        // 追加后续报表的右侧列
        for (int i = 1; i < reportMaps.size(); i++) {
            Map<String, Object> cur = reportMaps.get(i);
            if (cur == null) {
                continue;
            }

            List<List<String>> curHeads = (List<List<String>>) cur.get("headLists");
            List<List<String>> curData = (List<List<String>>) cur.get("dataLists");

            // 合并表头
            int headRows = Math.min(mergedHeads.size(), (curHeads == null) ? 0 : curHeads.size());
            for (int r = 0; r < headRows; r++) {
                List<String> srcRow = curHeads.get(r);
                if (srcRow == null || srcRow.size() <= leftDimCount) {
                    continue;
                }
                mergedHeads.get(r).addAll(srcRow.subList(leftDimCount, srcRow.size()));
            }

            // 合并数据
            int dataRows = Math.min(mergedData.size(), (curData == null) ? 0 : curData.size());
            for (int r = 0; r < dataRows; r++) {
                List<String> srcRow = curData.get(r);
                if (srcRow == null || srcRow.size() <= leftDimCount) {
                    continue;
                }
                mergedData.get(r).addAll(srcRow.subList(leftDimCount, srcRow.size()));
            }
        }

        Map<String, Object> res = new HashMap<>();
        res.put("headLists", mergedHeads);
        res.put("dataLists", mergedData);
        res.put("leftDimCount", leftDimCount);
        return res;
    }

    /**
     * 深度拷贝二维列表
     */
    private static List<List<String>> deepCopy2D(List<List<String>> src) {
        if (src == null) {
            return new ArrayList<>();
        }
        List<List<String>> out = new ArrayList<>(src.size());
        for (List<String> row : src) {
            out.add((row == null) ? new ArrayList<>() : new ArrayList<>(row));
        }
        return out;
    }

    // ==================== 内部工具方法：通用 ====================

    /**
     * 构建全局参数（用于模板替换）
     */
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

    /**
     * 将日期字符串转换为中文格式（如 2024-01-15 → 2024年1月15日）
     */
    private static String toCnDate(String yyyyMmDd) {
        int[] ymd = parseYmd(yyyyMmDd);
        if (ymd == null) {
            return yyyyMmDd;
        }
        return ymd[0] + "年" + ymd[1] + "月" + ymd[2] + "日";
    }

    /**
     * 解析日期字符串为年月日数组
     * 支持分隔符：- 或 /
     */
    private static int[] parseYmd(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        if (t.isEmpty()) {
            return null;
        }
        String[] parts = t.split("[-/]");
        if (parts.length < 3) {
            return null;
        }
        try {
            int y = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            int d = Integer.parseInt(parts[2]);
            return new int[]{y, m, d};
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 将模板中的 #{变量名} 替换为实际值
     * 特殊处理：branch 非"总行"时自动拼接"分行"后缀
     *
     * 示例：
     * 模板："#{startDateCn}至#{endDateCn}#{branch}报表"
     * 参数：{"startDateCn": "2024年1月1日", "endDateCn": "2024年1月31日", "branch": "北京"}
     * 结果："2024年1月1日至2024年1月31日北京分行报表"
     *
     * 特殊处理：
     * - branch = "总行" → 不拼接"分行"后缀
     * - branch = 其他 → 自动拼接"分行"后缀
     */
    private static String buildTitleFromTemplate(String template, JSONObject globalParams) {
        if (!StringUtils.hasText(template) || globalParams == null || globalParams.isEmpty()) {
            return template;
        }
        String t = template;
        for (Map.Entry<String, Object> e : globalParams.entrySet()) {
            if (e.getKey() == null || e.getValue() == null) {
                continue;
            }
            t = t.replace("#{" + e.getKey() + "}", String.valueOf(e.getValue()));
        }
        // branch 非"总行"时自动拼接"分行"后缀
        String branch = globalParams.getString("branch");
        if (template.contains("#{branch}") && !"总行".equals(branch)) {
            t = t.replace(branch, branch + "分行");
        }
        return t;
    }

    /**
     * 写入 Excel 响应
     */
    private static void writeExcelResponse(HttpServletResponse response, byte[] bytes, String fileName) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" +
                URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20"));
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
    }

    // ==================== 内部工具方法：模板解析 ====================

    /**
     * 解析并校验模板配置
     *
     * 校验规则：
     * - 每个模板的 req_para_val 必须非空
     * - 每个模板必须包含 common 和 config
     * - 所有模板的 common 配置必须一致
     *
     * 注意：为避免多线程并发时数据污染，必须对 JSON 对象进行深拷贝
     *
     * @return common 配置和 config 数组
     */
    private static TemplateBundle parseAndValidateTemplates(List<ReportConfigDO> templates) {
        JSONObject commonTemplate = null;
        JSONArray configs = new JSONArray();

        for (ReportConfigDO rc : templates) {
            if (rc == null) {
                continue;
            }

            if (!StringUtils.hasText(rc.getReqParaVal())) {
                throw new BizException(ResarchLibraryResponseCodeEnum.BL_EXPORT_CONFIG_MISSING, rc.getModelName());
            }
            // 深拷贝：每次解析都创建全新的对象，避免多线程共享引用
            JSONObject tpl = JSONObject.parseObject(rc.getReqParaVal());
            JSONObject c = tpl.getJSONObject("common");
            JSONObject cfg = tpl.getJSONObject("config");
            if (c == null || cfg == null) {
                throw new BizException(ResarchLibraryResponseCodeEnum.BL_EXPORT_JSON_STRUCTURE_INVALID, rc.getModelName());
            }

            if (commonTemplate == null) {
                // 深拷贝 common（避免后续修改影响模板）
                commonTemplate = JSONObject.parseObject(c.toJSONString());
            } else if (!commonTemplate.equals(c)) {
                throw new BizException(ResarchLibraryResponseCodeEnum.BL_TEMPLATE_COMMON_INCONSISTENT, rc.getModelName());
            }
            // 深拷贝 config（每个分支需要独立的 config 对象）
            configs.add(JSONObject.parseObject(cfg.toJSONString()));
        }

        if (commonTemplate == null) {
            throw new BizException(ResarchLibraryResponseCodeEnum.AT_TEMPLATE_COMMON_EMPTY);
        }
        return new TemplateBundle(commonTemplate, configs);
    }

    /**
     * 模板包：封装 common 和 configs
     */
    private static class TemplateBundle {

        final JSONObject common;
        final JSONArray configs;

        TemplateBundle(JSONObject common, JSONArray configs) {
            this.common = common;
            this.configs = configs;
        }
    }
}
