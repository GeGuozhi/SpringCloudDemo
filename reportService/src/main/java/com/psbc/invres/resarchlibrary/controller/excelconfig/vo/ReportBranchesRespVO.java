package com.psbc.invres.resarchlibrary.controller.excelconfig.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分行查询响应参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分行查询响应参数")
public class ReportBranchesRespVO {

    @ApiModelProperty(value = "动态查询分行返回list")
    private List<ReportBranchVO> branches;
}
