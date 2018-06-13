package com.cqjtu.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.model.Account;
import com.cqjtu.model.Function;

public interface FunctionService {
	public boolean insertFunction(Function function);
	public List<Function> getFunctions(Function function);
	public boolean updateFunction(Function function);
	public boolean deleteFunctionByPrimaryKey(Integer functionId);
}
