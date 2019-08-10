package com.zsr.controller;

import com.zsr.bean.Member;
import com.zsr.bean.Permission;
import com.zsr.bean.User;
import com.zsr.manager.service.UserService;
import com.zsr.potal.service.MemberService;
import com.zsr.utils.Const;
import com.zsr.utils.MD5Util;
import com.zsr.utils.AjaxMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private MemberService memberService;

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
    public String toLoginHtml(HttpServletRequest request,HttpSession session){
        Cookie[] cookies = request.getCookies();
        String loginCode = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("loginCode")){
                loginCode = cookie.getValue();
                break;
            }
        }
        if (loginCode!=null){
            HashMap<String, Object> paramMap = new HashMap<>(5);
            String[] strings = loginCode.split("&");
            paramMap.put("loginacct",strings[0].split("=")[1]);
            paramMap.put("userpswd",strings[1].split("=")[1]);
            paramMap.put("usertype",strings[2].split("=")[1]);
            if("user".equals(paramMap.get("usertype"))){
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
                }catch (Exception e){
                    e.printStackTrace();
                    return "login";
                }
                return "redirect:/main.htm";
            }
            if("member".equals(paramMap.get("usertype"))){
                Member member = memberService.queryMemberLogin(paramMap);
                session.setAttribute(Const.LOGIN_MEMBER,member);
                return "redirect:/member/member.htm";
            }
        }
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
     * 管理员登陆成功跳转到管理主页面，main.jsp
     */
    @RequestMapping("/main")
    public String toMainHtml(){
        return "main";
    }

    /**
     * 会员登陆成功跳转到会员主页面，member.jsp
     */
    @RequestMapping("/member/member")
    public String toMemberHtml(){
        return "member/member";
    }

    /**
     * 进行后台用户（管理员）登录操作，异步登陆
     */
    @ResponseBody
    @RequestMapping("/doLogin")
    public AjaxMessage doLogin(String loginacct, String userpswd, String usertype, String remember,
                               HttpSession session,HttpServletResponse response){
        try {
            HashMap<String, Object> paramMap = new HashMap<>(5);
            paramMap.put("loginacct",loginacct);
            paramMap.put("userpswd", MD5Util.digest(userpswd));
            paramMap.put("usertype",usertype);
            if("user".equals(usertype)){
                User user = userService.queryUserLogin(paramMap);
                session.setAttribute(Const.LOGIN_USER,user);
                if ("1".equals(remember)){
                    setLoginCookie(paramMap,response);
                }
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
                    return AjaxMessage.success("登陆成功！").addInfo("usertype",usertype);
                }catch (Exception e){
                    return AjaxMessage.success("菜单加载失败！");
                }
            }else if("member".equals(usertype)){
                Member member = memberService.queryMemberLogin(paramMap);
                session.setAttribute(Const.LOGIN_MEMBER,member);
                if ("1".equals(remember)){
                    setLoginCookie(paramMap,response);
                }
                return AjaxMessage.success("登陆成功！").addInfo("usertype",usertype);
            }else {
                return AjaxMessage.fail("用户类型不合法！");
            }
        }catch (Exception e){
            return AjaxMessage.fail("登陆失败！");
        }
    }

    private void setLoginCookie(HashMap<String, Object> map, HttpServletResponse response){
        String loginCode = "\"loginacct="+map.get("loginacct")+"&userpswd="+map.get("userpswd")+"&usertype="+map.get("usertype")+"\"";
        System.out.println(loginCode);
        Cookie c = new Cookie("loginCode",loginCode);
        c.setMaxAge(60*60*24*14);
        c.setPath("/");
        response.addCookie(c);
    }

    @ResponseBody
    @RequestMapping("/test")
    public AjaxMessage test(){
        return AjaxMessage.success();
    }
}
