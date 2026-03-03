# 分布式聊天服务

这是一个基于Spring Cloud的分布式聊天系统,支持多实例部署和实时消息广播。

## 功能特性

- ✅ WebSocket实时通信
- ✅ Redis分布式消息广播
- ✅ 支持多实例部署
- ✅ Eureka服务注册与发现
- ✅ 美观的聊天界面
- ✅ 用户加入/离开通知

## 技术栈

- Spring Boot 2.3.0
- Spring Cloud Hoxton.SR4
- WebSocket (STOMP协议)
- Redis (发布/订阅)
- Eureka Client
- Thymeleaf
- FastJSON

## 前置要求

1. JDK 1.8+
2. Maven 3.6+
3. Redis服务器 (默认localhost:6379)
4. Eureka Server (默认localhost:8761)

## 启动步骤

### 1. 启动Redis服务器

```bash
redis-server
```

### 2. 启动Eureka Server

参考 `eurekaServer` 模块的启动说明,确保Eureka Server运行在 `localhost:8761`

### 3. 启动聊天服务

```bash
cd chatService
mvn spring-boot:run
```

服务会在可用端口启动(默认8080,如果被占用会自动尝试8081或8082)

### 4. 访问聊天界面

打开浏览器访问: `http://localhost:8080`

## 分布式部署

### 多实例部署

可以启动多个聊天服务实例,它们会通过Redis进行消息广播:

1. 启动第一个实例 (端口8080)
2. 启动第二个实例 (端口8081,需要修改配置文件或使用不同端口)
3. 所有实例共享同一个Redis频道,消息会在所有实例间广播

### 配置说明

#### Redis配置

在 `application.yml` 中配置Redis连接:

```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: 
    database: 0
```

#### Eureka配置

在 `application.yml` 中配置Eureka注册中心:

```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

## 架构说明

### 消息流程

1. 客户端通过WebSocket发送消息到服务器
2. 服务器接收消息后发布到Redis频道
3. 所有订阅Redis频道的服务实例接收消息
4. 每个服务实例将消息广播给其WebSocket客户端

### 关键组件

- **ChatController**: 处理WebSocket消息映射
- **RedisMessagePublisher**: 发布消息到Redis
- **RedisMessageSubscriber**: 订阅Redis消息并广播
- **WebSocketConfig**: 配置WebSocket和STOMP
- **RedisConfig**: 配置Redis连接和消息监听

## 使用说明

1. 打开聊天界面
2. 输入用户名
3. 点击"进入聊天室"
4. 开始聊天!

## 注意事项

- 确保Redis服务器正在运行
- 确保Eureka Server正在运行
- 如果端口被占用,服务会自动尝试其他端口
- 多实例部署时,所有实例需要连接到同一个Redis服务器

## 开发说明

### 项目结构

```
chatService/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ggz/chat/
│   │   │       ├── config/          # 配置类
│   │   │       ├── controller/      # 控制器
│   │   │       ├── model/           # 实体类
│   │   │       ├── service/         # 服务类
│   │   │       └── ChatServiceApplication.java
│   │   └── resources/
│   │       ├── templates/           # 前端模板
│   │       └── application.yml      # 配置文件
│   └── test/
└── pom.xml
```

### 自定义配置

可以在 `application.yml` 中修改以下配置:

- 服务器端口
- Redis连接信息
- Eureka注册中心地址
- 日志级别

## 问题排查

### 无法连接到Redis

检查Redis服务器是否运行:
```bash
redis-cli ping
```

### 无法连接到Eureka

检查Eureka Server是否运行,并确认注册中心地址是否正确。

### WebSocket连接失败

检查服务器端口是否正确,以及防火墙设置。

## 许可证

本项目仅供学习使用。
