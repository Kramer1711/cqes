package com.cqjtu.mapper;

import com.cqjtu.model.AcademicYear;

public interface AcademicYearMapper {
    int insert(AcademicYear record);

    int insertSelective(AcademicYear record);
}