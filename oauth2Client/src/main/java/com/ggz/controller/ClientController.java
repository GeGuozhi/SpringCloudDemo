package com.ggz.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ClientController {

    @PostMapping(value = "get")
    @PreAuthorize("hasRole('USER')") // 注意：不需要写 ROLE_ 前缀，Spring会自动添加
    public Object get(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;

            // 获取JWT令牌信息
            String tokenValue = jwtToken.getToken().getTokenValue();
            Map<String, Object> claims = jwtToken.getToken().getClaims();

            System.out.println("Authorities: " + authentication.getAuthorities());
            System.out.println("Token claims: " + claims);
            System.out.println("Username: " + authentication.getName());

            return Map.of(
                    "token", tokenValue.substring(0, 50) + "...", // 只返回部分token
                    "username", authentication.getName(),
                    "authorities", authentication.getAuthorities(),
                    "claims", claims
            );
        }

        return Map.of("error", "Invalid authentication type");
    }

    @PostMapping(value = "admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // 使用 hasAuthority 需要完整权限名称
    public Object admin(Authentication authentication) {
        return Map.of(
                "message", "admin access granted",
                "user", authentication.getName(),
                "authorities", authentication.getAuthorities()
        );
    }

    @PostMapping("admin1")
    @PreAuthorize("hasAuthority('ADMIN1')") // 没有 ROLE_ 前缀
    public String admin2(Authentication authentication) {
        return "user2_admin1 - accessed by: " + authentication.getName();
    }

    @PostMapping("public")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }
}