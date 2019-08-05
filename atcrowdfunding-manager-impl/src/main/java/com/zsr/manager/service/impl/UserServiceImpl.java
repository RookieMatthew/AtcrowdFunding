package com.zsr.manager.service.impl;

import com.zsr.bean.Permission;
import com.zsr.bean.Role;
import com.zsr.bean.User;
import com.zsr.exception.LoginFailException;
import com.zsr.manager.dao.UserMapper;
import com.zsr.manager.service.UserService;
import com.zsr.utils.Const;
import com.zsr.utils.MD5Util;
import com.zsr.utils.VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setCreatetime(sdf.format(date));
        user.setUserpswd(MD5Util.digest(Const.INIT_PSWD));
        userMapper.insert(user);
    }

    @Override
    public List<User> getUsersByAcctLike(String selectCondition) {
        return userMapper.selectUsersByAcctLike(selectCondition);
    }

    @Override
    public User getUserById(Integer id) {
       return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBatchUser(String[] ids) {
        int i = 0;
        for (String id : ids) {
            userMapper.deleteByPrimaryKey(Integer.parseInt(id));
            i++;
        }
        return i;
    }

    @Override
    public List<Role> queryAllRole() {
        return userMapper.queryAllRole();
    }

    @Override
    public List<Integer> queryRoleIdByUserId(Integer id) {
        return userMapper.queryRoleIdByUserId(id);
    }

    @Override
    public int assignRoleToUser(String userId, VO vo) {
        return userMapper.assignRoleToUser(userId,vo);
    }

    @Override
    public int removeRoleToUser(String userId, VO vo) {
        return userMapper.removeRoleToUser(userId,vo);
    }

    @Override
    public List<Permission> queryPermissionByUserId(Integer id) {
        return userMapper.queryPermissionByUserId(id);
    }
}
