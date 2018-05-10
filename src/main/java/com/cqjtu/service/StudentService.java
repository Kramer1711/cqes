package com.cqjtu.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.model.Student;

public interface StudentService {
	int insertStudent(Student student);

	/**
	 * 
	 * @param key	关键字
	 * @param start	开始行
	 * @param end	结束行
	 * @return
	 */
	List<Map<String, Object>> searchStudent(Map<String,Object> param);

	/**
	 * 审核情况总览
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> searchAudit(Map<String, Object> param);
	/**
	 * 查询某学生
	 * @param studentId
	 * @return
	 */
	List<Map<String, Object>> searchAuditDetailOfStudent(Map<String, Object> param);

}
