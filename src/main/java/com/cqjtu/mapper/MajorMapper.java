package com.cqjtu.mapper;

import com.cqjtu.model.Major;

public interface MajorMapper {
    int deleteByPrimaryKey(Integer majorId);

    int insert(Major record);

    int insertSelective(Major record);

    Major selectByPrimaryKey(Integer majorId);

    int updateByPrimaryKeySelective(Major record);

    int updateByPrimaryKeyWithBLOBs(Major record);

    int updateByPrimaryKey(Major record);
}