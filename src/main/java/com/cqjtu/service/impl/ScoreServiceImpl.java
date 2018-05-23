package com.cqjtu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqjtu.mapper.ScoreMapper;
import com.cqjtu.mapper.StudentMapper;
import com.cqjtu.model.Score;
import com.cqjtu.model.Student;
import com.cqjtu.service.ScoreService;

@Service
public class ScoreServiceImpl implements ScoreService {
	@Autowired
	ScoreMapper scoreMapper;
	@Autowired
	StudentMapper studentMapper;

	@Override
	public int insertScore(Score score) {
		return scoreMapper.insert(score);
	}

	@Override
	public int updateScore(Score score) {
		return scoreMapper.updateByStudentIdAndAcademicYear(score);
	}

}
