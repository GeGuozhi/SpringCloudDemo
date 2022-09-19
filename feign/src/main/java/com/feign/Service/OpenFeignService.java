package com.feign.Service;

import com.feign.client.OpenFeignClient;
import com.feign.client.OpenFeignClient1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OpenFeignService {
    final
    OpenFeignClient openFeignClient;

    final
    OpenFeignClient1 openFeignClient1;

    public OpenFeignService(@Qualifier("com.feign.client.OpenFeignClient") OpenFeignClient openFeignClient, @Qualifier("com.feign.client.OpenFeignClient1") OpenFeignClient1 openFeignClient1) {
        this.openFeignClient = openFeignClient;
        this.openFeignClient1 = openFeignClient1;
    }

    public String getPort() {
        return openFeignClient.getPort();
    }

    public String getPort1(){
        return openFeignClient1.getPort();
    }
}
