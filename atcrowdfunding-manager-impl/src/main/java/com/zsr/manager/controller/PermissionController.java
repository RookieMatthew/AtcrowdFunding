package com.zsr.manager.controller;

import com.zsr.bean.Permission;
import com.zsr.manager.service.PermissionService;
import com.zsr.utils.AjaxMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String toPermissionPage(){
        return "permission/permission";
    }


    /**
     * 得到许可树的节点数据
     * */
    @ResponseBody
    @RequestMapping(value = "/permission/getNodeData",method = RequestMethod.GET)
    public AjaxMessage getNodeData(){
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
            return AjaxMessage.success().addInfo("zNodes",rootPermission);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("许可树加载失败！");
        }
    }

    /**
     * 仅用于跳转许可添加页面
     * */
    @RequestMapping("/permission/toAddPage")
    public String toAddPage(){
        return "permission/add";
    }

    /**
     * 响应许可添加请求
     * */
    @ResponseBody
    @RequestMapping(value = "/permission",method = RequestMethod.POST)
    public AjaxMessage addPermission(Permission permission){
        try {
            permissionService.addPermission(permission);
            return AjaxMessage.success();
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("添加许可失败！");
        }
    }

    /**
     * 跳转到修改页面，并回显数据
     * */
    @RequestMapping("/permission/toUpdatePage")
    public String toUpdatePage(Integer id,Map map){
        Permission permission = permissionService.getPermissionById(id);
        map.put("permission",permission);
        return "permission/edit";
    }

    /**
     * 响应许可修改请求
     * */
    @ResponseBody
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.PUT)
    public AjaxMessage updatePermission(Permission permission){
        try {
            permissionService.updatePermission(permission);
            return AjaxMessage.success();
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("修改许可失败！");
        }
    }

    /**
     * 响应许可删除请求
     * */
    @ResponseBody
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.DELETE)
    public AjaxMessage deletePermission(@PathVariable("id") Integer id){
        try {
            permissionService.deletePermission(id);
            return AjaxMessage.success();
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("修改许可失败！");
        }
    }


    /**
     * 得到许可树的节点数据(树异步加载)
     * */
    @ResponseBody
    @RequestMapping(value = "/permission/loadDataAsync",method = RequestMethod.GET)
    public Object loadDataAsync(Integer roleid){
            Permission rootPermission = null;
            List<Permission> allPermissions = permissionService.getAllPermissions();
            List<Integer> assignedPermissionIds = permissionService.getAssignedPermissionIdsByRoleId(roleid);
            Map<Integer,Permission> map = new HashMap<>(allPermissions.size());
            for (Permission allPermission : allPermissions) {
                map.put(allPermission.getId(),allPermission);
                allPermission.setOpen(true);
                if (assignedPermissionIds.contains(allPermission.getId())){
                    allPermission.setChecked(true);
                }
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
            return rootPermission;
    }
}
