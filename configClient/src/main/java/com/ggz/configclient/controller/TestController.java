package com.ggz.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractList;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;

/**
 * @author ggz on 2025/4/1
 */
@RefreshScope
@RestController
public class TestController {

    @Value("${version}")
    private String version;

    @Value("${bobo.user.name}")
    private String userName;

    @Value("${bobo.user.age}")
    private String age;

    @RequestMapping("/version")
    public String version() {
        return version;
    }

    @PostMapping("/query")
    public String query() {
        return "userName:" + userName + ",age:" + age;
    }

}