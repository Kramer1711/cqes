package com.cqjtu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqjtu.mapper.AcademicYearMapper;
import com.cqjtu.service.AcademicYearService;

@Service
public class AcademicYearServiceImpl implements AcademicYearService{

	@Autowired
	AcademicYearMapper academicYearMapper;
	
	@Override
	public String getDoingYear() {
		return academicYearMapper.selectStatusIsDoing();
	}

}
