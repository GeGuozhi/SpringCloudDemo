package com.ggz.proxy;


import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * sfm库，cglib动态代理
 */
public class LogInterceptor implements MethodInterceptor {


    private void before() {
        System.out.println("事前");
    }

    private void after() {
        System.out.println("事后");
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o,objects);
        after();
        return result;
    }
}
