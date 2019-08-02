package com.zsr.manager.controller;

import com.zsr.bean.Permission;
import com.zsr.bean.User;
import com.zsr.manager.service.PermissionService;
import com.zsr.utils.Const;
import com.zsr.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demo class
 * 许可模块控制层
 * @author shourenzhang
 * @date 2019/8/1 17:56
 */
@Controller
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    /**
     * 仅用于跳转到许可模块主页面
     * */
    @RequestMapping("/permission/toPermissionPage")
    public String toPermissionPage(HttpSession session){
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        if (user==null){
            return "redirect:/login.htm";
        }
        return "permission/permission";
    }


    /**
     * 得到许可树的节点数据
     * */
    @ResponseBody
    @RequestMapping("/permission/getNodeData")
    public Message getNodeData(){
        try {
            Permission rootPermission = null;
            List<Permission> allPermissions = permissionService.getAllPermissions();
            Map<Integer,Permission> map = new HashMap<>(allPermissions.size());
            for (Permission allPermission : allPermissions) {
                map.put(allPermission.getId(),allPermission);
                allPermission.setOpen(true);
            }
            rootPermission = map.get(0);
            for (Permission allPermission : allPermissions) {
                if (allPermission.getPid()==0){
                    rootPermission = allPermission;
                }else{
                    //得到当前节点的父亲节点
                    Permission parent = map.get(allPermission.getPid());
                    //将该节点加入父亲节点的子节点
                    parent.getChildren().add(allPermission);
                }
            }
            return Message.success().addInfo("zNodes",rootPermission);
        }catch (Exception e){
            e.printStackTrace();
            return Message.fail("许可树加载失败！");
        }
    }

    /**
     * 仅用于跳转许可添加页面
     * */
    @RequestMapping("/permission/toAddPage")
    public String toAddPage(HttpSession session){
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        if (user==null){
            return "redirect:/login.htm";
        }
        return "permission/add";
    }

    /**
     * 响应许可添加请求
     * */
    @ResponseBody
    @RequestMapping("/permission")
    public Message addPermission(Permission permission){
        try {
            permissionService.addPermission(permission);
            return Message.success();
        }catch (Exception e){
            e.printStackTrace();
            return Message.fail("添加许可失败！");
        }
    }
}
