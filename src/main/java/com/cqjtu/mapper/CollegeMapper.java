package com.cqjtu.mapper;

import java.util.List;

import com.cqjtu.model.College;

public interface CollegeMapper {
    int deleteByPrimaryKey(Integer collegeId);

    int insert(College record);

    int insertSelective(College record);

    College selectByPrimaryKey(Integer collegeId);

    int updateByPrimaryKeySelective(College record);

    int updateByPrimaryKeyWithBLOBs(College record);

    int updateByPrimaryKey(College record);

    /**
     * 获取全部college
     * @return
     */
	List<College> getList();
}