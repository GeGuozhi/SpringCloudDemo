//package com.ggz.filter;
//
//import javax.servlet.*;
//import java.io.IOException;
//
//public class LogCostFilter2 implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        long start = System.currentTimeMillis();
//        System.out.println("cost2计算可得，这个请求花费时间："+(System.currentTimeMillis()-start)+"ms");
//        filterChain.doFilter(servletRequest,servletResponse);
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
//
