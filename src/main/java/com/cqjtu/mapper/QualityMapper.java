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

}