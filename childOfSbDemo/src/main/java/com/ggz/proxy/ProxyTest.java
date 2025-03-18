package com.ggz.proxy;

import com.ggz.service.UserService;
import com.ggz.service.UserService2;
import com.ggz.service.impl.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        UserService userService = new UserServiceImpl();
//        UserService userService = new UserServiceImpl();

        ClassLoader classLoader = userService.getClass().getClassLoader();

        Class[] interfaces = userService.getClass().getInterfaces();

        InvocationHandler invocationHandler = new LogHandler(userService);

        UserService proxy = (UserService) Proxy.newProxyInstance(classLoader,interfaces,invocationHandler);
        UserService2 proxy2 = (UserService2) Proxy.newProxyInstance(classLoader,interfaces,invocationHandler);
        ((UserService) Proxy.newProxyInstance(classLoader,interfaces,invocationHandler)).select("6");

        /**
         cglib 动态代理 ,jdk 动态代理
         cglib 对普通类进行代理，生成继承类，动态代理只可以对接口进行代理。因为生成的代理类已经extends 了 proxy类。

         **/

//        CglibProxy cglibProxy =new CglibProxy();
//        UserService userService1 = (UserService) cglibProxy.getCglibProxy(new UserServiceImpl());
//        userService1.select("1");
    }
}
