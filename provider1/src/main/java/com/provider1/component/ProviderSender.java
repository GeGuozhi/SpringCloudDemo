package com.provider1.component;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProviderSender {

    final AmqpTemplate amqpTemplate;

    public ProviderSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void simple_a() {
        String content = "simple_a";
        amqpTemplate.convertAndSend("topic.simple.a", content);
    }

    public void simple_b() {
        String content = "simple_b";
        amqpTemplate.convertAndSend("topic.simple.b", content);
    }

    public void simple_c() {
        String content = "simple_c";
        amqpTemplate.convertAndSend("topic.simple.c", content);
    }

    /**
     * fanout模式，全部转发
     */
    public void fanoutExchange() {
        String content = "fanoutExchange";
        amqpTemplate.convertAndSend("fanoutExchange", "", content);
    }


    /**
     * direct模式，精准模式
     */
    public void directExchange_a() {
        String content = "directExchange_a";
        amqpTemplate.convertAndSend("directExchange", "topic.directExchange.a", content);
    }


    public void directExchange_b() {
        String content = "directExchange_b";
        amqpTemplate.convertAndSend("directExchange", "topic.directExchange.b", content);
    }

    public void directExchange_c() {
        String content = "directExchange_c";
        amqpTemplate.convertAndSend("directExchange", "topic.directExchange.c", content);
    }

    /**
     * topic模糊匹配模式，
     */

    public void topicExchange_a() {
        String content = "topicExchange_a";
        amqpTemplate.convertAndSend("topicExchange", "topic.topicExchange.a", content);
    }


    public void topicExchange_b() {
        String content = "topicExchange_b";
        amqpTemplate.convertAndSend("topicExchange", "topic.topicExchange.b", content);
    }

    public void topicExchange_c() {
        String content = "topicExchange_c";
        amqpTemplate.convertAndSend("topicExchange", "topic.topicExchange.c", content);
    }

}
