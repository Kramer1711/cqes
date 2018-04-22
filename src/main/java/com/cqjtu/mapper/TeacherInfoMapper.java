package com.cqjtu.mapper;

import com.cqjtu.model.TeacherInfo;

public interface TeacherInfoMapper {
    int deleteByPrimaryKey(Integer teacherInfoId);

    int insert(TeacherInfo record);

    int insertSelective(TeacherInfo record);

    TeacherInfo selectByPrimaryKey(Integer teacherInfoId);

    int updateByPrimaryKeySelective(TeacherInfo record);

    int updateByPrimaryKeyWithBLOBs(TeacherInfo record);

    int updateByPrimaryKey(TeacherInfo record);
}