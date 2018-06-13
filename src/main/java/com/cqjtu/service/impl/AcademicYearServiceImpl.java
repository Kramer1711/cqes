package com.cqjtu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqjtu.mapper.AcademicYearMapper;
import com.cqjtu.model.AcademicYear;
import com.cqjtu.service.AcademicYearService;

@Service
public class AcademicYearServiceImpl implements AcademicYearService {

	@Autowired
	AcademicYearMapper academicYearMapper;

	@Override
	public String getDoingYear() {
		return academicYearMapper.selectStatusIsDoing();
	}

	@Override
	public List<AcademicYear> getAcademicYears(AcademicYear academicYear) {
		return academicYearMapper.selectSelective(academicYear);
	}

	@Override
	public boolean updateByPrimaryKeySelective(AcademicYear academicYear) {
		if (academicYearMapper.updateByPrimaryKeySelective(academicYear) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Integer countSelective(AcademicYear academicYear) {
		return academicYearMapper.countSelective(academicYear);
	}

	@Override
	public boolean insertAcademicYear(AcademicYear academicYear) {
		if (academicYearMapper.insertSelective(academicYear) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean seleteByPrimaryKey(AcademicYear academicYear) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean having(AcademicYear academicYear) {
		if (academicYearMapper.countSelective(academicYear) > 0) {
			return true;
		} else {
			return false;
		}
	}

}
