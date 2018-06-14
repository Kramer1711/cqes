package com.cqjtu.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.mapper.AdminInfoMapper;
import com.cqjtu.mapper.AdminMapper;
import com.cqjtu.mapper.StudentInfoMapper;
import com.cqjtu.mapper.StudentMapper;
import com.cqjtu.mapper.TeacherInfoMapper;
import com.cqjtu.mapper.TeacherMapper;
import com.cqjtu.model.Account;
import com.cqjtu.model.Admin;
import com.cqjtu.service.CommonService;
import com.cqjtu.util.ParamUtil;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	StudentMapper studentMapper;
	@Autowired
	StudentInfoMapper studentInfoMapper;
	@Autowired
	TeacherMapper teacherMapper;
	@Autowired
	TeacherInfoMapper teacherInfoMapper;
	@Autowired
	AdminMapper adminMapper;
	@Autowired
	AdminInfoMapper adminInfoMapper;
	
	@Override
	public JSONObject getPersonalInfo(Account account) {
		Map<String, Object> info = ParamUtil.getParamMap();
		if (account.getRoleId() == 1) {
			info = adminInfoMapper.selectPersonalInfoByAccountId(account.getAccountId());
		}else if(account.getRoleId() == 2){
			info = teacherInfoMapper.selectPersonalInfoByAccountId(account.getAccountId());
		}else if(account.getRoleId() == 3 || account.getRoleId() == 4) {
			info = studentInfoMapper.selectPersonalInfoByAccountId(account.getAccountId());
		}
		return (JSONObject) JSONObject.toJSON(info);
	}

}
