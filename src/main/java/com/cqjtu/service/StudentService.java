package com.cqjtu.service;

import com.alibaba.fastjson.JSONArray;
import com.cqjtu.model.Student;

public interface StudentService {
	int insertStudent(Student student);
	/**
	 * 搜索学生信息
	 * @param key
	 * @param page
	 * @param rows
	 */
	JSONArray searchStudent(String key, int start, int rows);
}
