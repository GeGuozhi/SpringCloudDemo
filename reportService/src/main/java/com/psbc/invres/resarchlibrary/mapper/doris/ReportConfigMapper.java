package com.psbc.invres.resarchlibrary.mapper.doris;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psbc.invres.resarchlibrary.controller.excelconfig.vo.ReportConfigDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("pgidx")
public interface ReportConfigMapper extends BaseMapper<ReportConfigDO> {
}