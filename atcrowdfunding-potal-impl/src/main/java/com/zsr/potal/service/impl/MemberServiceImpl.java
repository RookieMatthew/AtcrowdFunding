package com.zsr.potal.service.impl;

import com.zsr.bean.Member;
import com.zsr.potal.dao.MemberMapper;
import com.zsr.potal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Demo class
 * 会员业务层实现
 * @author shourenzhang
 * @date 2019/8/10 10:10
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member queryMemberLogin(HashMap<String, Object> paramMap) {
        return memberMapper.queryMemberLogin(paramMap);
    }

    @Override
    public int updateAcctType(Member member) {
        return memberMapper.updateAcctType(member);
    }

    @Override
    public int updateBaseInfo(Member member) {
        return memberMapper.updateBaseInfo(member);
    }

    @Override
    public int updateEmail(Member member) {
        return memberMapper.updateEmail(member);
    }

    @Override
    public int updateAuthstatus(Member member) {
        return memberMapper.updateAuthstatus(member);
    }
}

