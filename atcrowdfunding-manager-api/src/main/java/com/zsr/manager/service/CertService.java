package com.zsr.manager.service;

import com.zsr.bean.Cert;

import java.util.List;

/**
 * Demo class
 * 资质维护模块业务层接口
 * @author shourenzhang
 * @date 2019/8/10 14:10
 */
public interface CertService {

    List<Cert> getCerts();

    List<Cert> getCertsByNameLike(String selectCondition);

    void deleteCert(int parseInt);

    void deleteBatchCert(String[] ids);

    void addCert(Cert cert);

    Cert getCertById(Integer id);

    void updateCert(Cert cert);
}
