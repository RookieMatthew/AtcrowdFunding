package com.zsr.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsr.utils.AjaxMessage;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demo class
 * 流程管理控制层
 * @author shourenzhang
 * @date 2019/8/9 15:33
 */
@Controller
public class ProcessController {

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 仅用于跳转到流程管理模块主页面
     * */
    @RequestMapping("/process/toProcessPage")
    public String toProcessPage(){
        return "/process/process";
    }


    /**
     * 异步步请求，以json形式返回user信息
     */
    @RequestMapping(value = "/processs",method = RequestMethod.GET)
    @ResponseBody
    public AjaxMessage userList(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
                                @RequestParam(value = "selectCondition",defaultValue = "") String selectCondition){
        int pageSize = 3;
        List<Map<String,Object>> processDefinitionList = new ArrayList<>();
        List<ProcessDefinition> processDefinitions;
        List<ProcessDefinition> allList;
        try {
            if (selectCondition==null||"".equals(selectCondition.trim())){
                if (pn==1){
                    processDefinitions = repositoryService.createProcessDefinitionQuery().listPage(0,pageSize);
                }else {
                    processDefinitions = repositoryService.createProcessDefinitionQuery().listPage(pageSize*(pn-1),pageSize);
                }
                allList = repositoryService.createProcessDefinitionQuery().list();
            }else {
                if (pn==1){
                    processDefinitions = repositoryService.createProcessDefinitionQuery().processDefinitionNameLike("%"+selectCondition+"%").listPage(0,pageSize);
                }else {
                    processDefinitions = repositoryService.createProcessDefinitionQuery().processDefinitionNameLike("%"+selectCondition+"%").listPage(pageSize*(pn-1),pageSize);
                }
                allList = repositoryService.createProcessDefinitionQuery().processDefinitionNameLike("%"+selectCondition+"%").list();
            }
            for (ProcessDefinition processDefinition : processDefinitions) {
                HashMap<String, Object> map = new HashMap<>(processDefinitions.size());
                map.put("id",processDefinition.getId());
                map.put("name",processDefinition.getName());
                map.put("key",processDefinition.getKey());
                map.put("version",processDefinition.getVersion());
                processDefinitionList.add(map);
            }
            PageInfo pageInfo = new PageInfo(processDefinitionList,7);
            int[] pageNums = new int[allList.size()%pageSize==0?allList.size()/pageSize:allList.size()/pageSize+1];
            for (int i = 0; i < pageNums.length; i++) {
                pageNums[i] = i+1;
            }
            pageInfo.setNavigatepageNums(pageNums);
            if (pn==1){
                pageInfo.setHasPreviousPage(false);
                pageInfo.setIsFirstPage(true);
                pageInfo.setHasNextPage(true);
                pageInfo.setIsLastPage(false);
            } else if (pn==pageNums[pageNums.length-1]){
                pageInfo.setHasNextPage(false);
                pageInfo.setIsLastPage(true);
                pageInfo.setHasPreviousPage(true);
                pageInfo.setIsFirstPage(false);
            }else {
                pageInfo.setHasNextPage(true);
                pageInfo.setHasPreviousPage(true);
                pageInfo.setIsLastPage(false);
                pageInfo.setIsFirstPage(false);
            }

            pageInfo.setPageNum(pn);
            pageInfo.setPages(pageNums.length);
            return AjaxMessage.success("流程定义信息加载成功！").addInfo("pageInfo",pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("流程定义信息加载失败！");
        }
    }

    /**
     * 异步步请求，流程定义部署（添加）
     */
    @RequestMapping(value = "/process",method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage userList(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest)request;
            MultipartFile uploadFile = mulRequest.getFile("uploadFile");
            repositoryService.createDeployment()
                    .addInputStream(uploadFile.getOriginalFilename(),uploadFile.getInputStream())
            .deploy();
            return AjaxMessage.success("流程定义部署成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxMessage.fail("流程定义部署失败！");
        }
    }

    /**
     * 删除流程定义
     */
    @RequestMapping(value = "/process/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxMessage userList(@PathVariable("id") String id) {
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(id).singleResult();
            repositoryService.deleteDeployment(processDefinition.getDeploymentId(),true);
            return AjaxMessage.success("流程定义删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxMessage.fail("流程定义删除失败！");
        }
    }
}
