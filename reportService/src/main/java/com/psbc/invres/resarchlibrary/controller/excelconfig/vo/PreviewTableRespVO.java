package com.psbc.invres.resarchlibrary.controller.excelconfig.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * 预览接口返回体：树形表头 + 行数据（同时附带原始二维 head/data 方便排查）
 */
public class PreviewTableRespVO {

    @ApiModelProperty("模板名称（report_config.model_name）")
    private String modelName;

    @ApiModelProperty("标题（由 titleTemplate 替换得到；若未配置 titleTemplate 则为空字符串）")
    private String title;

    @ApiModelProperty("树形表头：每个叶子节点带 pk；多级表头用 _children 递归表示")
    private List<HeaderNodeVO> tableHeaderList;

    @ApiModelProperty("表内容：每一行是一个对象，key=pk（a/b/c...），value=单元格内容")
    private List<Map<String, String>> tableContentList;

    @ApiModelProperty("原始二维表头（调试用）：List<List<String>>")
    private List<List<String>> headLists;

    @ApiModelProperty("原始二维数据（调试用）：List<List<String>>")
    private List<List<String>> dataLists;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HeaderNodeVO> getTableHeaderList() {
        return tableHeaderList;
    }

    public void setTableHeaderList(List<HeaderNodeVO> tableHeaderList) {
        this.tableHeaderList = tableHeaderList;
    }

    public List<Map<String, String>> getTableContentList() {
        return tableContentList;
    }

    public void setTableContentList(List<Map<String, String>> tableContentList) {
        this.tableContentList = tableContentList;
    }

    public List<List<String>> getHeadLists() {
        return headLists;
    }

    public void setHeadLists(List<List<String>> headLists) {
        this.headLists = headLists;
    }

    public List<List<String>> getDataLists() {
        return dataLists;
    }

    public void setDataLists(List<List<String>> dataLists) {
        this.dataLists = dataLists;
    }
}
