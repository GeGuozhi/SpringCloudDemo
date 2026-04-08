package com.psbc.invres.resarchlibrary.mapper.doris;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 通用动态查询 Mapper：
 * - 使用 Spring JdbcTemplate 执行任意 SELECT 语句，返回 List<Map<String,Object>>
 * - 解决 MyBatis XML 中使用 ${sql} 动态 SQL 时，Map key 为空的问题
 *   （MyBatis 的 MapResultSetHandler 在 ${sql} 场景下依赖 ResultSetMetaData.getColumnLabel()，
 *    MySQL 驱动默认不启用 information_schema，导致列名为空字符串，且 null 值被过滤）
 * - JdbcTemplate 直接使用 ResultSetMetaData.getColumnName() 获取列名，行为更稳定
 */
/**
 * 通用动态查询 Mapper：
 * - 使用 MyBatis 执行任意 SELECT 语句，返回 List<Map<String,Object>>
 * - 未来如果需要多数据源，可以在实现它的 Service 上加 @DS("xxx")
 */
@Mapper
@DS("dwsdb")
public interface DynamicReportMapper {

    /**
     * 执行任意 SELECT 语句（仅限读），返回列名到值的 Map 列表。
     * 说明：SQL 语句已在上层通过 SqlBuilder 替换完占位符。
     */
    List<Map<String, Object>> executeSelect(@Param("sql") String sql);
}
