package com.feign.client;

import org.springframework.stereotype.Component;

@Component
public class OpenFeignClient1Hystrix implements OpenFeignClient1{
    @Override
    public String getPort() {
        return "暂时不可用";
    }
}
