package com.zsr.manager.service.impl;

import com.zsr.bean.Permission;
import com.zsr.manager.dao.PermissionMapper;
import com.zsr.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Demo class
 * 许可维护模块业务层实现
 * @author shourenzhang
 * @date 2019/8/1 17:27
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Permission getRootPermission() {
        return permissionMapper.getRootPermission();
    }

    @Override
    public List<Permission> getChildrenPermissionByPid(Integer id) {
        return permissionMapper.getChildrenPermissionByPid(id);
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionMapper.selectAll();
    }

    @Override
    public int addPermission(Permission permission) {
        return permissionMapper.insert(permission);
    }

    @Override
    public Permission getPermissionById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updatePermission(Permission permission) {
        return permissionMapper.updateByPrimaryKey(permission);
    }

    @Override
    public int deletePermission(Integer id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Integer> getAssignedPermissionIdsByRoleId(Integer roleid) {
        return permissionMapper.getAssignedPermissionIdsByRoleId(roleid);
    }
}
