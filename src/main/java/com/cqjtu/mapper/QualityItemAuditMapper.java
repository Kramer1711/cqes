package com.cqjtu.mapper;

import org.apache.ibatis.annotations.Param;

import com.cqjtu.model.QualityItemAudit;

public interface QualityItemAuditMapper {
    int deleteByPrimaryKey(Integer auditId);

    int insert(QualityItemAudit record);

    int insertSelective(QualityItemAudit record);

    QualityItemAudit selectByPrimaryKey(Integer auditId);

    int updateByPrimaryKeySelective(QualityItemAudit record);

    int updateByPrimaryKey(QualityItemAudit record);
    /**
     * 根据qualityItemId删除审核情况
     * @param qualityItemId
     * @return
     */
	int deleteByQualityItemId(Integer qualityItemId);
	/**
	 * 根据qualityItemId修改审核信息
	 * @param itemId
	 * @param itemStatus
	 */
	int updateByQualityItemId(@Param("itemId")Integer itemId, @Param("itemStatus")String itemStatus);
}