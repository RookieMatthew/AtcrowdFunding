package com.zsr.potal.service;

import com.zsr.bean.Member;

import java.util.HashMap;

/**
 * Demo class
 * 会员业务层接口
 * @author shourenzhang
 * @date 2019/8/10 10:08
 */
public interface MemberService {

    Member queryMemberLogin(HashMap<String, Object> paramMap);

    int updateAcctType(Member member);
}
