package com.cqjtu.service;

import java.util.List;

import com.cqjtu.model.College;

public interface CollegeService {
	
	List<College> getCollegeList(); 
	
	boolean deleteByCollegeId(Integer collegeId);

	boolean insertCollege(College college);

	boolean updateCollege(College college);
}
