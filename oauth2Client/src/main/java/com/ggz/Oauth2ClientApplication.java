package com.ggz;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Oauth2ClientApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Oauth2ClientApplication.class).run(args);
    }
}