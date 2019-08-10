package com.zsr.manager.service.impl;

import com.zsr.bean.Cert;
import com.zsr.manager.dao.CertMapper;
import com.zsr.manager.service.CertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Demo class
 * 资质维护模块业务层实现类
 * @author shourenzhang
 * @date 2019/8/10 14:10
 */
@Service
public class CertServiceImpl implements CertService {

    @Autowired
    private CertMapper certMapper;

    @Override
    public List<Cert> getCerts() {
        return certMapper.selectAll();
    }

    @Override
    public List<Cert> getCertsByNameLike(String selectCondition) {
        return certMapper.getCertsByNameLike(selectCondition);
    }

    @Override
    public void deleteCert(int parseInt) {
        certMapper.deleteByPrimaryKey(parseInt);
    }

    @Override
    public void deleteBatchCert(String[] ids) {
        for (String id : ids) {
            certMapper.deleteByPrimaryKey(Integer.parseInt(id));
        }
    }

    @Override
    public void addCert(Cert cert) {
        certMapper.insert(cert);
    }

    @Override
    public Cert getCertById(Integer id) {
        return certMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateCert(Cert cert) {
        certMapper.updateByPrimaryKey(cert);
    }
}
