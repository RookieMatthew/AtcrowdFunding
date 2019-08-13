package com.zsr.potal.controller;

import com.zsr.bean.Cert;
import com.zsr.bean.Member;
import com.zsr.bean.MemberCert;
import com.zsr.bean.Ticket;
import com.zsr.manager.service.CertService;
import com.zsr.potal.listener.PassListener;
import com.zsr.potal.listener.RefuseListener;
import com.zsr.potal.service.MemberService;
import com.zsr.potal.service.TicketService;
import com.zsr.utils.AjaxMessage;
import com.zsr.utils.Const;
import com.zsr.utils.VO;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

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

    @Autowired
    private CertService certService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;


    @RequestMapping("member/toMyctrowdfundingPage")
    public String toMyctrowdfundingPage(){
        return "member/myctrowdfunding";
    }

    @RequestMapping("/member/toAccttypePage")
    public String toAccttypePage(){
        return "member/accttype";
    }

    @RequestMapping("/member/toApplyPage")
    public String toApplyPage(){
        return "member/apply";
    }

    @RequestMapping("member/updateAcctType")
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
            Ticket ticket = ticketService.getTicketByMemberId(member.getId());
            ticket.setPstep("accttype");
            int j = ticketService.updatePstep(ticket);
            if (j!=1){
                return AjaxMessage.fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail();
        }
        return AjaxMessage.success();
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
            Ticket ticket = ticketService.getTicketByMemberId(member.getId());
            ticket.setPstep("baseInfo");
            int j = ticketService.updatePstep(ticket);
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
    public String toApply1Page(HttpSession session, HttpServletRequest request){
        Member member = (Member) session.getAttribute(Const.LOGIN_MEMBER);
        List<Cert> needCerts = certService.getCertsByAccttype(member.getAccttype());
        request.setAttribute("needCerts",needCerts);
        return "member/apply-1";
    }


    @RequestMapping("/member/uploadCertImg")
    @ResponseBody
    public AjaxMessage uploadCertImg(HttpSession session, VO vo){
        try {
            Member member = (Member) session.getAttribute(Const.LOGIN_MEMBER);
            List<MemberCert> certimgs = vo.getCertimgs();
            String realPath = session.getServletContext().getRealPath("/pictures");
            for (MemberCert memberCert : certimgs) {
                MultipartFile fileimg = memberCert.getFileimg();
                String extName = fileimg.getOriginalFilename().substring(fileimg.getOriginalFilename().indexOf("."));
                String fileName = realPath+"/certimg"+"/"+ UUID.randomUUID().toString()+extName;
                fileimg.transferTo(new File(fileName));
                memberCert.setMemberid(member.getId());
                memberCert.setIconpath(fileName);
            }
            certService.saveMemberCertImg(certimgs);
            Ticket ticket = ticketService.getTicketByMemberId(member.getId());
            ticket.setPstep("uploadCertImg");
            int j = ticketService.updatePstep(ticket);
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
        return "member/apply-2";
    }


    @RequestMapping("/member/checkEmail")
    @ResponseBody
    public AjaxMessage uploadCertImg(HttpSession session, String email){
        try {
            //判断邮箱是否改变，若改变更新数据和memebersession
            Member member = (Member) session.getAttribute(Const.LOGIN_MEMBER);
            if (!member.getEmail().equals(email)){
                member.setEmail(email);
                if (memberService.updateEmail(member)==1){
                    session.setAttribute(Const.LOGIN_MEMBER,member);
                }
            }

            //得到实名认证的流程定义
            ProcessDefinition auth = repositoryService.createProcessDefinitionQuery().processDefinitionKey("auth").singleResult();

            //设置实名认证的流程所需参数
            Map<String, Object> variables = new HashMap<>(5);
            variables.put("toEmail",email);
            variables.put("loginacct",member.getLoginacct());
            StringBuilder authcode = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                authcode.append(new Random().nextInt(10));
            }
            variables.put("authcode",authcode);
            variables.put("passListener",new PassListener());
            variables.put("refuseListener",new RefuseListener());
            //启动流程实例，来到发送邮件任务发送邮件
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(auth.getId(),variables);

            //更新步骤，添加流程实例id和验证码
            Ticket ticket = ticketService.getTicketByMemberId(member.getId());
            ticket.setPstep("checkEmail");
            ticket.setPiid(processInstance.getId());
            ticket.setAuthcode(String.valueOf(authcode));
            int j = ticketService.updatePstepAndPiidAndAuthcode(ticket);
            if (j!=1){
                return AjaxMessage.fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail();
        }
        return AjaxMessage.success();
    }
    @RequestMapping("/member/toApply3Page")
    public String toApply3Page(){
        return "member/apply-3";
    }


    @RequestMapping("/member/finishApply")
    @ResponseBody
    public AjaxMessage finishApply(HttpSession session, String authcode){
        try {
            Member member = (Member) session.getAttribute(Const.LOGIN_MEMBER);
            Ticket ticket = ticketService.getTicketByMemberId(member.getId());
            //得到发送邮件的任务,若验证码正确则完成
            if (ticket.getAuthcode().equals(authcode)){
                //完成任务
                Task task = taskService.createTaskQuery().processInstanceId(ticket.getPiid()).taskAssignee(member.getLoginacct()).singleResult();
                taskService.complete(task.getId());
                //更新会员的状态为1（审核中）
                member.setAuthstatus("1");
                memberService.updateAuthstatus(member);
            }else{
                return AjaxMessage.fail("验证码不正确！");
            }
            //更新步骤
            ticket.setPstep("finishApply");
            int j = ticketService.updatePstep(ticket);
            if (j!=1){
                return AjaxMessage.fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxMessage.fail();
        }
        return AjaxMessage.success();
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
            if ("uploadCertImg".equals(pstep)){
                return "redirect:/member/toApply2Page.htm";
            }
            if ("checkEmail".equals(pstep)){
                return "redirect:/member/toApply3Page.htm";
            }
        }
        return "member/accttype";
    }


}
