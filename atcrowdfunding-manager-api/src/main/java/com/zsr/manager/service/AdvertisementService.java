package com.zsr.manager.service;

import com.zsr.bean.Advertisement;
import com.zsr.utils.VO;

import java.util.List;

/**
 * Demo class
 * 广告模块业务层接口
 * @author shourenzhang
 * @date 2019/8/5 15:02
 */
public interface AdvertisementService {
    /**
     * 得到所有广告信息
     * @return 广告信息集合
     * */
    List<Advertisement> getAdvertisements();
    /**
     * 按条件模糊查询得到所有广告信息
     * @param selectCondition 查询条件
     * @return 广告信息集合
     * */
    List<Advertisement> getAdvertisementsByNameLike(String selectCondition);

    void deleteAdvertisement(int id);

    void deleteAdvertisements(VO vo);

    Advertisement getAdvertisementById(Integer id);

    void updateAdvertisement(Advertisement advertisement);

    void insertAdvert(Advertisement advertisement);
}
