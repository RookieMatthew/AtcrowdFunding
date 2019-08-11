package com.zsr.potal.controller;

import com.zsr.bean.Member;
import com.zsr.bean.Ticket;
import com.zsr.potal.service.MemberService;
import com.zsr.potal.service.TicketService;
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

    @Autowired
    private TicketService ticketService;

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
                return AjaxMessage.fail();
            }
            session.setAttribute(Const.LOGIN_MEMBER,member);
            int j = ticketService.updatePstep(member.getId(),"accttype");
            if (j!=1){
                return AjaxMessage.fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail();
        }
        return AjaxMessage.success();
    }

    @RequestMapping("/member/toApply1Page")
    public String toApply1Page(){
        return "/member/apply-1";
    }

    @RequestMapping("/member/updateBaseInfo")
    @ResponseBody
    public AjaxMessage updateBaseInfo(HttpSession session,String realname,String cardnum,String phone){
        try {
            Member member = (Member) session.getAttribute(Const.LOGIN_MEMBER);
            member.setRealname(realname);
            member.setCardnum(cardnum);
            member.setPhone(phone);
            int i = memberService.updateBaseInfo(member);
            if (i!=1){
                return AjaxMessage.fail();
            }
            session.setAttribute(Const.LOGIN_MEMBER,member);
            int j = ticketService.updatePstep(member.getId(),"baseInfo");
            if (j!=1){
                return AjaxMessage.fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail();
        }
        return AjaxMessage.success();
    }

    @RequestMapping("/member/toApply2Page")
    public String toApply2Page(){
        return "/member/apply-2";
    }

    @RequestMapping("/member/toApply3Page")
    public String toApply3Page(){
        return "/member/apply-3";
    }


    @RequestMapping("/member/choseApply")
    public String choseApply(HttpSession session){
        Member member = (Member) session.getAttribute(Const.LOGIN_MEMBER);
        Ticket ticket = ticketService.getTicketByMemberId(member.getId());
        if (ticket==null){
            ticket=new Ticket();
            ticket.setMemberid(member.getId());
            ticket.setStatus("0");
            ticket.setPstep("apply");
            ticketService.addTicket(ticket);
        }else {
            String pstep = ticket.getPstep();
            if ("accttype".equals(pstep)){
                return "redirect:/member/toApplyPage.htm";
            }
            if ("baseInfo".equals(pstep)){
                return "redirect:/member/toApply1Page.htm";
            }
        }
        return "/member/accttype";
    }
}
