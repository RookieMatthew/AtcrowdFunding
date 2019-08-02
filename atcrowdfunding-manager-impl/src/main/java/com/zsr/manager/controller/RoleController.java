package com.zsr.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsr.bean.Role;
import com.zsr.bean.User;
import com.zsr.manager.service.RoleService;
import com.zsr.utils.Const;
import com.zsr.utils.AjaxMessage;
import com.zsr.utils.VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 角色模块控制层
 * @author shourenzhang
 * @date 2019/8/1 13:35
 */
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 仅用于跳转到角色列表role.jsp
     * */
    @RequestMapping("role/toRolePage")
    public String toUserPage(HttpSession session){
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        if (user==null){
            return "redirect:/login.htm";
        }
        return "role/role";
    }

    /**
     * 异步步请求，以json形式返回角色信息
     */
    @RequestMapping(value = "/roles",method = RequestMethod.GET)
    @ResponseBody
    public AjaxMessage userList(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
                                @RequestParam(value = "selectCondition",defaultValue = "") String selectCondition){
        List<Role> roles;
        try {
            PageHelper.startPage(pn,10);
            if (selectCondition==null||"".equals(selectCondition.trim())){
                roles = roleService.getRoles();
            }else {
                roles = roleService.getRolesByNameLike(selectCondition);
            }
            PageInfo pageInfo = new PageInfo(roles,7);
            return AjaxMessage.success().addInfo("pageInfo",pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("用户信息加载失败！");
        }
    }

    /**
     * 仅用于跳转到角色添加
     * */
    @RequestMapping("role/toAddPage")
    public String toAddPage(HttpSession session){
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        if (user==null){
            return "redirect:/login.htm";
        }
        return "role/add";
    }

    /**
     * 接收角色添加请求，进行角色添加
     */
    @RequestMapping(value = "/role",method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage addUser(Role role){
        try {
            roleService.addRole(role);
            return AjaxMessage.success("角色添加成功！");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("角色添加失败！");
        }
    }

    /**
     * 跳转到角色编辑页面,并查询所要回显的数据
     * */
    @RequestMapping("/role/toUpdatePage")
    public String toUpdatePage(HttpSession session, Integer id, Model model){
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        if (user==null){
            return "redirect:/login.htm";
        }
        Role updateRole = roleService.getRoleById(id);
        model.addAttribute("role",updateRole);
        return "role/edit";
    }
    /**
     * 处理角色修改请求
     * */
    @RequestMapping(value = "/role/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public AjaxMessage updateUser(Role role){
        try {
            roleService.updateRole(role);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("更新信息失败!");
        }
        return AjaxMessage.success("更新信息成功!");
    }
    /**
     * 处理用户删除请求
     * */
    @RequestMapping(value = "/role/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxMessage deleteRole(@PathVariable String id){
        try {
          roleService.deleteRole(Integer.parseInt(id));
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("删除用户失败!");
        }
        return AjaxMessage.success("删除用户成功!");
    }

    /**
     * 处理用户删除请求（批量）
     * */
    @RequestMapping(value = "/roles",method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxMessage deleteRoles(VO vo){
        try {
            roleService.deleteRoles(vo);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("删除用户失败!");
        }
        return AjaxMessage.success("删除用户成功!");
    }

    /**
     * 仅用于跳转到许可分配页面
     * */
    @RequestMapping("/role/toAssignPermissionPage")
    public String toAssignPermissionPage(HttpSession session){
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        if (user==null){
            return "redirect:/login.htm";
        }
        return "role/assignPermission";
    }


    /**
     * 为角色分配许可
     */
    @RequestMapping(value = "/role/assignPermissionForRole",method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage assignPermissionForRole(Integer roleid,VO vo){
        try {
            roleService.assignPermissionForRole(roleid,vo);
            return AjaxMessage.success("许可分配成功！");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("许可分配失败！");
        }
    }
}
