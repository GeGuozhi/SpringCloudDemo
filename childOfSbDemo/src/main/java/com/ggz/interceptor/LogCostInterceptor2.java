//package com.ggz.interceptor;
//
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class LogCostInterceptor2 implements HandlerInterceptor {
//    Long start;
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HandlerMethod h;
//        if(handler instanceof HandlerMethod){
//            h = (HandlerMethod)handler;
//        }else{
//            return true;
//        }
//        start = System.currentTimeMillis();
//        System.out.println("contextPath"+request.getContextPath());
//        System.out.println(request.getRequestURI());
//        System.out.println(request.getRequestURL());
//        System.out.println(h.getMethod().getName());
//        System.out.println(h.getBean().getClass().getName());
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//        System.out.println("拦截器2：花费时间:"+(System.currentTimeMillis()-start)+"ms");
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("拦截器2完成拦截");
//    }
//}
