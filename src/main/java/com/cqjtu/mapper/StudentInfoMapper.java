package com.cqjtu.mapper;

import com.cqjtu.model.StudentInfo;

public interface StudentInfoMapper {
    int deleteByPrimaryKey(Integer studentInfoId);

    int insert(StudentInfo record);

    int insertSelective(StudentInfo record);

    StudentInfo selectByPrimaryKey(Integer studentInfoId);

    int updateByPrimaryKeySelective(StudentInfo record);

    int updateByPrimaryKeyWithBLOBs(StudentInfo record);

    int updateByPrimaryKey(StudentInfo record);
}