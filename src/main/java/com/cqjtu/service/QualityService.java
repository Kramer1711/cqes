package com.cqjtu.service;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	/**
	 * 搜索：综合素质分
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> searchQualityScore(Map<String, Object> param);
	/**
	 * 搜索综合素质分结果总数
	 * @param param
	 * @return
	 */
	int getTotalQualityScore(Map<String, Object> param);
	/**
	 * 导出Excel文件二进制数据
	 * 
	 * @param param
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	byte[] exportExcel(Map<String, Object> param) throws FileNotFoundException, IOException;
	/**
	 * 更新综合素质分
	 * @param qualityId 
	 */
	void updateQualityScore(Integer qualityId);
}
