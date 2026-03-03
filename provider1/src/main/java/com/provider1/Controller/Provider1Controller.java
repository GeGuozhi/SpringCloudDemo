package com.provider1.Controller;

import com.ggz.service.TicketService;
import com.provider1.component.ProviderSender;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("providerNumberOne")
    public String provider() {
        return port;
    }

    @PostMapping("simple_a")
    public void simple_a() {
        process.simple_a();
    }

    @PostMapping("simple_b")
    public void simple_b() {
        process.simple_b();
    }

    @PostMapping("simple_c")
    public void simple_c() {
        process.simple_c();
    }

    @PostMapping("fanoutExchange")
    public void fanoutExchange() {
        process.fanoutExchange();
    }

    @PostMapping("directExchange_a")
    public void directExchange_a() {
        process.directExchange_a();
    }

    @PostMapping("directExchange_b")
    public void directExchange_b() {
        process.directExchange_b();
    }

    @PostMapping("directExchange_c")
    public void directExchange_c() {
        process.directExchange_c();
    }

    @PostMapping("topicExchange_a")
    public void topicExchange_a() {
        process.topicExchange_a();
    }

    @PostMapping("topicExchange_b")
    public void topicExchange_b() {
        process.topicExchange_b();
    }

    @PostMapping("topicExchange_c")
    public void topicExchange_c() {
        process.topicExchange_c();
    }

    @PostMapping("dubboTest")
    public String dubboTest() {
        return ticketService.sell();
    }

}
