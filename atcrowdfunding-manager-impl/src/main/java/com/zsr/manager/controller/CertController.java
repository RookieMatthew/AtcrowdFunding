package com.zsr.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsr.bean.Cert;
import com.zsr.manager.service.CertService;
import com.zsr.utils.AjaxMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Demo class
 * 资质维护模块控制层
 * @author shourenzhang
 * @date 2019/8/10 14:09
 */
@Controller
public class CertController {

    @Autowired
    private CertService certService;

    @RequestMapping("/cert/toCertPage")
    public String toCertPage(){
        return "cert/cert";
    }

    @RequestMapping(value = "/certs",method = RequestMethod.GET)
    @ResponseBody
    public AjaxMessage getCerts(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
                                @RequestParam(value = "selectCondition",defaultValue = "") String selectCondition){
        List<Cert> certs;
        try {
            PageHelper.startPage(pn,10);
            if (selectCondition==null||"".equals(selectCondition.trim())){
                certs = certService.getCerts();
            }else {
                certs = certService.getCertsByNameLike(selectCondition);
            }
            PageInfo pageInfo = new PageInfo(certs,7);
            return AjaxMessage.success().addInfo("pageInfo",pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("资质信息加载失败！");
        }
    }

    /**
     * 处理资质删除请求
     * */
    @RequestMapping(value = "/cert/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxMessage deleteCert(@PathVariable String id){
        try {
            if (!id.contains("-")){
                certService.deleteCert(Integer.parseInt(id));
            }else {
                String[] ids = id.split("-");
                certService.deleteBatchCert(ids);
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("删除资质信息失败!");
        }
        return AjaxMessage.success("删除资质信息成功!");
    }

    @RequestMapping("/cert/toAddPage")
    public String toAddPage(){
        return "cert/add";
    }

    /**
     * 接收用户添加请求，进行用户添加
     */
    @RequestMapping(value = "/cert",method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage addCert(Cert cert){
        try {
            certService.addCert(cert);
            return AjaxMessage.success("资质信息添加成功！");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("资质信息添加失败！");
        }
    }

    /**
     * 跳转到edit.jsp,并查询所要回显的数据
     * */
    @RequestMapping("/cert/toUpdatePage")
    public String toUpdatePage(Integer id, Model model){
        Cert updateCert = certService.getCertById(id);
        model.addAttribute("cert",updateCert);
        return "cert/edit";
    }

    @RequestMapping(value = "/cert/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public AjaxMessage updateCert(Cert cert){
        try {
            certService.updateCert(cert);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("更新资质信息失败!");
        }
        return AjaxMessage.success("更新资质信息成功!");
    }

}
