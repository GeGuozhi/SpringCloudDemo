package com.feign.Controller;

import com.feign.Service.OpenFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {
    final
    OpenFeignService openFeignService;

    public FeignController(OpenFeignService openFeignService) {
        this.openFeignService = openFeignService;
    }

    @RequestMapping("getPort")
    public String getPort() {
         return openFeignService.getPort();
    }

    @RequestMapping("getPort1")
    public String getPort1(){
        return openFeignService.getPort1();
    }
}
