package com.provider1.component;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.messages")
public class CustomerReceiver1 {
    @RabbitHandler
    public void process(String hello){
        System.out.println("CustomerReceiver1:"+hello);
    }
}
