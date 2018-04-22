package com.cqjtu.mapper;

import com.cqjtu.model.AdminInfo;

public interface AdminInfoMapper {
    int deleteByPrimaryKey(Integer adminInfoId);

    int insert(AdminInfo record);

    int insertSelective(AdminInfo record);

    AdminInfo selectByPrimaryKey(Integer adminInfoId);

    int updateByPrimaryKeySelective(AdminInfo record);

    int updateByPrimaryKeyWithBLOBs(AdminInfo record);

    int updateByPrimaryKey(AdminInfo record);
}