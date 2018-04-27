package com.cqjtu.mapper;

import com.cqjtu.model.Student;

public interface StudentMapper {
    int deleteByPrimaryKey(Long studentId);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Long studentId);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

	Student selectByAccountId(Integer accountId);
}