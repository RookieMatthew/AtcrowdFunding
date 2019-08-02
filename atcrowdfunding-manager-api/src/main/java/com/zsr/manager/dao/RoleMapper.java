package com.zsr.manager.dao;

import com.zsr.bean.Role;
import com.zsr.utils.VO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    List<Role> getRolesByNameLike(String selectCondition);

    int deleteRoles(@Param("vo") VO vo);

    void deleteAllPermission(Integer roleid);

    void assignPermissionForRole(@Param("roleid") Integer roleid,@Param("vo") VO vo);
}