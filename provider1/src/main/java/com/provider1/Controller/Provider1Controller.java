package com.provider1.Controller;

import com.provider1.component.CustomerReceiver;
import com.provider1.component.CustomerReceiver1;
import com.provider1.component.ProviderSender;
import com.provider1.component.Sender;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    Sender sender;

    @Autowired
    ProviderSender process;

    @GetMapping("providerNumberOne")
    public String provider() {
        return port;
    }

    @GetMapping("sender")
    public void hello(){
        sender.send();
    }

    @GetMapping("senderDirect")
    public void hello1(){
        process.send();
    }

    @GetMapping("senderTopic")
    public void hello2(){
        process.send();
    }
}
