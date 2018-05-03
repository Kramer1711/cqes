package com.cqjtu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
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

	@Override
	public JSONArray searchStudent(String key, int start, int rows) {
		System.out.println("searchStudent:\t"+key+" "+start+" "+rows);
		JSONArray info = new JSONArray();
		Map<String,Object> param = new HashMap<>();
		param.put("key", key);
		param.put("start", start);
		param.put("rows", rows);
		List<Map<String,String>> result = studentMapper.search(param);
		info =(JSONArray) JSONArray.toJSON(result);
		System.out.println(info.toJSONString());
		return info;
	}
}
