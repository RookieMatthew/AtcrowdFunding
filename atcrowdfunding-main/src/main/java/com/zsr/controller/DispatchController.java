package com.zsr.controller;

import com.zsr.bean.User;
import com.zsr.manager.service.UserService;
import com.zsr.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * 进行登录操作
     */
    @RequestMapping("/doLogin")
    public String doLogin(String loginacct, String userpswd, String usertype, HttpSession session){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginacct",loginacct);
        paramMap.put("userpswd",userpswd);
        paramMap.put("usertype",usertype);
        User user = userService.queryUserLogin(paramMap);
        session.setAttribute(Const.LOGIN_USER,user);
        return "redirect:/main.htm";
    }
}
