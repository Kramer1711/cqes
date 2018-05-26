package com.cqjtu.mapper;

import com.cqjtu.model.Score;

public interface ScoreMapper {
    int deleteByPrimaryKey(Integer scoreId);

    int insert(Score record);

    int insertSelective(Score record);

    Score selectByPrimaryKey(Integer scoreId);

    int updateByPrimaryKeySelective(Score record);

    int updateByPrimaryKey(Score record);
    /**
     * 更新成绩
     * @param score
     * @return
     */
	int updateByStudentIdAndAcademicYear(Score score);
	/**
	 * 
	 * @param studentId
	 * @param academicYear
	 * @return
	 */
	Score selectByStudentIdAndAcademicYear(Long studentId, String academicYear);
}