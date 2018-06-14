package com.cqjtu.service;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.model.Account;

public interface CommonService {
	JSONObject getPersonalInfo(Account account);
}
