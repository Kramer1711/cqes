package com.cqjtu.mapper;

import java.util.List;

import com.cqjtu.model.Major;

public interface MajorMapper {
    int deleteByPrimaryKey(Integer majorId);

    int insert(Major record);

    int insertSelective(Major record);

    Major selectByPrimaryKey(Integer majorId);

    int updateByPrimaryKeySelective(Major record);

    int updateByPrimaryKeyWithBLOBs(Major record);

    int updateByPrimaryKey(Major record);
    /**
     * 通过学院id获取属于该学院的专业
     * @param collegeId
     * @return
     */
	List<Major> getMajorListByCollegeId(int collegeId);
}