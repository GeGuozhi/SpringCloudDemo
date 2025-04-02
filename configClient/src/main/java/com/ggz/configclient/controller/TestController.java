package com.ggz.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ggz on 2025/4/1
 */
@RefreshScope
@RestController
public class TestController {

    @Value("${version}")
    private String version;

    @RequestMapping("/version")
    public String version() {
        return version;
    }
}