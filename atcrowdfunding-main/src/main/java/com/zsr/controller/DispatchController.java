package com.zsr.controller;

import com.zsr.bean.Permission;
import com.zsr.bean.User;
import com.zsr.manager.service.UserService;
import com.zsr.utils.Const;
import com.zsr.utils.MD5Util;
import com.zsr.utils.AjaxMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Demo class
 * 页面跳转控制器
 * @author shourenzhang
 * @date 2019/7/27 10:52
 */
@Controller
public class DispatchController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到首页，index.jsp
     */
    @RequestMapping("/index")
    public String toIndexHtml(){
        return "index";
    }

    /**
     * 跳转到登陆页面，login.jsp
     */
    @RequestMapping("/login")
    public String toLoginHtml(){
        return "login";
    }

    /**
     * 跳转到注册页面，reg.jsp
     */
    @RequestMapping("/reg")
    public String toRegHtml(){
        return "reg";
    }

    /**
     * 跳转到注册页面，main.jsp
     */
    @RequestMapping("/doLogout")
    public String doLogout(HttpSession session){
        session.invalidate();
        return "redirect:/index.htm";
    }

    /**
     * 登陆成功跳转到管理主页面，main.jsp
     */
    @RequestMapping("/main")
    public String toMainHtml(){
        return "main";
    }

    /**
     * 进行后台用户（管理员）登录操作，同步登陆
     */
    /*@RequestMapping("/doLogin")
    public String doLogin(String loginacct, String userpswd, String usertype, HttpSession session){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginacct",loginacct);
        paramMap.put("userpswd",userpswd);
        paramMap.put("usertype",usertype);
        User user = userService.queryUserLogin(paramMap);
        session.setAttribute(Const.LOGIN_USER,user);
        return "redirect:/main.htm";
    }*/
    /**
     * 进行后台用户（管理员）登录操作，异步登陆
     */
    @ResponseBody
    @RequestMapping("/doLogin")
    public AjaxMessage doLogin(String loginacct, String userpswd, String usertype, HttpSession session){
        try {
            HashMap<String, Object> paramMap = new HashMap<>(5);
            paramMap.put("loginacct",loginacct);
            paramMap.put("userpswd", MD5Util.digest(userpswd));
            paramMap.put("usertype",usertype);
            User user = userService.queryUserLogin(paramMap);
            session.setAttribute(Const.LOGIN_USER,user);
            try {
                List<Permission> userPermissionList = userService.queryPermissionByUserId(user.getId());
                Set<String> allPermissionUrls = new HashSet<>();
                Map<Integer,Permission> map = new HashMap<>(userPermissionList.size());
                for (Permission permission : userPermissionList) {
                    map.put(permission.getId(),permission);
                    allPermissionUrls.add(permission.getUrl());
                }
                session.setAttribute(Const.PERMISSION_URLS,allPermissionUrls);
                Permission rootPermission = null;
                for (Permission child : userPermissionList) {
                    if (child.getPid()==0){
                        rootPermission = child;
                    }else {
                        map.get(child.getPid()).getChildren().add(child);
                    }
                }
                session.setAttribute("rootPermission",rootPermission);
                return AjaxMessage.success("登陆成功！");
            }catch (Exception e){
                return AjaxMessage.success("菜单加载失败！");
            }
        }catch (Exception e){
            return AjaxMessage.fail("登陆失败！");
        }
    }

    @ResponseBody
    @RequestMapping("/test")
    public AjaxMessage test(){
        return AjaxMessage.success();
    }
}
