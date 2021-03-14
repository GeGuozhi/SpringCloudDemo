package com.ggz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;


@Configuration
@EnableAuthorizationServer
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenStore redisTokenStore;

    public Oauth2Config(PasswordEncoder passwordEncoder, @Qualifier("customUserDetailsService") UserDetailsService userDetailsService, AuthenticationManager authenticationManager, TokenStore redisTokenStore) {
        super();
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.redisTokenStore = redisTokenStore;
    }

    /**
     * 客户端的参数的重写
     * 这里是将数据直接写入内存，实际应该从数据库表获取
     * clientId:客户端Id
     * secret:客户端的密钥
     * authorizedGrantTypes：授权方式
     *     authorization_code: 授权码类型,
     *     implicit: 隐式授权,
     *     password: 密码授权,
     *     client_credentials: 客户端授权,
     *     refresh_token: 通过上面4中方式获取的刷新令牌获取的新令牌，
     *                      注意是获取token和refresh_token之后，通过refresh_token刷新之后的令牌
     * accessTokenValiditySeconds: token有效期
     * scopes 用来限制客户端访问的权限，只有在scopes定义的范围内，才可以正常的换取token
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("order-client")
                .secret(passwordEncoder.encode("order-secret-8888"))//b3JkZXItY2xpZW50Om9yZGVyLXNlY3JldC04ODg4
                .redirectUris("https://www.huya.com/")
                .authorizedGrantTypes("refresh_token","authorization_code","password")
                .accessTokenValiditySeconds(3600)
                .scopes("all")
                .and()
                .withClient("user-client")
                .secret(passwordEncoder.encode("user-secret-8888"))//dXNlci1jbGllbnQ6dXNlci1zZWNyZXQtODg4OA==
                .authorizedGrantTypes("refresh_token","authorization_code","password")
                .accessTokenValiditySeconds(3600)
                .scopes("all");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");
    }

    /**
     * 重写授权管理bean参数
     * 重写用户校验
     * 重写token缓存方式
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(redisTokenStore)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }
}
