package com.zsr.manager.dao;

import com.zsr.bean.AccountTypeCert;
import java.util.List;
import java.util.Map;

public interface AccountTypeCertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountTypeCert record);

    AccountTypeCert selectByPrimaryKey(Integer id);

    List<AccountTypeCert> selectAll();

    int updateByPrimaryKey(AccountTypeCert record);

    List<Map<String, Object>> getAccountTypeCerts();

    int deleteAccountTypeCert(AccountTypeCert accountTypeCert);

    int addAccountTypeCert(AccountTypeCert accountTypeCert);
}