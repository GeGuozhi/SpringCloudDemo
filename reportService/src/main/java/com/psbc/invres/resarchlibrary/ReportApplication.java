package com.psbc.invres.resarchlibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 使用 dynamic-datasource 接管数据源创建，
 * 避免 Spring Boot 默认 DataSourceAutoConfiguration 走 Hikari 兜底并报错
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportApplication.class, args);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
