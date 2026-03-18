package com.finance.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.finance.entity.req.ExportWithMergeReqVO;
import com.finance.entity.req.ReportPreviewReqVO;
import com.finance.entity.Response;
import com.finance.entity.req.ReportSummaryReqVO;
import com.finance.entity.resp.PreviewTableRespVO;
import com.finance.entity.resp.SummaryOverviewRespVO;

public interface ReportService {
    void exportMerged(ExportWithMergeReqVO req, HttpServletResponse response) throws IOException;

    /**
     * 预览：返回树形表头（tableHeaderList）与行数据对象（tableContentList）
     */
    Response<PreviewTableRespVO> preview(ReportPreviewReqVO req);

    /**
     * 汇总页面查询：按 branch+startDate+endDate 统计所有已配置汇总 JSON 的业务品种
     */
    Response<SummaryOverviewRespVO> summary(ReportSummaryReqVO req);
}