package com.cqjtu.mapper;

import java.util.Map;

import com.cqjtu.model.AdminInfo;

public interface AdminInfoMapper {
    int deleteByPrimaryKey(Integer adminInfoId);

    int insert(AdminInfo record);

    int insertSelective(AdminInfo record);

    AdminInfo selectByPrimaryKey(Integer adminInfoId);

    int updateByPrimaryKeySelective(AdminInfo record);

    int updateByPrimaryKeyWithBLOBs(AdminInfo record);

    int updateByPrimaryKey(AdminInfo record);
    /**
     * 获取个人信息
     * @param accountId
     * @return
     */
	Map<String, Object> selectPersonalInfoByAccountId(Integer accountId);
}