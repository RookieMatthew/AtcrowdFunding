package com.zsr.controller;

import com.zsr.bean.User;
import com.zsr.manager.service.UserService;
import com.zsr.utils.Const;
import com.zsr.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

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
    public Message doLogin(String loginacct, String userpswd, String usertype, HttpSession session){
        try {
            HashMap<String, Object> paramMap = new HashMap<>(5);
            paramMap.put("loginacct",loginacct);
            paramMap.put("userpswd",userpswd);
            paramMap.put("usertype",usertype);
            User user = userService.queryUserLogin(paramMap);
            session.setAttribute(Const.LOGIN_USER,user);
        }catch (Exception e){
            return Message.fail("登陆失败！");
        }
        System.out.println("成功");
        return Message.success("登陆成功！");
    }

    @ResponseBody
    @RequestMapping("/test")
    public Message test(){
        return Message.success();
    }
}
