package com.cqjtu.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqjtu.mapper.AuditPermissionMapper;
import com.cqjtu.model.Account;
import com.cqjtu.model.AuditPermission;
import com.cqjtu.service.AuditPermissionService;

@Service
public class AuditPermissionServiceImpl implements AuditPermissionService {

	@Autowired
	AuditPermissionMapper auditPermissionMapper;

	@Override
	public Map<String, Object> getAccountAndPermission(Account account) {
		AuditPermission auditPermission= auditPermissionMapper.selectByAuditorId(account.getAccountId());
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("roleId", account.getRoleId());
		result.put("auditPermission", auditPermission);
		return result;
	}

}
