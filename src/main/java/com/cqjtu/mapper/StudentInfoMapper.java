package com.cqjtu.mapper;

import java.util.Map;

import com.cqjtu.model.StudentInfo;

public interface StudentInfoMapper {
    int deleteByPrimaryKey(Integer studentInfoId);

    int insert(StudentInfo record);

    int insertSelective(StudentInfo record);

    StudentInfo selectByPrimaryKey(Integer studentInfoId);

    int updateByPrimaryKeySelective(StudentInfo record);

    int updateByPrimaryKeyWithBLOBs(StudentInfo record);

    int updateByPrimaryKey(StudentInfo record);
    /**
     * 通过学生id查找学生信息
     * @param studentId
     * @return
     */
	StudentInfo selectByStudentId(Long studentId);
	/**
	 * 获取学生信息
	 * @param accountId
	 * @return
	 */
	Map<String, Object> selectPersonalInfoByAccountId(Integer accountId);
}