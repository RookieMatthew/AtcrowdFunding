package com.zsr.manager.service.impl;

import com.zsr.bean.User;
import com.zsr.exception.LoginFailException;
import com.zsr.manager.dao.UserMapper;
import com.zsr.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Demo class
 * 用户模块业务层实现
 * @author shourenzhang
 * @date 2019/7/27 16:13
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 装配dao层接口,用于调用持久层方法
     */
    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserLogin(HashMap<String, Object> paramMap) {
        User user = userMapper.queryUserLogin(paramMap);
        if (user==null){
            throw new LoginFailException("用户名或密码不正确！");
        }
        return user;
    }

    @Override
    public List getUsers() {
        return userMapper.selectAll();
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }
}
