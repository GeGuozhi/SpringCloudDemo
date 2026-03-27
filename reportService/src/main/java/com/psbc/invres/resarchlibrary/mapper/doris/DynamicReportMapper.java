package com.psbc.invres.resarchlibrary.mapper.doris;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
@Slf4j
@Repository
public class DynamicReportMapper {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 执行任意 SELECT 语句（仅限读），返回列名到值的 Map 列表。
     *
     * @param sql 完整的 SELECT 语句（已在调用方完成参数替换）
     * @return 列名到值的 Map 列表，key 为列名，value 为列值（包含 null）
     */
    public List<Map<String, Object>> executeSelect(String sql) {
        return jdbcTemplate.queryForList(sql);
    }
}
