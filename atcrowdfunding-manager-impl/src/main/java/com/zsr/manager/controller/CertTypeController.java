package com.zsr.manager.controller;

import com.zsr.bean.AccountTypeCert;
import com.zsr.bean.Cert;
import com.zsr.manager.service.AccountTypeCertService;
import com.zsr.manager.service.CertService;
import com.zsr.utils.AjaxMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Demo class
 * 资质分类管理模块控制层
 * @author shourenzhang
 * @date 2019/8/11 14:22
 */

@Controller
public class CertTypeController {

    @Autowired
    private CertService certService;

    @Autowired
    private AccountTypeCertService accountTypeCertService;

    @RequestMapping("/certtype/toCertTypePage")
    public String toCertTypePage(Map<String,Object> map){

        try {
            List<Cert> certs = certService.getCerts();
            map.put("certs",certs);
            List<Map<String,Object>> accountTypeCerts = accountTypeCertService.getAccountTypeCerts();
            map.put("accountTypeCerts",accountTypeCerts);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "certtype/certtype";
    }

    @RequestMapping("/certtype/deleteAccountTypeCert")
    @ResponseBody
    public AjaxMessage deleteAccountTypeCert(AccountTypeCert accountTypeCert){
        try {
            int i = accountTypeCertService.deleteAccountTypeCert(accountTypeCert);
            if (i!=1){
                return AjaxMessage.fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail();
        }
        return AjaxMessage.success();
    }

    @RequestMapping("/certtype/addAccountTypeCert")
    @ResponseBody
    public AjaxMessage addAccountTypeCert(AccountTypeCert accountTypeCert){
        try {
            int i = accountTypeCertService.addAccountTypeCert(accountTypeCert);
            if (i!=1){
                return AjaxMessage.fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail();
        }
        return AjaxMessage.success();
    }
}
