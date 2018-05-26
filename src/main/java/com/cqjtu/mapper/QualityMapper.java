package com.cqjtu.mapper;

import java.util.List;
import java.util.Map;

import com.cqjtu.model.Quality;

public interface QualityMapper {
    int deleteByPrimaryKey(Integer qualityId);

    int insert(Quality record);

    int insertSelective(Quality record);

    Quality selectByPrimaryKey(Integer qualityId);

    int updateByPrimaryKeySelective(Quality record);

    int updateByPrimaryKey(Quality record);

    /**
     * 获取综合素质
     * @param param
     * @return
     */
	Quality selectByStudentId(Map<String, Object> param);
	/**
	 * 获取审核情况
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> selectAuditSituationList(Map<String, Object> param);
	/**
	 * 搜索:符合条件的学生综合素质测评情况 的分页结果
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> searchQualityAndStudentBaseInfo(Map<String, Object> param);
	/**
	 * searchQualityAndStudentBaseInfo()方法中符合条件的总数
	 * @param param
	 * @return
	 */
	int getQualityAndStudentTotal(Map<String, Object> param);
	/**
	 * 搜索分页：综合素质分
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> searchQualityScore(Map<String, Object> param);
	/**
	 * 综合素质分结果总数
	 * @param param
	 * @return
	 */
	int getTotalQualityScore(Map<String, Object> param);
	/**
	 * 搜索不分页：综合素质分
	 * 
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> searchAllQualityScore(Map<String, Object> param);

}