package com.zsr.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsr.utils.Message;
import com.zsr.bean.User;
import com.zsr.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * 仅用于跳转到user.jsp
     * */
    @RequestMapping("/toUserPage")
    public String toUserPage(){
        return "user";
    }

    /**
     * 异步步请求，以json形式返回user信息
     */
    @RequestMapping("/users")
    @ResponseBody
    public Message userList(@RequestParam(value = "pn",defaultValue = "1") Integer pn){
        try {
            PageHelper.startPage(pn,15);
            List<User> users = userService.getUsers();
            System.out.println(users);
            PageInfo pageInfo = new PageInfo(users,7);
            return Message.success().addInfo("pageInfo",pageInfo);
        }catch (Exception e){
            return Message.fail("用户信息加载失败！");
        }
    }


    /**
     * 同步请求，携带user信息返回到user.jsp页面
     */
   /* @RequestMapping("/users")
    public String userList(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){
        PageHelper.startPage(pn,15);
        List<User> users = userService.getUsers();
        System.out.println(users);
        PageInfo pageInfo = new PageInfo(users,7);
        model.addAttribute("pageInfo",pageInfo);
        return "user";
    }*/
}
