package com.ggz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@SpringBootApplication
@EnableEurekaClient
public class OauthServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(OauthServerApplication.class).run(args);
    }
}
