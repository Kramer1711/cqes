package com.cqjtu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqjtu.mapper.StudentMapper;
import com.cqjtu.model.Student;
import com.cqjtu.service.StudentService;
@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	StudentMapper studentMapper;
	
	@Override
	public int insertStudent(Student student) {
		return studentMapper.insert(student);
	}
}
