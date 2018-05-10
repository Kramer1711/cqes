package com.cqjtu.mapper;

import com.cqjtu.model.EvaluationMethod;

public interface EvaluationMethodMapper {
    int deleteByPrimaryKey(Integer evaluationMethodId);

    int insert(EvaluationMethod record);

    int insertSelective(EvaluationMethod record);

    EvaluationMethod selectByPrimaryKey(Integer evaluationMethodId);

    int updateByPrimaryKeySelective(EvaluationMethod record);

    int updateByPrimaryKey(EvaluationMethod record);
}