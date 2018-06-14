package com.cqjtu.mapper;

import java.util.List;
import java.util.Map;

import com.cqjtu.model.QualityItem;

public interface QualityItemMapper {
    int deleteByPrimaryKey(Integer qualityTiemId);

    int insert(QualityItem record);

    int insertSelective(QualityItem record);

    QualityItem selectByPrimaryKey(Integer qualityTiemId);

    int updateByPrimaryKeySelective(QualityItem record);

    int updateByPrimaryKey(QualityItem record);

	List<QualityItem> getListByQualityId(Integer qualityId);
	/**
	 * 	计算操行总分
	 * @param qualityId
	 * @return
	 */
	Integer calQualityScore(Integer qualityId);
	/**
	 * 计算每种类型的操行总分
	 * @param qualityId
	 * @return
	 */
	List<Map<String, Object>> calTypeQualityScore(Integer qualityId);
	/**
	 * 没有审核通过的数量
	 * @param qualityId
	 * @return
	 */
	Integer getNumberOfNotPassedItem(Integer qualityId);
}