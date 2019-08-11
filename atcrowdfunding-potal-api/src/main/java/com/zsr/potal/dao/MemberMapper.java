package com.zsr.potal.dao;

import com.zsr.bean.Member;

import java.util.HashMap;
import java.util.List;

public interface MemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    Member selectByPrimaryKey(Integer id);

    List<Member> selectAll();

    int updateByPrimaryKey(Member record);

    Member queryMemberLogin(HashMap<String, Object> paramMap);

    int updateAcctType(Member member);

    int updateBaseInfo(Member member);
}