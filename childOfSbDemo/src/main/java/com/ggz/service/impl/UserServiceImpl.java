package com.ggz.service.impl;

import com.ggz.pojo.User;
import com.ggz.mapper.UserMapper;
import com.ggz.service.UserService;
import com.ggz.service.UserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = {RuntimeException.class,Exception.class})
public class UserServiceImpl implements UserService
//        , UserService2
{

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService2 userService2;

    private int a = 0;

    @Override
    public void select(String id) {
        System.out.println(a);
        a = 2;
//        System.out.println("select id："+id);
    }

    @Override
    public void update(String id) throws Exception {
        User user = new User("1","1","ggz");
        userMapper.updateUserByUserInfo(user);
        userService2.test2(new User("2","2","ggz"));
        userService2.test2(new User("3","3","ggz"));
        userService2.test2(new User("4","4","ggz"));

//        throw new Exception("ee");
    }

//    @Override
    public void test2() {
        System.out.println("test id");
    }
}
