package com.ggz.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * jdk动态代理，
 * (被代理的接口) Proxy.newProxyInstance(classLoader,interfaces,invocationHandler);
 * classLoader 具体子类的classLoader
 * interfaces 具体子类实现的接口集合
 * invocationHandler 实现InvocationHandler接口的动态代理方法。
 */
public class LogHandler implements InvocationHandler {

    Object target;

    public LogHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target,args);
        after();
        return result;
    }

    private void before(){
        System.out.println(String.format("log time start [%s]",new Date()));
    }

    private void after(){
        System.out.println(String.format("log time end [%s]",new Date()));
    }

}
