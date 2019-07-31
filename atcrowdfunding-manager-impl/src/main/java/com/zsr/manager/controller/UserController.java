package com.zsr.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsr.utils.Const;
import com.zsr.utils.Message;
import com.zsr.bean.User;
import com.zsr.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public String toUserPage(HttpSession session){
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        if (user==null){
            return "redirect:/login.htm";
        }
        return "user";
    }

    /**
     * 异步步请求，以json形式返回user信息
     */
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    @ResponseBody
    public Message userList(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
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
            return Message.success().addInfo("pageInfo",pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return Message.fail("用户信息加载失败！");
        }
    }


    /**
     * 仅用于跳转到user.jsp
     * */
    @RequestMapping("/toAddPage")
    public String toAddPage(HttpSession session){
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        if (user==null){
            return "redirect:/login.htm";
        }
        return "add";
    }

    /**
     * 接收用户添加请求，进行用户添加
     */
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    @ResponseBody
    public Message addUser(User user){
        try {
            userService.addUser(user);
            return Message.success("用户添加成功！");
        }catch (Exception e){
            e.printStackTrace();
            return Message.fail("用户添加失败！");
        }
    }

    /**
     * 跳转到edit.jsp,并查询所要回显的数据
     * */
    @RequestMapping("/toUpdatePage")
    public String toUpdatePage(HttpSession session, Integer id, Model model){
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        if (user==null){
            return "redirect:/login.htm";
        }
        User updateUser = userService.getUserById(id);
        model.addAttribute("user",updateUser);
        return "edit";
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public Message updateUser(User user){
        try {
            userService.updateUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return Message.fail("更新信息失败!");
        }
        return Message.success("更新信息成功!");
    }

}
