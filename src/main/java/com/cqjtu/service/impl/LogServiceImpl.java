package com.cqjtu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqjtu.mapper.LogMapper;
import com.cqjtu.model.Log;
import com.cqjtu.model.LogWithBLOBs;
import com.cqjtu.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	LogMapper logMapper;
	
	@Override
	public void createLog(LogWithBLOBs log) {
		logMapper.insert(log);
		System.out.println(log.toString());
	}

	@Override
	public void updateLog(LogWithBLOBs log) {
		System.out.println(log.toString());
	}

}
