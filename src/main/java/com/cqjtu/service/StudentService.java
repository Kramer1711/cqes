package com.cqjtu.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.model.Student;

public interface StudentService {
	int insertStudent(Student student);

	/**
	 * 查找学生的分页结果
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> searchStudent(Map<String,Object> param);
	/**
	 * 查找学生结果总数
	 * @param param
	 * @return
	 */
	int getTotal(Map<String, Object> param);
	/**
	 * 搜索：符合条件的学生综合素质测评[总]审核情况 的 分页结果
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> searchAudit(Map<String, Object> param);
	/**
	 * searchAudit()方法中符合条件的总数
	 * @param param
	 * @return
	 */

	int getAuditTotal(Map<String, Object> param);
	/**
	 * 查询某学生
	 * @param studentId
	 * @return
	 */
	List<Map<String, Object>> searchAuditDetailOfStudent(Map<String, Object> param);


}
