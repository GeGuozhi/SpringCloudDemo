package com.ggz.TestPackage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EurekaClientController {
    @GetMapping("EurekaClient")
    public String service() {
        return "eurekaClient Service";
    }
}
