package com.cqjtu.service;

import com.cqjtu.model.Score;
import com.cqjtu.model.Student;

public interface ScoreService {
	int insertScore(Score score);
	/**
	 * 
	 * @param score
	 * @return
	 */
	int uploadScore( Score score);
}
