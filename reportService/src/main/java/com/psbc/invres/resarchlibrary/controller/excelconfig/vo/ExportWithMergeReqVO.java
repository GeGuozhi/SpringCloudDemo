package com.psbc.invres.resarchlibrary.controller.excelconfig.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * /api/excel/export-with-merge 请求参数
 */
public class ExportWithMergeReqVO {

    @ApiModelProperty(value = "分行名称，多个用逗号分隔（例如：北京分行,上海分行）", required = true)
    private String branches;

    @ApiModelProperty(value = "开始日期，格式 yyyy-MM-dd", required = true, example = "2025-07-01")
    private String startDate;

    @ApiModelProperty(value = "结束日期，格式 yyyy-MM-dd", required = true, example = "2025-09-30")
    private String endDate;

    public String getBranches() {
        return branches;
    }

    public void setBranches(String branches) {
        this.branches = branches;
    }

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
}
