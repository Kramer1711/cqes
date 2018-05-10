package com.cqjtu.mapper;

import java.util.List;
import java.util.Map;

import com.cqjtu.model.Student;

public interface StudentMapper {
    int deleteByPrimaryKey(Long studentId);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Long studentId);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
    /**
     * 通过账号id查找学生实体
     * @param accountId
     * @return
     */
	Student selectByAccountId(Integer accountId);
	/**
	 * 查找分页
	 * @param param key关键字,start开始位置,rows页大小
	 * @return
	 */
	List<Map<String, Object>> search(Map<String, Object> param);
}