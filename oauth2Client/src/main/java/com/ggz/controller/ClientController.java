package com.ggz.controller;

import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ClientController {
    @GetMapping(value = "get")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Object get(Authentication authentication) {
        authentication.getCredentials();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();

        System.out.println(authentication.getAuthorities());
        String detail = details.getTokenValue();
        System.out.println("remoteAddress:" + details.getRemoteAddress());
        System.out.println("sessionId:" + details.getSessionId());
        System.out.println("tokenType:" + details.getTokenType());
        return detail;
    }

    @GetMapping(value = "admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Object ADMIN(Authentication authentication) {
        return "user1_admin";
    }

    @GetMapping("admin1")
    @PreAuthorize("hasAuthority('ADMIN1')")
    public String ADMIN2(HttpServletRequest request) {
        return "user2_admin1";
    }
}
