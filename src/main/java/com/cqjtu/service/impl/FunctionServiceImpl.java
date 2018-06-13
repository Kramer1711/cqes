package com.cqjtu.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqjtu.mapper.AccountMapper;
import com.cqjtu.mapper.FunctionMapper;
import com.cqjtu.model.Account;
import com.cqjtu.model.Function;
import com.cqjtu.service.AccountService;
import com.cqjtu.service.FunctionService;
import com.cqjtu.util.Password;

@Service
public class FunctionServiceImpl implements FunctionService {
	@Autowired
	FunctionMapper functionMapper;

	@Override
	public boolean insertFunction(Function function) {
		if(functionMapper.insertSelective(function)>0){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public List<Function> getFunctions(Function function){
		return null;
	}
	
	@Override
	public boolean updateFunction(Function function){
		if(functionMapper.updateByPrimaryKeySelective(function)>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean deleteFunctionByPrimaryKey(Integer functionId){
		if(functionMapper.deleteByPrimaryKey(functionId)>0){
			return true;
		}else{
			return false;
		}
	}
	
}
