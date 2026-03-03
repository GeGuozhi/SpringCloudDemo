package com.ggz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OauthServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(OauthServerApplication.class).run(args);
    }
}