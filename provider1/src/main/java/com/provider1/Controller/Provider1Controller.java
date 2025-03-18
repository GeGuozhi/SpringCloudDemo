package com.provider1.Controller;

import com.ggz.service.TicketService;
import com.provider1.component.ProviderSender;
import org.apache.dubbo.config.annotation.Reference;
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

    final ProviderSender process;

    @Reference
    private TicketService ticketService;

    public Provider1Controller(ProviderSender process) {
        this.process = process;
    }

    @GetMapping("providerNumberOne")
    public String provider() {
        return port;
    }

    @GetMapping("simple_a")
    public void simple_a() {
        process.simple_a();
    }

    @GetMapping("simple_b")
    public void simple_b() {
        process.simple_b();
    }

    @GetMapping("simple_c")
    public void simple_c() {
        process.simple_c();
    }

    @GetMapping("fanoutExchange")
    public void fanoutExchange() {
        process.fanoutExchange();
    }

    @GetMapping("directExchange_a")
    public void directExchange_a() {
        process.directExchange_a();
    }

    @GetMapping("directExchange_b")
    public void directExchange_b() {
        process.directExchange_b();
    }

    @GetMapping("directExchange_c")
    public void directExchange_c() {
        process.directExchange_c();
    }

    @GetMapping("topicExchange_a")
    public void topicExchange_a() {
        process.topicExchange_a();
    }

    @GetMapping("topicExchange_b")
    public void topicExchange_b() {
        process.topicExchange_b();
    }

    @GetMapping("topicExchange_c")
    public void topicExchange_c() {
        process.topicExchange_c();
    }

    @GetMapping("dubboTest")
    public String dubboTest() {
        return ticketService.sell();
    }

}
