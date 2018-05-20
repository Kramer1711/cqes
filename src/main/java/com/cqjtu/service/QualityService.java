package com.cqjtu.service;

import java.util.List;
import java.util.Map;

import com.cqjtu.model.Quality;

public interface QualityService {
	int insertQuality(Quality quality);

	List<Map<String, Object>> getTypeList();
	/**
	 * 上传综合素质测评项目
	 * @param param
	 */
	void uploadQualityItem(Map<String, Object> param);
	/**
	 * 
	 * @param param
	 */
	void updateQualityItemById(Map<String, Object> param);
	/***
	 * 删除素质测评项目及其审核信息
	 * @param deleteId
	 * @return 
	 */
	boolean deleteQualityItem(Integer qualityItemId);
}
