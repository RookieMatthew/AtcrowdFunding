package com.zsr.manager.dao;

import com.zsr.bean.Cert;
import com.zsr.bean.MemberCert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cert record);

    Cert selectByPrimaryKey(Integer id);

    List<Cert> selectAll();

    int updateByPrimaryKey(Cert record);

    List<Cert> getCertsByNameLike(String selectCondition);

    void deleteBatchCert(String[] ids);

    List<Cert> getCertsByAccttype(String accttype);

    int saveMemberCertImg(@Param("certimgs") List<MemberCert> certimgs);
}