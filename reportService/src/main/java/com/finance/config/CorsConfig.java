package com.finance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 仅用于本地联调：
 * - 允许静态 html（含 file://）直接调用本服务的 /api/** 接口
 * - 生产环境请按实际域名收敛 allowedOriginPatterns
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Content-Disposition", "Content-Type")
                .allowCredentials(false)
                .maxAge(3600);
    }
}

