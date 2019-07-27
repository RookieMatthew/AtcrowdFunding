package com.zsr.manager.dao;

import com.zsr.bean.MemberCert;
import java.util.List;

public interface MemberCertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberCert record);

    MemberCert selectByPrimaryKey(Integer id);

    List<MemberCert> selectAll();

    int updateByPrimaryKey(MemberCert record);
}