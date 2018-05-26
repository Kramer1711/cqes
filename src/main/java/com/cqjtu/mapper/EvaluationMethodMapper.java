package com.cqjtu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cqjtu.model.EvaluationMethod;

public interface EvaluationMethodMapper {
    int deleteByPrimaryKey(Integer evaluationMethodId);

    int insert(EvaluationMethod record);

    int insertSelective(EvaluationMethod record);

    EvaluationMethod selectByPrimaryKey(Integer evaluationMethodId);

    int updateByPrimaryKeySelective(EvaluationMethod record);

    int updateByPrimaryKey(EvaluationMethod record);
    /**
     * 获取某年的测评方法
     * @param academicYear
     * @return
     */
	List<EvaluationMethod> selectByAcademicYear(@Param("academicYear")String academicYear);
}