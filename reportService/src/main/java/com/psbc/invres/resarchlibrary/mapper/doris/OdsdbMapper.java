package com.psbc.invres.resarchlibrary.mapper.doris;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
@DS("odsdb")
public interface OdsdbMapper {

    /**
     * 执行任意 SELECT 语句（仅限读），返回列名到值的 Map 列表。
     * 说明：SQL 语句已在上层通过 SqlBuilder 替换完占位符。
     */
    List<Map<String, String>> searchBranches(@Param("sql") String sql);
}
