package com.psbc.invres.resarchlibrary.controller.excelconfig.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分行查询请求参数
 */
@Data
@ApiModel(description = "分行查询请求参数")
public class ReportBranchesReqVO {

    @ApiModelProperty(value = "分行名称（模糊匹配），不传则查询全部")
    private String branchName;
}
