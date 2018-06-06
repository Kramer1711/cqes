package com.cqjtu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqjtu.mapper.LogMapper;
import com.cqjtu.model.LogWithBLOBs;
import com.cqjtu.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	LogMapper logMapper;

	@Override
	public void createLog(LogWithBLOBs log) {
		logMapper.insertSelective(log);
		System.out.println(log.toString());
	}

	@Override
	public void updateLog(LogWithBLOBs log) {
		System.out.println(log.toString());
		logMapper.updateByPrimaryKeySelective(log);
	}

	@Override
	public List<String> getTitle() {
		// TODO Auto-generated method stub
		return logMapper.getTitles();
	}

	@Override
	public List<String> getType() {
		return logMapper.getTypes();
	}

	@Override
	public List<Map<String,Object>> searchLog(Map<String, Object> param) {
		return logMapper.search(param);
	}

	@Override
	public Integer geTotal(Map<String, Object> param) {
		return logMapper.getTotal(param);
	}

}
