//package com.ggz.ConfigDemo;
//import com.ggz.filter.LogCostFilter;
//import com.ggz.filter.LogCostFilter2;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.web.filter.CharacterEncodingFilter;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//
//import javax.servlet.FilterRegistration;
//
///**
// * 过滤器，利用 FilterRegistrationBean 注入对应的过滤器。
// * order：设置等级，越小越先进入doChain越先执行，doChain之后，进入下一个等级低的拦截器中。
// * @WebServlet(patternUrl = "/*" )(Servlet3.0提供) 也可以在
// * 过滤器上面写上该注解可也可实现注入默认等级最高。
// * @Author:ggz
// */
//@Configuration
//public class FilterConfig {
//
//    @Bean
//    @Order(2)
//    public FilterRegistrationBean<LogCostFilter> filterRegistration(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new LogCostFilter());
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.setName("costTime1");
//        return filterRegistrationBean;
//    }
//
//    @Bean
//    @Order(5)
//    public FilterRegistrationBean<LogCostFilter2> filterRegistration2(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new LogCostFilter2());
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.setName("costTime2");
//        return filterRegistrationBean;
//    }
//
//    @Bean
//    @Order(1)
//    public FilterRegistrationBean filterRegistration3(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//
//        CharacterEncodingFilter filter = new CharacterEncodingFilter();
//        filter.setForceEncoding(true);
//        filter.setEncoding("UTF-8");
//        filterRegistrationBean.setFilter(filter);
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.setName("filterForEncoding");
//        return filterRegistrationBean;
//    }
//
//}
