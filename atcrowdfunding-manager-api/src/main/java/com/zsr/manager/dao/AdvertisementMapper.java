package com.zsr.manager.dao;

import com.zsr.bean.Advertisement;
import com.zsr.utils.VO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    List<Advertisement> selectAll();

    int updateByPrimaryKey(Advertisement record);

    List<Advertisement> getAdvertisementsByNameLike(String selectCondition);

    void deleteAdvertisements(@Param("vo") VO vo);
}