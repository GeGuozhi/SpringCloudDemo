package com.psbc.invres.resarchlibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 报表导出专用线程池：用于多个分行 Excel 的并行生成
 */
@Configuration
public class ExportExecutorConfig {

    @Bean
    public ThreadPoolExecutor exportExecutor() {
        return new ThreadPoolExecutor(
                4,
                8,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
