package com.ggz.chat;

import brave.sampler.Sampler;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * 分布式聊天服务
 * 
 * @author ggz
 */
@SpringBootApplication
@EnableEurekaClient
public class ChatServiceApplication {
    public static void main(String[] args) {
        
        System.out.println("==========================================");
        System.out.println("启动聊天服务");
        System.out.println("服务端口: " + 8083);
        System.out.println("访问地址: http://localhost:" + 8083);
        System.out.println("==========================================");
        
        new SpringApplicationBuilder(ChatServiceApplication.class)
                .properties("server.port=" + 8083)
                .run(args);
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}

