package com.cqjtu.mapper;

import com.cqjtu.model.Quality;

public interface QualityMapper {
    int deleteByPrimaryKey(Integer qualityId);

    int insert(Quality record);

    int insertSelective(Quality record);

    Quality selectByPrimaryKey(Integer qualityId);

    int updateByPrimaryKeySelective(Quality record);

    int updateByPrimaryKey(Quality record);
}