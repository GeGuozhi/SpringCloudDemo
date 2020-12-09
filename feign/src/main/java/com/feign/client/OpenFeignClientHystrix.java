package com.feign.client;

import org.springframework.stereotype.Component;

@Component
public class OpenFeignClientHystrix implements OpenFeignClient{
    @Override
    public String getPort() {
        return "暂时不可用";
    }
}
