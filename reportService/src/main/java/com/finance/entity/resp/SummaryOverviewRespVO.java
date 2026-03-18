package com.finance.entity.resp;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 汇总查询接口的返回对象
 */
public class SummaryOverviewRespVO {

    @ApiModelProperty("汇总结果 JSON 列表：每个元素对应一个 report_config.model_name 的汇总块")
    private List<JSONObject> jsons;

    public List<JSONObject> getJsons() {
        return jsons;
    }

    public void setJsons(List<JSONObject> jsons) {
        this.jsons = jsons;
    }
}

