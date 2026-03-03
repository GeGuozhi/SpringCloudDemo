package com.ggz.service.impl;

import com.ggz.mapper.UserMapper;
import com.ggz.pojo.User;
import com.ggz.service.UserService;
import com.ggz.service.UserService2;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, Exception.class})
@Slf4j
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
        User user = new User("1", "1", "ggz");
        userMapper.updateUserByUserInfo(user);
        userService2.test2(new User("2", "2", "ggz"));
        userService2.test2(new User("3", "3", "ggz"));
        userService2.test2(new User("4", "4", "ggz"));

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpClient.execute(new HttpPost("1"));
            if (response.getStatusLine().getStatusCode() == 200) {
                log.info("通信成功！");
            }
        }
    }

    //    @Override
    public void test2() {
        System.out.println("test id");
    }
}
