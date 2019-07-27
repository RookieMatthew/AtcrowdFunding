package com.zsr.manager.dao;

import com.zsr.bean.User;
import java.util.List;
import java.util.Map;

/**
 * @author ACER
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User queryUserLogin(Map map);
}