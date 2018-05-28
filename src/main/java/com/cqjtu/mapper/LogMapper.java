package com.cqjtu.mapper;

import com.cqjtu.model.Log;
import com.cqjtu.model.LogWithBLOBs;

public interface LogMapper {
    int deleteByPrimaryKey(String logId);

    int insert(LogWithBLOBs record);

    int insertSelective(LogWithBLOBs record);

    LogWithBLOBs selectByPrimaryKey(String logId);

    int updateByPrimaryKeySelective(LogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(LogWithBLOBs record);

    int updateByPrimaryKey(Log record);
}