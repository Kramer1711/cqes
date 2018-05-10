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

	Quality selectByStudentId(Map<String, Object> student);

	List<Map<String, Object>> selectAuditSituationList(Map<String, Object> param);
}