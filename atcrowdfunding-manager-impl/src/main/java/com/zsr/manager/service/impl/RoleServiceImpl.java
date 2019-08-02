package com.zsr.manager.service.impl;

import com.zsr.bean.Role;
import com.zsr.manager.dao.RoleMapper;
import com.zsr.manager.service.RoleService;
import com.zsr.utils.VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色模块业务层实现
 * @author shourenzhang
 * @date 2019/8/1 13:36
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRoles() {
        return roleMapper.selectAll();
    }

    @Override
    public int deleteRoles(VO vo) {
        return roleMapper.deleteRoles(vo);
    }

    @Override
    public int addRole(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteRole(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Role> getRolesByNameLike(String selectCondition) {
        return roleMapper.getRolesByNameLike(selectCondition);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void assignPermissionForRole(Integer roleid, VO vo) {
        roleMapper.deleteAllPermission(roleid);
        roleMapper.assignPermissionForRole(roleid,vo);
    }
}
