package com.cqjtu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqjtu.mapper.CollegeMapper;
import com.cqjtu.model.College;
import com.cqjtu.service.CollegeService;

@Service
public class CollegeServiceImpl implements CollegeService{

	@Autowired
	CollegeMapper collegeMapper;
	
	@Override
	public List<College> getCollegeList() {
		return collegeMapper.getList();
	}

}
