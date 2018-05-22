package com.cqjtu.mapper;

import java.util.List;
import java.util.Map;

import com.cqjtu.model.AuditPermission;

public interface AuditPermissionMapper {
    int deleteByPrimaryKey(Integer auditPermissionId);

    int insert(AuditPermission record);

    int insertSelective(AuditPermission record);

    AuditPermission selectByPrimaryKey(Integer auditPermissionId);

    int updateByPrimaryKeySelective(AuditPermission record);

    int updateByPrimaryKey(AuditPermission record);
    /**
     * 根据auditorId
     * @param accountId
     * @return
     */
    AuditPermission selectByAuditorId(Integer accountId);
    /**
     * 查找代理审核人
     * @param param
     * @return
     */
	List<Map<String, Object>> searchAgentAudit(Map<String, Object> param);
	/**
	 * 获取符合查找条件的记录总数
	 * @param param
	 * @return
	 */
	int getTotal(Map<String, Object> param);
}