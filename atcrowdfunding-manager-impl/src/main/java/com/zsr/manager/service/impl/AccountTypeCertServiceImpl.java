package com.zsr.manager.service.impl;

import com.zsr.bean.AccountTypeCert;
import com.zsr.manager.dao.AccountTypeCertMapper;
import com.zsr.manager.service.AccountTypeCertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Demo class
 *
 * @author shourenzhang
 * @date 2019/8/11 15:50
 */
@Service
public class AccountTypeCertServiceImpl implements AccountTypeCertService {

    @Autowired
    private AccountTypeCertMapper accountTypeCertMapper;

    @Override
    public List<Map<String, Object>> getAccountTypeCerts() {
        return accountTypeCertMapper.getAccountTypeCerts();
    }

    @Override
    public int deleteAccountTypeCert(AccountTypeCert accountTypeCert) {
        return accountTypeCertMapper.deleteAccountTypeCert(accountTypeCert);
    }

    @Override
    public int addAccountTypeCert(AccountTypeCert accountTypeCert) {
        return accountTypeCertMapper.addAccountTypeCert(accountTypeCert);
    }
}
