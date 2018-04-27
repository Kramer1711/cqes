package com.cqjtu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqjtu.mapper.TeacherMapper;
import com.cqjtu.model.Teacher;
import com.cqjtu.service.TeacherService;
@Service
public class TeacherServiceImpl implements TeacherService{

	@Autowired
	TeacherMapper teacherMapper;
	
	@Override
	public int insertTeacher(Teacher teacher) {
		return teacherMapper.insert(teacher);
	}

}
