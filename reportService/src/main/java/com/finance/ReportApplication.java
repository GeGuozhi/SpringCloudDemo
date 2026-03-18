package com.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

// 使用 dynamic-datasource 接管数据源创建，避免 Spring Boot 默认 DataSourceAutoConfiguration 走 Hikari 兜底并报错
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReportApplication.class, args);
    }

    // 【修改点】：删除了 dataSourceInitializer 方法！
    // Spring Boot 会自动检测 src/main/resources/schema.sql 并执行。
    // 我们只需要保留 JdbcTemplate 的 Bean (或者直接注入自动配置的 JdbcTemplate)

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}