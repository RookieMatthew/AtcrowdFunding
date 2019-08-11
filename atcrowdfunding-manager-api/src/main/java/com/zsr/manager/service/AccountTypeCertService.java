package com.zsr.manager.service;

import com.zsr.bean.AccountTypeCert;

import java.util.List;
import java.util.Map;

/**
 * Demo class
 *
 * @author shourenzhang
 * @date 2019/8/11 15:48
 */
public interface AccountTypeCertService {

    List<Map<String, Object>> getAccountTypeCerts();

    int deleteAccountTypeCert(AccountTypeCert accountTypeCert);

    int addAccountTypeCert(AccountTypeCert accountTypeCert);
}
