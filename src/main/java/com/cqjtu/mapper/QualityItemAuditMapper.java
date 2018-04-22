package com.cqjtu.mapper;

import com.cqjtu.model.QualityItemAudit;

public interface QualityItemAuditMapper {
    int deleteByPrimaryKey(Integer auditId);

    int insert(QualityItemAudit record);

    int insertSelective(QualityItemAudit record);

    QualityItemAudit selectByPrimaryKey(Integer auditId);

    int updateByPrimaryKeySelective(QualityItemAudit record);

    int updateByPrimaryKey(QualityItemAudit record);
}