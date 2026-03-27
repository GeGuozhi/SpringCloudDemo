package com.psbc.invres.resarchlibrary.controller.excelconfig.vo;

import com.psbc.invres.resarchlibrary.enums.SummaryTimeType;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * 汇总页面中单个业务品种的统计块
 */
public class SummaryItemVO {

    @ApiModelProperty("业务品种标识，对应 report_config.model_name")
    private String modelName;

    @ApiModelProperty("时间类型：POINT=时点，RANGE=区间")
    private SummaryTimeType summaryType;

    @ApiModelProperty("是否为汇总列表中的第一行（前端可用于特殊展示）")
    private boolean firstRow;

    @ApiModelProperty("原始统计结果 JSON（通常包含 rows、types 等键，结构由后端约定）")
    private Map<String, Object> summaryJson;

    @ApiModelProperty("按 types 展开的明细数据列表，元素为列名到字符串值的映射")
    private List<Map<String, String>> dataList;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public SummaryTimeType getSummaryType() {
        return summaryType;
    }

    public void setSummaryType(SummaryTimeType summaryType) {
        this.summaryType = summaryType;
    }

    public boolean isFirstRow() {
        return firstRow;
    }

    public void setFirstRow(boolean firstRow) {
        this.firstRow = firstRow;
    }

    public Map<String, Object> getSummaryJson() {
        return summaryJson;
    }

    public void setSummaryJson(Map<String, Object> summaryJson) {
        this.summaryJson = summaryJson;
    }

    public List<Map<String, String>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, String>> dataList) {
        this.dataList = dataList;
    }
}
