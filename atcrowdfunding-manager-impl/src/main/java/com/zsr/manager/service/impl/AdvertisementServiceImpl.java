package com.zsr.manager.service.impl;

import com.zsr.bean.Advertisement;
import com.zsr.manager.dao.AdvertisementMapper;
import com.zsr.manager.service.AdvertisementService;
import com.zsr.utils.VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Demo class
 * 广告模块业务层接口实现类
 * @author shourenzhang
 * @date 2019/8/5 15:04
 */
@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Override
    public List<Advertisement> getAdvertisements() {
        return advertisementMapper.selectAll();
    }

    @Override
    public List<Advertisement> getAdvertisementsByNameLike(String selectCondition) {
        return advertisementMapper.getAdvertisementsByNameLike(selectCondition);
    }

    @Override
    public void deleteAdvertisement(int id) {
        advertisementMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteAdvertisements(VO vo) {
        advertisementMapper.deleteAdvertisements(vo);
    }

    @Override
    public Advertisement getAdvertisementById(Integer id) {
        return advertisementMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateAdvertisement(Advertisement advertisement) {
        advertisementMapper.updateByPrimaryKey(advertisement);
    }

    @Override
    public void insertAdvert(Advertisement advertisement) {
        advertisementMapper.insert(advertisement);
    }
}
