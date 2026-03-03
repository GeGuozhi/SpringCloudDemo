# 依赖升级说明

## 升级概览

本次升级移除了所有过时的依赖，并升级到最新支持的版本。

## 已完成的升级

### 1. Hystrix → Resilience4j

**移除的依赖**:
- `spring-cloud-starter-netflix-hystrix` (已移除)

**新增的依赖**:
- `spring-cloud-starter-circuitbreaker-resilience4j` (替代Hystrix)

**变更内容**:
- ✅ 移除Hystrix依赖
- ✅ 添加Resilience4j依赖
- ✅ 更新Feign配置以使用Resilience4j
- ✅ 重命名fallback类（移除Hystrix命名）
- ✅ 更新application.yml配置

**配置文件变更**:
- `feign/src/main/resources/application.yml`: 添加Resilience4j配置
- `feign/pom.xml`: 移除Hystrix，添加Resilience4j

**代码变更**:
- `feign/src/main/java/com/feign/client/OpenFeignClientHystrix.java` → `OpenFeignClientFallback.java`
- `feign/src/main/java/com/feign/client/OpenFeignClient1Hystrix.java` → `OpenFeignClient1Fallback.java`

### 2. OAuth2 配置升级

#### OAuth2 Server升级

**移除的依赖**:
- `spring-cloud-starter-oauth2` (已移除)

**新增的依赖**:
- `spring-security-oauth2-authorization-server` (新的OAuth2授权服务器)
- `spring-security-oauth2-resource-server` (新的OAuth2资源服务器)
- `spring-security-oauth2-jose` (JWT支持)

**变更内容**:
- ✅ 移除旧的OAuth2依赖
- ✅ 添加新的Spring Security OAuth2 Authorization Server依赖
- ✅ 创建新的OAuth2AuthorizationServerConfig配置类
- ✅ 禁用旧的AuthorizationServerConfig配置类
- ✅ 禁用旧的SecurityConfig配置类

**配置文件变更**:
- `oauthServer/pom.xml`: 移除旧OAuth2，添加新OAuth2依赖
- `oauthServer/src/main/java/com/ggz/config/OAuth2AuthorizationServerConfig.java`: 新配置类
- `oauthServer/src/main/java/com/ggz/config/AuthorizationServerConfig.java`: 已禁用（保留为备份）

**主要变更**:
- 使用`RegisteredClientRepository`替代`ClientDetailsService`
- 使用`JWKSource`生成JWT密钥
- 使用`SecurityFilterChain`替代`WebSecurityConfigurerAdapter`
- 支持OpenID Connect 1.0

#### OAuth2 Client升级

**移除的依赖**:
- `spring-cloud-starter-oauth2` (已移除)

**新增的依赖**:
- `spring-boot-starter-oauth2-resource-server` (新的OAuth2资源服务器)
- `spring-security-oauth2-jose` (JWT支持)

**变更内容**:
- ✅ 移除旧的OAuth2依赖
- ✅ 添加新的Spring Security OAuth2 Resource Server依赖
- ✅ 创建新的OAuth2ResourceServerConfig配置类
- ✅ 禁用旧的ResourceServerConfig配置类
- ✅ 更新application.yml配置

**配置文件变更**:
- `oauth2Client/pom.xml`: 移除旧OAuth2，添加新OAuth2依赖
- `oauth2Client/src/main/resources/application.yml`: 更新OAuth2配置
- `oauth2Client/src/main/java/com/ggz/component/OAuth2ResourceServerConfig.java`: 新配置类
- `oauth2Client/src/main/java/com/ggz/component/ResourceServerConfig.java`: 已禁用（保留为备份）

**主要变更**:
- 使用JWT token替代Opaque token
- 使用`SecurityFilterChain`替代`ResourceServerConfigurerAdapter`
- 使用`@EnableMethodSecurity`替代`@EnableGlobalMethodSecurity`

### 3. Feign模块OAuth2依赖移除

**移除的依赖**:
- `spring-cloud-starter-oauth2` (已移除，因为配置已被注释)

**变更内容**:
- ✅ 移除未使用的OAuth2依赖
- ✅ ResourceServerConfig配置已被注释

### 4. 其他依赖检查

**已检查的依赖**:
- ✅ fastjson: 1.2.83 (已升级，支持Java 11)
- ✅ guava: 31.1-jre (已升级)
- ✅ cglib: 3.3.0 (已升级)
- ✅ hutool-all: 5.8.26 (已升级)
- ✅ junit: 4.13.2 (已升级)

## 配置变更详情

### Resilience4j配置

