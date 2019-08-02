package com.zsr.manager.service;

import com.zsr.bean.Permission;

import java.util.List;

/**
 * Demo class
 * 许可维护模块业务层接口
 * @author shourenzhang
 * @date 2019/8/1 17:26
 */
public interface PermissionService {
    /**
     * 查询许可树的根节点
     * @return 根节点
     * */
    Permission getRootPermission();
    /**
     * 查询许可树的某个节点的所有子节点
     * @param id 要查询的节点id
     * @return 子节点的集合
     * */
    List<Permission> getChildrenPermissionByPid(Integer id);
    /**
     * 查询许可树所有节点
     * @return 节点的集合
     * */
    List<Permission> getAllPermissions();
    /**
     * 添加许可树的节点
     * @param permission 要添加的节点
     * @return 影响数据库行数
     * */
    int addPermission(Permission permission);
    /**
     * 查询许可树的某个节点
     * @param id 要查询的节点id
     * @return 查询到的节点
     * */
    Permission getPermissionById(Integer id);
    /**
     * 修改许可树的节点
     * @param permission 修改后的节点
     * @return 影响数据库行数
     * */
    int updatePermission(Permission permission);
    /**
     * 删除许可树的节点
     * @param id 要删除节点的id
     * @return 影响数据库行数
     * */
    int deletePermission(Integer id);
    /**
     * 通过角色id查询该角色已分配的权限id
     * @param roleid 要查询的角色id
     * @return 所有的已分配权限id
     * */
    List<Integer> getAssignedPermissionIdsByRoleId(Integer roleid);
}
