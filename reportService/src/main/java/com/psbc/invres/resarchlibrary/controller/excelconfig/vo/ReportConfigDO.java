package com.psbc.invres.resarchlibrary.controller.excelconfig.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ggz on 2026/3/9
 */
@Data
@TableName("report_config")
public class ReportConfigDO {

    @TableId
    private String prKeyId;

    /** 导出配置JSON */
    private String reqParaVal;

    /** 查看配置JSON */
    private String fieldContentOldVal;

    /** 汇总配置JSON */
    private String fieldContentNewVal;

    private LocalDateTime updStamp;

    private LocalDateTime createStamp;

    private String modelName;
}