**feign/src/main/resources/application.yml**:
```yaml
feign:
  circuitbreaker:
    enabled: true
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 5000

resilience4j:
  circuitbreaker:
    configs:
      default:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenState: true
        waitDurationInOpenState: 10s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
  timelimiter:
    configs:
      default:
        timeoutDuration: 5s
```

### OAuth2 Resource Server配置

**oauth2Client/src/main/resources/application.yml**:
```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8888
```

## 注意事项

### 1. OAuth2 Grant Types

**变更**: PASSWORD grant type已被移除

**原因**: PASSWORD grant type在新的OAuth2规范中已被弃用，不建议使用

**替代方案**:
- 使用AUTHORIZATION_CODE grant type（推荐）
- 使用CLIENT_CREDENTIALS grant type（适用于服务间调用）

**如果确实需要使用PASSWORD grant type**:
- 可以在`OAuth2AuthorizationServerConfig.java`中取消注释PASSWORD grant type
- 但建议迁移到更安全的grant type

### 2. OAuth2 Token Store

**变更**: 不再使用Redis Token Store

**原因**: 新的OAuth2 Authorization Server使用JWT token，不需要Token Store

**影响**:
- JWT token是无状态的，不需要存储在Redis中
- 如果需要撤销token，需要使用Opaque token或JWT revocation

### 3. OAuth2端点变更

**旧端点**:
- `/oauth/authorize`
- `/oauth/token`
- `/oauth/check_token`

**新端点**:
- `/oauth2/authorize`
- `/oauth2/token`
- `/oauth2/jwks` (JWK Set端点)

**注意**: 新的OAuth2 Authorization Server使用不同的端点路径

### 4. Security配置变更

**变更**: 使用`SecurityFilterChain`替代`WebSecurityConfigurerAdapter`

**原因**: `WebSecurityConfigurerAdapter`在Spring Security 5.7+中已被弃用

**影响**:
- 所有安全配置需要使用`SecurityFilterChain`
- 配置方式更加灵活

## 迁移指南

### 从Hystrix迁移到Resilience4j

1. **移除Hystrix依赖**:
   ```xml
   <!-- 移除 -->
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
   </dependency>
   ```

2. **添加Resilience4j依赖**:
   ```xml
   <!-- 添加 -->
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
   </dependency>
   ```

3. **更新配置**:
   - 更新`application.yml`中的配置
   - Feign会自动使用Resilience4j

4. **更新代码**:
   - Fallback类不需要修改
   - Feign的fallback机制仍然有效

### 从旧OAuth2迁移到新OAuth2

1. **更新依赖**:
   - 移除`spring-cloud-starter-oauth2`
   - 添加新的OAuth2依赖

2. **更新配置类**:
   - 使用新的`OAuth2AuthorizationServerConfig`
   - 使用新的`OAuth2ResourceServerConfig`

3. **更新配置文件**:
   - 更新`application.yml`中的OAuth2配置
   - 使用新的配置格式

4. **测试OAuth2功能**:
   - 测试授权码流程
   - 测试token获取
   - 测试资源访问

## 测试建议

1. **测试Resilience4j**:
   - 测试Feign客户端降级功能
   - 测试熔断器功能
   - 检查配置是否正确

2. **测试OAuth2 Server**:
   - 测试授权码流程
   - 测试token获取
   - 测试JWK Set端点

3. **测试OAuth2 Client**:
   - 测试资源访问
   - 测试JWT token验证
   - 测试权限控制

## 已知问题

### 1. OAuth2 PASSWORD Grant Type

**问题**: 新的OAuth2 Authorization Server不支持PASSWORD grant type

**解决方案**:
- 移除PASSWORD grant type（推荐）
- 或使用CLIENT_CREDENTIALS grant type

### 2. OAuth2 Token Revocation

**问题**: JWT token无法撤销

**解决方案**:
- 使用短期的token
- 使用token blacklist
- 或使用Opaque token

### 3. OAuth2端点路径变更

**问题**: 新的OAuth2端点路径不同

**解决方案**:
- 更新客户端配置
- 使用新的端点路径

## 后续工作

1. **测试所有功能**:
   - 测试Resilience4j功能
   - 测试OAuth2功能
   - 测试所有服务

2. **更新文档**:
   - 更新API文档
   - 更新配置文档
   - 更新使用说明

3. **监控和日志**:
   - 添加Resilience4j监控
   - 添加OAuth2日志
   - 检查错误日志

## 参考资源

- [Resilience4j文档](https://resilience4j.readme.io/)
- [Spring Security OAuth2 Authorization Server文档](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/)
- [Spring Security OAuth2 Resource Server文档](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html)
- [OAuth2规范](https://oauth.net/2/)

## 联系支持

如果遇到问题，请检查：
1. 依赖版本是否正确
2. 配置是否正确
3. 日志中的错误信息
4. 参考文档和示例

