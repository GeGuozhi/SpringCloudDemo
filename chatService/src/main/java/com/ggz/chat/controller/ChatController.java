package com.ggz.chat.controller;

import com.ggz.chat.model.ChatMessage;
import com.ggz.chat.service.RedisMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

/**
 * 聊天控制器
 * 
 * @author ggz
 */
@Slf4j
@Controller
public class ChatController {

    @Autowired
    private RedisMessagePublisher redisMessagePublisher;

    /**
     * 返回聊天页面
     */
    @PostMapping("/")
    public String index() {
        return "chat";
    }

    /**
     * 处理客户端发送的消息
     * 客户端发送消息到 /app/chat.sendMessage
     * 通过Redis发布消息，实现分布式消息广播
     */
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        log.info("收到消息: {} 来自用户: {}", chatMessage.getContent(), chatMessage.getSender());
        chatMessage.setTimestamp(LocalDateTime.now());
        
        // 发布消息到Redis，实现分布式消息广播
        // Redis订阅服务会接收到消息并广播给所有WebSocket客户端
        redisMessagePublisher.publish(chatMessage);
    }

    /**
     * 处理用户加入聊天室
     * 客户端发送消息到 /app/chat.addUser
     */
    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // 在session中添加用户名
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        log.info("用户加入: {}", chatMessage.getSender());
        chatMessage.setTimestamp(LocalDateTime.now());
        
        // 发布加入消息到Redis
        // Redis订阅服务会接收到消息并广播给所有WebSocket客户端
        redisMessagePublisher.publish(chatMessage);
    }

    @MessageMapping("/hello")
    @SendTo("/topic/public")
    public ChatMessage greeting(ChatMessage chatMessage){
        return chatMessage;
    }
}

