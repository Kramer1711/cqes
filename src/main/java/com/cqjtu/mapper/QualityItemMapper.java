package com.cqjtu.mapper;

import com.cqjtu.model.QualityItem;

public interface QualityItemMapper {
    int deleteByPrimaryKey(Integer qualityTiemId);

    int insert(QualityItem record);

    int insertSelective(QualityItem record);

    QualityItem selectByPrimaryKey(Integer qualityTiemId);

    int updateByPrimaryKeySelective(QualityItem record);

    int updateByPrimaryKey(QualityItem record);
}