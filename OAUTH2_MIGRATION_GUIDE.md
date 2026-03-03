# OAuth2 迁移指南

## 概述

本次升级将OAuth2从旧的Spring Cloud OAuth2迁移到新的Spring Security OAuth2 Authorization Server和Resource Server。

## 主要变更

### 1. OAuth2 Server升级

**移除的依赖**:
- `spring-cloud-starter-oauth2` (已移除)

**新增的依赖**:
- `spring-boot-starter-security` (必需)
- `spring-security-oauth2-authorization-server` (新的OAuth2授权服务器)
- `spring-security-oauth2-resource-server` (新的OAuth2资源服务器)
- `spring-security-oauth2-jose` (JWT支持)

**配置变更**:
- 旧配置: `AuthorizationServerConfig` (已禁用)
- 新配置: `OAuth2AuthorizationServerConfig` (已启用)

### 2. OAuth2 Client升级

**移除的依赖**:
- `spring-cloud-starter-oauth2` (已移除)

**新增的依赖**:
- `spring-boot-starter-security` (必需)
- `spring-boot-starter-oauth2-resource-server` (新的OAuth2资源服务器)

**配置变更**:
- 旧配置: `ResourceServerConfig` (已禁用)
- 新配置: `OAuth2ResourceServerConfig` (已启用)

## 重要变更

### 1. PASSWORD Grant Type

**变更**: PASSWORD grant type已被移除

**原因**: 
- PASSWORD grant type在新的OAuth2规范中已被弃用
- 新的Spring Security OAuth2 Authorization Server不支持PASSWORD grant type

**替代方案**:
- 使用AUTHORIZATION_CODE grant type (推荐)
- 使用CLIENT_CREDENTIALS grant type (适用于服务间调用)

**如果确实需要使用PASSWORD grant type**:
- 需要使用自定义的TokenGranter实现
- 或继续使用旧的OAuth2配置（但不推荐）

### 2. OAuth2端点变更

**旧端点**:
- `/oauth/authorize`
- `/oauth/token`
- `/oauth/check_token`

**新端点**:
- `/oauth2/authorize`
- `/oauth2/token`
- `/oauth2/jwks` (JWK Set端点)

**注意**: 新的OAuth2 Authorization Server使用不同的端点路径

### 3. Token格式变更

**旧格式**: Opaque token (存储在Redis中)

**新格式**: JWT token (无状态，不存储在Redis中)

**影响**:
- JWT token无法撤销（除非使用token blacklist）
- JWT token包含用户信息，不需要每次请求都查询数据库
- JWT token更适合分布式系统

### 4. 配置方式变更

**旧方式**: 
- 使用`@EnableAuthorizationServer`
- 使用`@EnableResourceServer`
- 使用`AuthorizationServerConfigurerAdapter`
- 使用`ResourceServerConfigurerAdapter`

**新方式**:
- 使用`SecurityFilterChain`
- 使用`RegisteredClientRepository`
- 使用`JWKSource`
- 使用`AuthorizationServerSettings`

## 配置示例

### OAuth2 Server配置

**新配置类**: `OAuth2AuthorizationServerConfig`

主要功能:
- 配置授权服务器安全过滤器链
- 配置默认安全过滤器链
- 配置用户详情服务
- 配置注册的客户端仓库
- 配置JWK源（用于JWT签名）

**客户端配置**:
```java
RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId("client_1")
        .clientSecret(passwordEncoder().encode("123456"))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .redirectUri("https://www.baidu.com/")
        .postLogoutRedirectUri("https://www.baidu.com/")
        .scope(OidcScopes.OPENID)
        .scope(OidcScopes.PROFILE)
        .scope("all")
        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
        .build();
```

### OAuth2 Client配置

**新配置类**: `OAuth2ResourceServerConfig`

主要功能:
- 配置资源服务器安全过滤器链
- 配置JWT token验证

**配置文件**: `application.yml`
```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8888
```

## 迁移步骤

### 1. 更新依赖

**OAuth2 Server**:
```xml
<!-- 移除 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-oauth2</artifactId>
</dependency>

<!-- 添加 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-oauth2-authorization-server</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-oauth2-resource-server</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-oauth2-jose</artifactId>
</dependency>
```

**OAuth2 Client**:
```xml
<!-- 移除 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-oauth2</artifactId>
</dependency>

<!-- 添加 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```

### 2. 更新配置类

**OAuth2 Server**:
- 使用新的`OAuth2AuthorizationServerConfig`
- 禁用旧的`AuthorizationServerConfig`

**OAuth2 Client**:
- 使用新的`OAuth2ResourceServerConfig`
- 禁用旧的`ResourceServerConfig`

### 3. 更新配置文件

**OAuth2 Client**:
```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8888
```

### 4. 更新客户端代码

**端点变更**:
- 旧端点: `/oauth/authorize`, `/oauth/token`
- 新端点: `/oauth2/authorize`, `/oauth2/token`

**Token格式变更**:
- 旧格式: Opaque token
- 新格式: JWT token

## 测试建议

1. **测试OAuth2 Server**:
   - 测试授权码流程
   - 测试token获取
   - 测试JWK Set端点
   - 测试OpenID Connect

2. **测试OAuth2 Client**:
   - 测试资源访问
   - 测试JWT token验证
   - 测试权限控制

3. **测试客户端应用**:
   - 更新客户端配置
   - 测试OAuth2流程
   - 验证token有效性

## 已知问题

### 1. PASSWORD Grant Type不支持

**问题**: 新的OAuth2 Authorization Server不支持PASSWORD grant type

**解决方案**:
- 使用AUTHORIZATION_CODE grant type (推荐)
- 使用CLIENT_CREDENTIALS grant type
- 或使用自定义TokenGranter实现

### 2. Token撤销

**问题**: JWT token无法撤销

**解决方案**:
- 使用短期的token
- 使用token blacklist
- 或使用Opaque token

### 3. 端点路径变更

**问题**: 新的OAuth2端点路径不同

**解决方案**:
- 更新客户端配置
- 使用新的端点路径

## 后续工作

1. **测试所有功能**:
   - 测试OAuth2 Server功能
   - 测试OAuth2 Client功能
   - 测试客户端应用

2. **更新文档**:
   - 更新API文档
   - 更新配置文档
   - 更新使用说明

3. **监控和日志**:
   - 添加OAuth2日志
   - 检查错误日志
   - 监控token使用情况

## 参考资源

- [Spring Security OAuth2 Authorization Server文档](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/)
- [Spring Security OAuth2 Resource Server文档](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html)
- [OAuth2规范](https://oauth.net/2/)
- [OpenID Connect规范](https://openid.net/connect/)

## 联系支持

如果遇到问题，请检查：
1. 依赖版本是否正确
2. 配置是否正确
3. 端点路径是否正确
4. Token格式是否正确
5. 日志中的错误信息

