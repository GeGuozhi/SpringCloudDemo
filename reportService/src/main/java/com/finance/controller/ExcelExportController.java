package com.finance.controller;

/**
 * @author ggz on 2026/3/9
 */

import com.finance.entity.req.ExportWithMergeReqVO;
import com.finance.entity.req.ReportPreviewReqVO;
import com.finance.entity.req.ReportSummaryReqVO;
import com.finance.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.finance.entity.Response;
import com.finance.entity.resp.PreviewTableRespVO;
import com.finance.entity.resp.SummaryOverviewRespVO;

/**
 * Excel 导出 Controller - 支持自动合并
 */
@RestController
@RequestMapping("/api/excel")
public class ExcelExportController {

    @Autowired
    private ReportService reportService;

    /**
     * 导出带有自动合并功能的 Excel
     * GET /api/excel/export-with-merge
     */
    /**
     * 导出（单分行 -> Excel；多分行 -> Zip）
     *
     * @param branches 逗号分隔分行名称，如 "北京,上海"
     */
    @PostMapping("/export-with-merge")
    public void exportWithMerge(
            @RequestBody ExportWithMergeReqVO req,
            HttpServletResponse response) throws IOException {
        reportService.exportMerged(req, response);
    }

    @PostMapping("/preview")
    public Response<PreviewTableRespVO> preview(@RequestBody ReportPreviewReqVO req) {
        return reportService.preview(req);
    }

    /**
     * 汇总查询接口：
     * - 入参：branch/startDate/endDate（query 参数）
     * - 返回：所有配置了汇总 JSON(field_content_new_val) 的业务品种统计结果
     */
    @PostMapping("/summary")
    public Response<SummaryOverviewRespVO> summary(@RequestBody ReportSummaryReqVO req) {
        return reportService.summary(req);
    }
}
