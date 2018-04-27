package com.cqjtu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqjtu.mapper.StudentInfoMapper;
import com.cqjtu.model.StudentInfo;
import com.cqjtu.service.StudentInfoService;

@Service
public class StudenInfotServiceImpl implements StudentInfoService {
	@Autowired
	StudentInfoMapper studentInfoMapper;
	
	@Override
	public int insertStudentInfo(StudentInfo studentInfo) {
		return studentInfoMapper.insert(studentInfo);
	}

}