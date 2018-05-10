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
		List<Map<String, Object>> result = null;
		String sortOrder = "";
		String sort = (String) param.get("sort");
		String order = (String) param.get("order");
		if (sort.contains(",")) {
			String[] sortArray = sort.split(",");
			String[] orderArray = order.split(",");
			for (int i = 0; i < sortArray.length; i++) {
				sortOrder += sortArray[i] + " " + orderArray[i];
				if (i < sortArray.length - 1) {
					sortOrder += ",";
				}
			}
		}
		param.put("sortOrder", sortOrder);
		result = studentMapper.search(param);
		return result;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> searchAudit(Map<String, Object> param) {
		List<Map<String, Object>> studentList = searchStudent(param);// 学生信息
		String status = (String) param.get("status");
		String academicYear = (String) param.get("academicYear");
		if (status.equals("全部"))
			status = "";
		for (int i = 0; i < studentList.size(); i++) {
			Map<String, Object> student = studentList.get(i);
			student.put("status", status);
			student.put("academicYear",academicYear);
			Quality quality = qualityMapper.selectByStudentId(student);// 审核总情况
			if (quality == null) {
				studentList.remove(student);
				i--;
			} else {
				student.put("status", quality.getStatus());
			}
		}
		return studentList;
	}

	@Override
	public List<Map<String, Object>> searchAuditDetailOfStudent(Map<String, Object> param) {
//		System.out.println("Service层：" + studentId);
		return qualityMapper.selectAuditSituationList(param);
	}
}
