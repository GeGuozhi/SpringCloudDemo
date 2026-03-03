package com.ggz.TestPackage.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EurekaClientController {
    @PostMapping("EurekaClient")
    public String service() {
        return "eurekaClient Service";
    }
}
