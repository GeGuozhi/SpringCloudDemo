package com.ggz.configserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ggz on 2025/4/1
 */
@RestController
public class TestController {
    @Value("${bobo.user.name}")
    private String userName;

    @Value("${bobo.user.age}")
    private String age;

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/query")
    public String query() {
        return "userName:" + userName + ",age:" + age + ",appName:" + appName;
    }
}