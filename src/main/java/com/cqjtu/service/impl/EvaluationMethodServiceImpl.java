package com.cqjtu.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqjtu.mapper.AcademicYearMapper;
import com.cqjtu.mapper.EvaluationMethodMapper;
import com.cqjtu.model.AcademicYear;
import com.cqjtu.model.EvaluationMethod;
import com.cqjtu.service.AcademicYearService;
import com.cqjtu.service.EvaluationMethodService;

@Service
public class EvaluationMethodServiceImpl implements EvaluationMethodService {
	@Autowired
	EvaluationMethodMapper evaluationMethodMapper;

	@Override
	public int insertSelective(EvaluationMethod record) {
		return evaluationMethodMapper.insertSelective(record);
	}

	@Override
	public boolean updatePercentage(EvaluationMethod record) {
		if(evaluationMethodMapper.updatePercentage(record)>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<EvaluationMethod> selectByAcademicYear(String academicYear) {
		return evaluationMethodMapper.selectByAcademicYear(academicYear);
	}

}
