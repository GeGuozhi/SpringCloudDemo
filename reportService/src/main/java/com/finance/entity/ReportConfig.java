package com.finance.entity;

/**
 * @author ggz on 2026/3/9
 */
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("report_config")
public class ReportConfig {
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