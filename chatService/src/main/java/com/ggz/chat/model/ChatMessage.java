package com.ggz.chat.model;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 聊天消息实体
 * 
 * @author ggz
 */
@Data
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 消息类型: JOIN, LEAVE, CHAT
     */
    private MessageType type;
    
    /**
     * 发送者用户名
     */
    private String sender;
    
    /**
     * 消息内容
     */
    private String content;
    
    /**
     * 发送时间
     */
    private LocalDateTime timestamp;
    
    /**
     * 消息类型枚举
     */
    public enum MessageType {
        JOIN,      // 加入聊天室
        LEAVE,     // 离开聊天室
        CHAT       // 普通消息
    }
}

