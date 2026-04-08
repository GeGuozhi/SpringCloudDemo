package com.psbc.invres.resarchlibrary.controller.excelconfig.vo;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author ggz on 2026/3/27
 */
@Data
public class TestReqVO {
    @Size(max = 50,message = "知识库长度不能超过50")
    String knowLedgeName;
}