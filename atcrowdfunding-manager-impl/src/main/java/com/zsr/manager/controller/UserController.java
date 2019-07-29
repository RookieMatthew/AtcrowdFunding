package com.zsr.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsr.bean.User;
import com.zsr.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Demo class
 *
 * @author shourenzhang
 * @date 2019/7/27 16:16
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/users")
    public String userList(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){
        PageHelper.startPage(pn,15);
        List<User> users = userService.getUsers();
        System.out.println(users);
        PageInfo pageInfo = new PageInfo(users,7);
        model.addAttribute("pageInfo",pageInfo);
        return "user";
    }
}
