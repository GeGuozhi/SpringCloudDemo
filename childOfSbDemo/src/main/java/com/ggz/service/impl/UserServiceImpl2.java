package com.ggz.service.impl;


import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl2{
    public void select() {
        System.out.println("UserDao 查询 selectById");
    }
    public void update() {
        System.out.println("UserDao 更新 update");
    }
}
