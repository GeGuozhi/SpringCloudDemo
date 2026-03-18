package com.finance.entity.req;

import io.swagger.annotations.ApiModelProperty;

/**
 * 汇总请求参数
 */
public class ReportSummaryReqVO {
    @ApiModelProperty(value = "开始日期，格式 yyyy-MM-dd", required = true, example = "2025-07-01")
    private String startDate;
    @ApiModelProperty(value = "结束日期，格式 yyyy-MM-dd", required = true, example = "2025-09-30")
    private String endDate;
    @ApiModelProperty(value = "分行名称（单个）", required = true, example = "北京分行")
    private String branch;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}

