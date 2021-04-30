package com.provider1.component;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.message")
public class CustomerReceiver {
    @RabbitHandler
    public void process(String hello){
        System.out.println("CustomerReceiver:"+hello);
    }
}
