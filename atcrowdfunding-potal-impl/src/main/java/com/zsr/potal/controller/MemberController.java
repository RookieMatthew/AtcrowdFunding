package com.zsr.potal.controller;

import com.zsr.bean.Member;
import com.zsr.potal.service.MemberService;
import com.zsr.utils.AjaxMessage;
import com.zsr.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Demo class
 * 会员模块控制层
 * @author shourenzhang
 * @date 2019/8/10 13:52
 */
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("member/toMyctrowdfundingPage")
    public String toMyctrowdfundingPage(){
        return "member/myctrowdfunding";
    }


    @RequestMapping("/member/toAccttypePage")
    public String toAccttypePage(){
        return "/member/accttype";
    }

    @RequestMapping("/member/toApplyPage")
    public String toApplyPage(){
        return "/member/apply";
    }

    @RequestMapping("/member/updateAcctType")
    @ResponseBody
    public AjaxMessage updateAcctType(HttpSession session,String accttype){
        try {
            Member member = (Member) session.getAttribute(Const.LOGIN_MEMBER);
            member.setAccttype(accttype);
            int i = memberService.updateAcctType(member);
            if (i!=1){
                session.setAttribute(Const.LOGIN_MEMBER,member);
                return AjaxMessage.fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail();
        }
        return AjaxMessage.success();
    }
}
