package com.cqjtu.mapper;

import java.util.List;

import com.cqjtu.model.AcademicYear;

public interface AcademicYearMapper {
    int insert(AcademicYear record);

    int insertSelective(AcademicYear record);
    /**
     * 获取status是doing的学年
     * @return
     */
	String selectStatusIsDoing();
	
    List<AcademicYear> selectSelective(AcademicYear academicYear);
    
    int updateByPrimaryKeySelective(AcademicYear academicYear);
    
    Integer countSelective(AcademicYear academicYear);
}