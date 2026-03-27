package com.psbc.invres.resarchlibrary.controller;

import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ExportWithMergeReqVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.PreviewTableRespVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportPreviewReqVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportSummaryReqVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.SummaryOverviewRespVO;
import com.psbc.invres.resarchlibrary.entity.Response;
import com.psbc.invres.resarchlibrary.service.excelconfig.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Excel 导出 Controller - 支持自动合并
 */
@Api(tags = "Excel报表导出")
@RestController
@RequestMapping("/api/excel")
public class ExcelExportController {

    @Autowired
    private ReportService reportService;

    /**
     * 导出（单分行 -> Excel；多分行 -> Zip）
     *
     * @param req 请求体，包含 branches（逗号分隔分行）、startDate、endDate
     */
    @ApiOperation(value = "导出报表", notes = "单分行导出为 Excel，多分行导出为 Zip 包。\n" +
            "请求参数 branches 支持逗号分隔多个分行名称，例如 \"北京,上海\"。\n" +
            "返回文件流，Content-Type 根据分支数量自动设置为 application/vnd.openxmlformats-officedocument.spreadsheetml.sheet 或 application/zip。")
    @PostMapping("/export-with-merge")
    public void exportWithMerge(
            @RequestBody ExportWithMergeReqVO req,
            HttpServletResponse response) throws IOException {
        reportService.exportMerged(req, response);
    }

    /**
     * 预览报表：根据 modelName 生成预览数据
     *
     * @param req 请求体，包含 startDate、endDate、branch、modelName
     */
    @ApiOperation(value = "预览报表", notes = "根据指定的模板名称（modelName）生成报表预览数据。\n" +
            "返回二维表头（treeHeaderList）和行数据（tableContentList），便于前端渲染。\n" +
            "模板数据从 report_config 表的 field_content_old_val 字段读取。")
    @PostMapping("/preview")
    public Response<PreviewTableRespVO> preview(@RequestBody ReportPreviewReqVO req) {
        return reportService.preview(req);
    }

    /**
     * 汇总查询：查询各业务品种的汇总数据
     *
     * @param req 请求体，包含 branch、startDate、endDate
     */
    @ApiOperation(value = "汇总查询", notes = "按指定机构（branch）和日期范围（startDate/endDate）统计所有已配置汇总 JSON 的业务品种。\n" +
            "返回各业务品种的汇总结果，每条包含 modelName、summaryType（POINT=时点/RANGE=区间）以及明细数据列表。\n" +
            "只返回 field_content_new_val 非空的业务品种。")
    @PostMapping("/summary")
    public Response<SummaryOverviewRespVO> summary(@RequestBody ReportSummaryReqVO req) {
        return reportService.summary(req);
    }
}
