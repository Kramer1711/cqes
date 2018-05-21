package com.cqjtu.service;

import java.util.Map;

import com.cqjtu.model.Account;

public interface AuditPermissionService {

	Map<String, Object> getAccountAndPermission(Account account);

}
