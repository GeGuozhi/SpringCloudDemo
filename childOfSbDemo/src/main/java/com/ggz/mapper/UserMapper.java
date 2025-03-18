package com.ggz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ggz.pojo.User;

/**
 * @author ggz on 2023/12/22
 */
public interface UserMapper extends BaseMapper<User> {
    public void updateUserByUserInfo(User user);
}