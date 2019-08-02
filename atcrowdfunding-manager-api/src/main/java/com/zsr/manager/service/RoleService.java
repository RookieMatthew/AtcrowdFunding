package com.zsr.manager.service;

import com.zsr.bean.Role;
import com.zsr.utils.VO;

import java.util.List;

/**
 * 角色模块业务层接口
 * @author shourenzhang
 * @date 2019/8/1 13:35
 */
public interface RoleService {
    /**
     * 得到所有角色信息
     * @return 角色信息集合
     * */
    List<Role> getRoles();
    /**
     * 模糊匹配得到所有角色信息
     * @param  selectCondition 模糊匹配的字段
     * @return 角色信息集合
     * */
    List<Role> getRolesByNameLike(String selectCondition);
    /**
     * 删除某个角色
     * @param  id 要删除角色的id
     * @return 数据库影响行数
     * */
    int deleteRole(Integer id);
    /**
     * 删除多个角色
     * @param  vo 封装要删除角色的id
     * @return 数据库影响行数
     * */
    int deleteRoles(VO vo);
    /**
     * 添加某个角色
     * @param  role 要添加角色的信息
     * @return 数据库影响行数
     * */
    int addRole(Role role);
    /**
     * 通过id查找某个角色
     * @param  id 要查找角色的id
     * @return 数据库影响行数
     * */
    Role getRoleById(Integer id);
    /**
     * 更新某个角色信息
     * @param  role 要更新的角色信息
     * */
    void updateRole(Role role);
    /**
     * 为某个角色分配许可
     * @param  roleid 角色id
     * @param vo 封装要分配的id集合
     * */
    void assignPermissionForRole(Integer roleid, VO vo);
}
