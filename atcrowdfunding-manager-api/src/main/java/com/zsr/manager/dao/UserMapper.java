package com.zsr.manager.dao;

import com.zsr.bean.Permission;
import com.zsr.bean.Role;
import com.zsr.bean.User;
import com.zsr.utils.VO;
import org.apache.ibatis.annotations.Param;

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

    List<User> selectUsersByAcctLike(String selectCondition);

    List<Role> queryAllRole();

    List<Integer> queryRoleIdByUserId(Integer id);

    int assignRoleToUser(@Param("userId") String userId, @Param("vo") VO vo);

    int removeRoleToUser(@Param("userId")String userId, @Param("vo") VO vo);

    List<Permission> queryPermissionByUserId(Integer id);
}