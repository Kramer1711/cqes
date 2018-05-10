package com.cqjtu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqjtu.mapper.MajorMapper;
import com.cqjtu.model.Major;
import com.cqjtu.service.MajorService;

@Service
public class MajorServiceImpl implements MajorService{

	@Autowired
	MajorMapper majorMapper;

	@Override
	public List<Major> getMajorListByCollegeId(int collegeId) {
		return majorMapper.getMajorListByCollegeId(collegeId);
	}
}
