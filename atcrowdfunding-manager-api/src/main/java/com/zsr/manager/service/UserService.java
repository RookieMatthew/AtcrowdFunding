package com.zsr.manager.service;

import com.zsr.bean.Permission;
import com.zsr.bean.Role;
import com.zsr.bean.User;
import com.zsr.utils.VO;

import java.util.HashMap;
import java.util.List;

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
    /**
     * 查询所有管理员用户信息
     * @return 返回所有信息的集合
     * */
    List<User> getUsers();

    /**
     * 添加一个员工
     * @param user 要添加的员工信息
     * */
    void addUser(User user);

    /**
     * 查询管理员用户账号中存在selectCondition的用户，用于模糊查询
     * @param selectCondition 查询条件
     * @return 返回查询到的用户集合
     * */
    List<User> getUsersByAcctLike(String selectCondition);
    /**
     * 通过主键id查询某个个员工
     * @param id 要查询的员工id
     * @return 查询到的员工信息
     * */
    User getUserById(Integer id);

    /**
     * 修改员工信息
     * @param user 要更新的员工信息
     * @return 影响数据库行数
     * */
    int updateUser(User user);
    /**
     * 删除员工
     * @param id 要删除员工的主键id
     * @return 影响数据库行数
     * */
    int deleteUser(Integer id);

    /**
     * 批量删除用户
     * @param ids 要删除用户的主键id数组
     * @return 影响数据库行数
     * */
    int deleteBatchUser(String[] ids);

    /**
     * 查询用户所能分配的所有角色
     * @return 所有角色的集合
     * */
    List<Role> queryAllRole();

    /**
     * 查询某个用户被分配的所有角色的角色id
     * @param id 要查询员工的主键id
     * @return 角色id集合
     * */
    List<Integer> queryRoleIdByUserId(Integer id);
    /**
     * 为用户分配角色
     * @param userId 要分配的员工的主键id
     *  @param vo 封装了即将被分配的角色
     * @return 影响数据库条数
     * */
    int assignRoleToUser(String userId, VO vo);
    /**
     * 移除用户担任角色
     * @param userId 要操作的员工的主键id
     *  @param vo 封装了即将要移除的角色
     * @return 影响数据库条数
     * */
    int removeRoleToUser(String userId, VO vo);

    /**
     * 通过用户id得到用户拥有的许可
     * @param id 用户id
     * @return 许可集合
     * */
    List<Permission> queryPermissionByUserId(Integer id);
}
