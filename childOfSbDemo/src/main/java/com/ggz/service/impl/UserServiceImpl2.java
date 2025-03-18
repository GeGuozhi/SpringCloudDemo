package com.ggz.service.impl;


import com.ggz.mapper.UserMapper;
import com.ggz.pojo.User;
import com.ggz.service.UserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class})
public class UserServiceImpl2 implements UserService2 {
    @Autowired
    UserMapper userMapper;

    public void select() {
        System.out.println("UserDao 查询 selectById");
    }

    public void update() {
        System.out.println("UserDao 更新 update");
    }

    @Override
    public void test2(User user) {
        userMapper.updateUserByUserInfo(user);
    }
}
