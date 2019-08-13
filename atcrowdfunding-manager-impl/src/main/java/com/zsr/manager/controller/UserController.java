package com.zsr.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsr.bean.Role;
import com.zsr.utils.Const;
import com.zsr.utils.AjaxMessage;
import com.zsr.bean.User;
import com.zsr.manager.service.UserService;
import com.zsr.utils.VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Demo class
 * 用户模块控制层
 * @author shourenzhang
 * @date 2019/7/27 16:16
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 仅用于跳转到user.jsp
     * */
    @RequestMapping("/toUserPage")
    public String toUserPage(){
        return "user/user";
    }

    /**
     * 异步步请求，以json形式返回user信息
     */
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    @ResponseBody
    public AjaxMessage userList(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
                                @RequestParam(value = "selectCondition",defaultValue = "") String selectCondition){
        List<User> users;
        try {
            PageHelper.startPage(pn,15);
            if (selectCondition==null||"".equals(selectCondition.trim())){
                users = userService.getUsers();
            }else {
                users = userService.getUsersByAcctLike(selectCondition);
            }
            PageInfo pageInfo = new PageInfo(users,7);
            return AjaxMessage.success().addInfo("pageInfo",pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("用户信息加载失败！");
        }
    }


    /**
     * 仅用于跳转到user.jsp
     * */
    @RequestMapping("/toAddPage")
    public String toAddPage(){
        return "user/add";
    }

    /**
     * 接收用户添加请求，进行用户添加
     */
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage addUser(User user){
        try {
            userService.addUser(user);
            return AjaxMessage.success("用户添加成功！");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("用户添加失败！");
        }
    }

    /**
     * 跳转到edit.jsp,并查询所要回显的数据
     * */
    @RequestMapping("/toUpdatePage")
    public String toUpdatePage(Integer id, Model model){
        User updateUser = userService.getUserById(id);
        model.addAttribute("user",updateUser);
        return "user/edit";
    }

    /**
     * 处理用户修改请求
     * */
    @RequestMapping(value = "/user/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public AjaxMessage updateUser(User user){
        try {
            userService.updateUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("更新信息失败!");
        }
        return AjaxMessage.success("更新信息成功!");
    }

    /**
     * 处理用户删除请求
     * */
    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxMessage deleteUser(@PathVariable String id){
        try {
            if (!id.contains("-")){
                userService.deleteUser(Integer.parseInt(id));
            }else {
                String[] ids = id.split("-");
                userService.deleteBatchUser(ids);
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("删除用户失败!");
        }
        return AjaxMessage.success("删除用户成功!");
    }


    /**
     * 跳转到用户角色分配页面assignRole.jsp
     * */
    @RequestMapping("/toAssignRolePage")
    public String toAssignRolePage(Integer id, Map map){
        List<Role> allRoleList = userService.queryAllRole();
        List<Integer> userRoleIdList = userService.queryRoleIdByUserId(id);
        List<Role> assignList = new ArrayList<>();
        List<Role> notAssignList = new ArrayList<>();
        for (Role role : allRoleList) {
            if (userRoleIdList.contains(role.getId())){
                assignList.add(role);
            }else {
                notAssignList.add(role);
            }
        }
        map.put("assignList",assignList);
        map.put("notAssignList",notAssignList);
        map.put("userId",id);
        return "user/assignRole";
    }
   /**
    * 用户角色分配
    * */
   @RequestMapping(value = "/user/assignRoleToUser/{userId}",method = RequestMethod.POST)
   @ResponseBody
   public AjaxMessage assignRoleToUser(@PathVariable String userId, VO vo){
       try {
           userService.assignRoleToUser(userId,vo);
       }catch (Exception e){
           e.printStackTrace();
           return AjaxMessage.fail("角色分配失败！");
       }
      return AjaxMessage.success("角色分配成功");
   }

    /**
     * 用户角色移除
     * */
    @RequestMapping(value = "/user/removeRoleToUser/{userId}",method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxMessage removeRoleToUser(@PathVariable String userId, VO vo){
        System.out.println(userId);
        try {
            userService.removeRoleToUser(userId,vo);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("角色分配失败！");
        }
        return AjaxMessage.success("角色分配成功");
    }
}
