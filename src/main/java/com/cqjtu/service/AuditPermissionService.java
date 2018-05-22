package com.cqjtu.service;

import java.util.List;
import java.util.Map;

import com.cqjtu.model.Account;

public interface AuditPermissionService {
	/**
	 * 获取账户及其审核权限信息
	 * @param account
	 * @return
	 */
	Map<String, Object> getAccountAndPermission(Account account);
	/**
	 * 查找代理审核人
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> searchAgentAudit(Map<String, Object> param);
	/**
	 * 获取符合条件的记录总数
	 * @param param
	 * @return
	 */
	int getTotal(Map<String, Object> param);
	/**
	 * 更改状态
	 * @param auditPermissionId
	 * @param newStatus
	 * @return
	 */
	boolean changeStatus(Integer auditPermissionId, Integer newStatus);

}
