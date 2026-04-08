package com.psbc.invres.resarchlibrary.service.excelconfig;

import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ExportWithMergeReqVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.PreviewTableRespVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportBranchesReqVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportBranchesRespVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportPreviewReqVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportSummaryReqVO;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.SummaryOverviewRespVO;
import com.psbc.invres.resarchlibrary.entity.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    /**
     * 查询分行列表
     * @param req 请求参数，包含 branchName（可选，模糊匹配）
     * @return 分行列表，每条包含 branch 和 branchValue
     */
    Response<ReportBranchesRespVO> searchBranches(ReportBranchesReqVO req);
}
