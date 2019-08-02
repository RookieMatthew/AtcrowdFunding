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
}
