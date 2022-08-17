package com.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * provider1 : 服务名，服务在eureka服务注册中心中注册的服务名。
 * /serviceNumberOne/providerNumberOne  对于的服务的请求名。
 */
@FeignClient(value = "provider1",fallback = OpenFeignClient1Hystrix.class)
public interface OpenFeignClient1 {

    @GetMapping("/serviceNumberOne/providerNumberOne")
    public String getPort();
}
