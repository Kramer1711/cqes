package com.cqjtu.service;

import java.util.List;

import com.cqjtu.model.AcademicYear;

public interface AcademicYearService {
	/**
	 * 获取正在进行综合素质测评的学年
	 * 
	 * @return
	 */
	String getDoingYear();

	public List<AcademicYear> getAcademicYears(AcademicYear academicYear);

	public boolean updateByPrimaryKeySelective(AcademicYear academicYear);

	Integer countSelective(AcademicYear academicYear);

	public boolean insertAcademicYear(AcademicYear academicYear);

	public boolean seleteByPrimaryKey(AcademicYear academicYear);

	boolean having(AcademicYear academicYear);
}
