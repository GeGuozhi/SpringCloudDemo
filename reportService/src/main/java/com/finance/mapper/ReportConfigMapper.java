package com.finance.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.finance.entity.ReportConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("pgidx")
public interface ReportConfigMapper extends BaseMapper<ReportConfig> {
}