package com.cqjtu.mapper;

import com.cqjtu.model.SystemLog;

public interface SystemLogMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(SystemLog record);

    int insertSelective(SystemLog record);

    SystemLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(SystemLog record);

    int updateByPrimaryKeyWithBLOBs(SystemLog record);

    int updateByPrimaryKey(SystemLog record);
}