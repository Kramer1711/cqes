package com.cqjtu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqjtu.mapper.QualityMapper;
import com.cqjtu.mapper.StudentMapper;
import com.cqjtu.model.Quality;
import com.cqjtu.model.Student;
import com.cqjtu.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	StudentMapper studentMapper;
	@Autowired
	QualityMapper qualityMapper;

	@Override
	public int insertStudent(Student student) {
		return studentMapper.insert(student);
	}

	@Override
	public List<Map<String, Object>> searchStudent(Map<String, Object> param) {
		sortOrder(param);
		List<Map<String, Object>> result = studentMapper.search(param);
		return result;
	}
	/**
	 * sort order拆分组合
	 * @param param
	 */
	private void sortOrder(Map<String, Object> param) {
		//组装排序参数
		String sortOrder = "";
		String sort = (String) param.get("sort");
		String order = (String) param.get("order");
		String[] sortArray = sort.split(",");
		String[] orderArray = order.split(",");
		for (int i = 0; i < sortArray.length; i++) {
			sortOrder += sortArray[i] + " " + orderArray[i];
			if (i < sortArray.length - 1) {
				sortOrder += ",";
			}
		}
		param.put("sortOrder", sortOrder);
	}
	@Override
	public int getTotal(Map<String, Object> param) {
		return studentMapper.getTotal(param);
	}
	@Override
	@Transactional
	public List<Map<String, Object>> searchAudit(Map<String, Object> param) {
		sortOrder(param);
		return qualityMapper.searchQualityAndStudentBaseInfo(param);
	}
	@Override
	public int getAuditTotal(Map<String, Object> param) {
		return qualityMapper.getQualityAndStudentTotal(param);
	}

	@Override
	public List<Map<String, Object>> searchAuditDetailOfStudent(Map<String, Object> param) {
		return qualityMapper.selectAuditSituationList(param);
	}

	@Override
	public List<String> getGrades() {
		return studentMapper.getGrades();
	}




}
