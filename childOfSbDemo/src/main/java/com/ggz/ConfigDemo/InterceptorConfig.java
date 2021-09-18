//package com.ggz.ConfigDemo;
//
//import com.ggz.interceptor.LogCostInterceptor;
//import com.ggz.interceptor.LogCostInterceptor2;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LogCostInterceptor2());
//        registry.addInterceptor(new LogCostInterceptor());
//    }
//}
