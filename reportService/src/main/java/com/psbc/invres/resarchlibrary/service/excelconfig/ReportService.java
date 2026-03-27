package com.psbc.invres.resarchlibrary.service.excelconfig;

import com.psbc.invres.resarchlibrary.entity.Response;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ExportWithMergeReqVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportPreviewReqVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportSummaryReqVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.PreviewTableRespVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.SummaryOverviewRespVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
