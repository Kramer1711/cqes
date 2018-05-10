package com.cqjtu.mapper;

import com.cqjtu.model.PuditPermission;

public interface PuditPermissionMapper {
    int deleteByPrimaryKey(Integer auditPermissionId);

    int insert(PuditPermission record);

    int insertSelective(PuditPermission record);

    PuditPermission selectByPrimaryKey(Integer auditPermissionId);

    int updateByPrimaryKeySelective(PuditPermission record);

    int updateByPrimaryKey(PuditPermission record);
}