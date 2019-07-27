package com.zsr.manager.service;

import com.zsr.bean.User;

import java.util.HashMap;

/**
 * Demo class
 * 用户模块service业务层接口
 * @author shourenzhang
 * @date 2019/7/27 16:08
 */
public interface UserService {
    /**
     * 查询管理员用户，用于登录
     * @param paramMap 封装用户登录时填写的信息的map
     * @return 返回查询到的用户
     * */
    User queryUserLogin(HashMap<String, Object> paramMap);
}
