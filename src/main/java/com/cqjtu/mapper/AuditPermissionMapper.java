package com.cqjtu.mapper;

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
}