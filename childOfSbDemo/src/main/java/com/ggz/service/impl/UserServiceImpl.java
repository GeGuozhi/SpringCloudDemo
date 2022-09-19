package com.ggz.service.impl;

import com.ggz.service.UserService;
import com.ggz.service.UserService2;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService, UserService2 {

    private int a = 0;

    @Override
    public void select(String id) {
        System.out.println(a);
        a = 2;
//        System.out.println("select id："+id);
    }

    @Override
    public void update(String id) {
        System.out.println(a);
        a = 1;
//        System.out.println("update id："+id);
    }

    @Override
    public void test2() {
        System.out.println("test id");
    }
}
