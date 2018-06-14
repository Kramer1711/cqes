package com.cqjtu.service;

import java.util.List;

import com.cqjtu.model.Major;

public interface MajorService {
	
	List<Major> getMajorListByCollegeId(int collegeId);
	
	boolean deleteByMajorId(Integer majorId);

	boolean insertMajor(Major major);

	boolean updateMajor(Major major);
	
}
