package com.provider1.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 有志者事竟成，破釜沉舟，百二秦关终属楚
 * 苦心人终不负，卧薪尝胆，三千越甲可吞吴
 */

@RestController
@RequestMapping("/serviceNumberOne")
public class Provider1Controller {
    @Value("${server.port}")
    String port;

    @GetMapping("providerNumberOne")
    public String provider(){
        return port;
    }
}
