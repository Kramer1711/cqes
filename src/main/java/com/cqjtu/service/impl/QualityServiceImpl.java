package com.cqjtu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqjtu.mapper.QualityMapper;
import com.cqjtu.model.Quality;
import com.cqjtu.service.QualityService;

@Service
public class QualityServiceImpl implements QualityService {
	@Autowired
	QualityMapper qualityMapper;

	@Override
	public int insertQuality(Quality quality) {
		return qualityMapper.insert(quality);
	}

}
