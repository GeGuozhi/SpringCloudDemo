package com.ggz;

import brave.sampler.Sampler;
import cn.hutool.core.net.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class ProviderApplication {
    public static void main(String[] args) {
        int port = 8010;
        if (!NetUtil.isUsableLocalPort(8010)) {
            port = 8011;
            if(!NetUtil.isUsableLocalPort(8011)){
                port = 8012;
            }
        }
        new SpringApplicationBuilder(ProviderApplication.class).properties("server.port=" + port).run(args);
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
