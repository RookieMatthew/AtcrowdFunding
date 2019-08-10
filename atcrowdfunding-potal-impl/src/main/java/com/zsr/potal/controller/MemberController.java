package com.zsr.potal.controller;

import com.zsr.potal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
