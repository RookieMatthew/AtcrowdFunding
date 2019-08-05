package com.zsr.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsr.bean.Advertisement;
import com.zsr.bean.Message;
import com.zsr.bean.User;
import com.zsr.manager.service.AdvertisementService;
import com.zsr.utils.AjaxMessage;
import com.zsr.utils.Const;
import com.zsr.utils.VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Demo class
 * 广告模块控制层
 * @author shourenzhang
 * @date 2019/8/5 15:06
 */
@Controller
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    /**
     * 仅用于跳转到广告列表
     * */
    @RequestMapping("advertisement/toAdvertisementPage")
    public String toAdvertisementPage(){
        return "advertisement/advertisement";
    }

    /**
     * 异步步请求，以json形式返回角色信息
     */
    @RequestMapping(value = "/advertisements",method = RequestMethod.GET)
    @ResponseBody
    public AjaxMessage advertisementList(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
                                @RequestParam(value = "selectCondition",defaultValue = "") String selectCondition){
        List<Advertisement> advertisements;
        try {
            PageHelper.startPage(pn,10);
            if (selectCondition==null||"".equals(selectCondition.trim())){
                advertisements = advertisementService.getAdvertisements();
            }else {
                advertisements = advertisementService.getAdvertisementsByNameLike(selectCondition);
            }
            PageInfo pageInfo = new PageInfo(advertisements,7);
            return AjaxMessage.success().addInfo("pageInfo",pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("广告信息加载失败！");
        }
    }

    /**
     * 处理用户删除请求
     * */
    @RequestMapping(value = "/advertisement/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxMessage deleteAdvertisement(@PathVariable String id){
        try {
            advertisementService.deleteAdvertisement(Integer.parseInt(id));
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("删除广告失败!");
        }
        return AjaxMessage.success("删除广告成功!");
    }

    /**
     * 处理用户删除请求（批量）
     * */
    @RequestMapping(value = "/advertisements",method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxMessage deleteAdvertisements(VO vo){
        try {
            advertisementService.deleteAdvertisements(vo);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("删除广告失败!");
        }
        return AjaxMessage.success("删除广告成功!");
    }

    /**
     * 跳转到广告编辑页面,并查询所要回显的数据
     * */
    @RequestMapping("/advertisement/toUpdatePage")
    public String toUpdatePage( Integer id, Model model){
        Advertisement updateAdvertisement = advertisementService.getAdvertisementById(id);
        model.addAttribute("advertisement",updateAdvertisement);
        return "advertisement/edit";
    }
    /**
     * 处理广告修改请求
     * */
    @RequestMapping(value = "/advertisement/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public AjaxMessage updateUser(Advertisement advertisement){
        try {
            advertisementService.updateAdvertisement(advertisement);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail("更新信息失败!");
        }
        return AjaxMessage.success("更新信息成功!");
    }

    /**
     * 仅用于跳转到广告添加
     * */
    @RequestMapping("/advertisement/toAddPage")
    public String toAddPage(){
        return "advertisement/add";
    }

    /**
     * 接收角色添加请求，进行角色添加
     */
    @RequestMapping(value = "/advertisement",method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessage doAdd(HttpServletRequest request, Advertisement advertisement , HttpSession session) {

        try {
            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)request;

            MultipartFile mfile = mreq.getFile("advpic");
            String name = mfile.getOriginalFilename();
            String extname = name.substring(name.lastIndexOf("."));

            String iconpath = UUID.randomUUID().toString()+extname;

            ServletContext servletContext = session.getServletContext();
            String realpath = servletContext.getRealPath("/pictures");

            String path =realpath+ "\\advertisement\\"+iconpath;

            mfile.transferTo(new File(path));

            User user = (User)session.getAttribute(Const.LOGIN_USER);
            advertisement.setUserid(user.getId());
            advertisement.setStatus("1");
            advertisement.setIconpath(iconpath);

            advertisementService.insertAdvert(advertisement);
            return AjaxMessage.success("添加成功！");
        } catch ( Exception e ) {
            e.printStackTrace();
            return AjaxMessage.fail("添加失败！");
        }
    }
}
