package com.psbc.invres.resarchlibrary.controller.excelconfig.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ggz on 2026/3/30
 */
@Data
public class ReportBranchVO {
    @ApiModelProperty("分行实际值")
    private String branch;

    @ApiModelProperty("分行value")
    private String branchValue;
}