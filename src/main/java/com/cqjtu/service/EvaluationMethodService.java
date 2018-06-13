package com.cqjtu.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.model.Account;
import com.cqjtu.model.EvaluationMethod;

public interface EvaluationMethodService {
	
	int insertSelective(EvaluationMethod record);
	boolean updatePercentage(EvaluationMethod record);
	List<EvaluationMethod> selectByAcademicYear(String academicYear);
}
