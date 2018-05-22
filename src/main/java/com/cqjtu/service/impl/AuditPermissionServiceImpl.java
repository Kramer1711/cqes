package com.cqjtu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqjtu.mapper.AccountMapper;
import com.cqjtu.mapper.AuditPermissionMapper;
import com.cqjtu.model.Account;
import com.cqjtu.model.AuditPermission;
import com.cqjtu.service.AuditPermissionService;
import com.cqjtu.service.StudentService;

@Service
public class AuditPermissionServiceImpl implements AuditPermissionService {

	@Autowired
	AuditPermissionMapper auditPermissionMapper;
	@Autowired
	AccountMapper accountMapper;

	@Override
	public Map<String, Object> getAccountAndPermission(Account account) {
		AuditPermission auditPermission = auditPermissionMapper.selectByAuditorId(account.getAccountId());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("roleId", account.getRoleId());
		result.put("auditPermission", auditPermission);
		return result;
	}

	@Override
	public List<Map<String, Object>> searchAgentAudit(Map<String, Object> param) {
		return auditPermissionMapper.searchAgentAudit(param);
	}

	@Override
	public int getTotal(Map<String, Object> param) {
		return auditPermissionMapper.getTotal(param);
	}

	@Transactional
	@Override
	public boolean changeStatus(Integer auditPermissionId, Integer newStatus) {
		AuditPermission auditPermission = auditPermissionMapper.selectByPrimaryKey(auditPermissionId);
		auditPermission.setStatus(newStatus);
		int result = auditPermissionMapper.updateByPrimaryKey(auditPermission);
		if (result == 1) {
			// newStatus=1时,5-1=4表现为正常:代理审核人
			// newStatus=2时,5-2=3表现为停用:学生
			int result1 = conversionRole(auditPermission.getAuditorId(), 5 - newStatus);
			if (result1 == 1)
				return true;
		}
		return false;
	}

	/**
	 * 学生id:3 ;代理审核人id:4; 转换角色: 学生 <---> 代理审核人
	 * 
	 * @param auditorId
	 * @return
	 */
	private int conversionRole(Integer auditorId, Integer roleId) {
		Account account = accountMapper.selectByPrimaryKey(auditorId);
		account.setRoleId(roleId);
		return accountMapper.updateByPrimaryKeySelective(account);
	}
	@Transactional
	@Override
	public boolean addAuditPermission(Map<String, Object> param) {
		AuditPermission auditPermission = new AuditPermission();
		auditPermission.setAuditorId((Integer) param.get("auditorId"));
		auditPermission.setCollegeId((Integer) param.get("collegeId"));
		auditPermission.setMajorId((Integer) param.get("majorId"));
		auditPermission.setStatus((Integer) param.get("status"));
		auditPermissionMapper.insertSelective(auditPermission);
		conversionRole(auditPermission.getAuditorId(), 5 - auditPermission.getStatus());
		return true;

	}
}
