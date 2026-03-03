# Eureka Server 启动说明

## 环境配置

项目支持两种环境配置：
- **local**: 本地开发环境
- **linux**: Linux生产环境

## 配置文件说明

- `application.yml`: 主配置文件，包含公共配置
- `application-local.yml`: 本地环境配置
- `application-linux.yml`: Linux环境配置

## 启动方式

### 1. 使用JVM参数指定profile

**本地环境启动：**
```bash
java -jar -Dspring.profiles.active=local eurekaServer-1.0-SNAPSHOT.jar
```

**Linux环境启动：**
```bash
java -jar -Dspring.profiles.active=linux eurekaServer-1.0-SNAPSHOT.jar
```

### 2. 使用环境变量

**Linux环境：**
```bash
export SPRING_PROFILES_ACTIVE=linux
java -jar eurekaServer-1.0-SNAPSHOT.jar
```

### 3. 使用启动脚本

**Windows本地环境：**
```bash
start-local.bat
```

**Linux环境：**
```bash
chmod +x start-linux.sh
./start-linux.sh
```

## 配置差异

### Local环境特点：
- 主机名: localhost
- 访问地址: http://localhost:8761
- 自我保护: 关闭
- 日志级别: DEBUG
- 日志输出: 控制台

### Linux环境特点：
- 主机名: 192.168.157.129
- 访问地址: http://192.168.157.129:8761
- 自我保护: 开启
- 日志级别: INFO/WARN
- 日志输出: 文件 + 控制台
- 日志文件路径: /var/log/eureka-server/eureka-server.log

## 环境变量配置

在Linux环境中，可以通过以下环境变量进行配置：

- `EUREKA_HOSTNAME`: Eureka主机名（默认: 192.168.157.129）
- `SERVER_PORT`: 服务端口（默认: 8761）
- `EUREKA_SELF_PRESERVATION`: 是否启用自我保护（默认: true）
- `EUREKA_EVICTION_INTERVAL`: 清理间隔（默认: 1000ms）

## 访问地址

- **本地环境**: http://localhost:8761
- **Linux环境**: http://192.168.157.129:8761

## 打包命令

```bash
mvn clean package -DskipTests
```

打包后的JAR文件位于 `target/eurekaServer-1.0-SNAPSHOT.jar`
