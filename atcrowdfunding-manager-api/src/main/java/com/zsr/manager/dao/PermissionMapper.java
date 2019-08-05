package com.zsr.manager.dao;

import com.zsr.bean.Permission;
import java.util.List;
import java.util.Set;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    Permission getRootPermission();

    List<Permission> getChildrenPermissionByPid(Integer id);

    List<Integer> getAssignedPermissionIdsByRoleId(Integer roleid);

    Set<String> getAllPermissionsUrl();
}