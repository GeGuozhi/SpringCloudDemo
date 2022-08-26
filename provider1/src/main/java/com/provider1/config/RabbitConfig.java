package com.provider1.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    /**
     * 简单模式,工作模式(一个消费者消费一个消息)
     */
    @Bean
    public Queue simple_a() {
        return new Queue("topic.simple.a");
    }

    @Bean
    public Queue simple_b() {
        return new Queue("topic.simple.b");
    }

    @Bean
    public Queue simple_c() {
        return new Queue("topic.simple.c");
    }

    /**
     * 订阅与发布模式：FanoutExchange,根据交换机转发所有监听该队列的消费者
     */

    @Bean
    public Queue fanoutExchange_a() {
        return new Queue("topic.fanoutExchange.a");
    }

    @Bean
    public Queue fanoutExchange_b() {
        return new Queue("topic.fanoutExchange.b");
    }

    @Bean
    public Queue fanoutExchange_c() {
        return new Queue("topic.fanoutExchange.c");
    }

    /**
     * 订阅与发布模式：DirectExchange,根据交换机以及队列名精准推送
     */

    @Bean
    public Queue directExchange_a() {
        return new Queue("topic.directExchange.a");
    }

    @Bean
    public Queue directExchange_b() {
        return new Queue("topic.directExchange.b");
    }

    @Bean
    public Queue directExchange_c() {
        return new Queue("topic.directExchange.c");
    }

    /**
     * 订阅与发布模式：TopicExchange,模糊推送
     */

    @Bean
    public Queue topicExchange_a() {
        return new Queue("topic.topicExchange.a");
    }

    @Bean
    public Queue topicExchange_b() {
        return new Queue("topic.topicExchange.b");
    }

    @Bean
    public Queue topicExchange_c() {
        return new Queue("topic.topicExchange.c");
    }


    /**
     * 创建一个fanoutExchange交换机，可以转发消息到绑定了该交换机的队列
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 创建一个directExchange交换机,指定directExchange交换机名称以及queue队列名进行精准推送
     */
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    /**
     * 创建一个topicExchange交换机,可以根据正则模式模糊匹配所有队列
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    /**
     * 将queueA队列绑定到fanoutExchange交换机上面
     */
    @Bean
    Binding bindingExchangeMessageFanoutA(Queue fanoutExchange_a, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutExchange_a).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeMessageFanoutB(Queue fanoutExchange_b, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutExchange_b).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeMessageFanoutC(Queue fanoutExchange_c, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutExchange_c).to(fanoutExchange);
    }

    /**
     * 将topic.a队列绑定到directExchange交换机中，使用topic.a作为路由规则
     */
    @Bean
    public Binding bindingExchangeMessageDirectA(Queue directExchange_a, DirectExchange directExchange) {
        return BindingBuilder.bind(directExchange_a).to(directExchange).with("topic.directExchange.a");
    }

    @Bean
    public Binding bindingExchangeMessageDirectB(Queue directExchange_b, DirectExchange directExchange) {
        return BindingBuilder.bind(directExchange_b).to(directExchange).with("topic.directExchange.b");
    }

    @Bean
    public Binding bindingExchangeMessageDirectC(Queue directExchange_c, DirectExchange directExchange) {
        return BindingBuilder.bind(directExchange_c).to(directExchange).with("topic.directExchange.c");
    }
        
    /**
     * 队列topic.b绑定交换机并且关联了topic.topicExchange.#正则路由规则。就是说只要topic.topicExchange.开头的，topicExchange_a队列都将收到消息
     */
    @Bean
    public Binding bindingExchangeMessages(Queue topicExchange_a, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicExchange_a).to(topicExchange).with("topic.topicExchange.#");
    }
}
