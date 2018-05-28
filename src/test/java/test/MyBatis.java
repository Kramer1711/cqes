package test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.cqjtu.mapper.AccountMapper;
import com.cqjtu.mapper.AuditPermissionMapper;
import com.cqjtu.mapper.CollegeMapper;
import com.cqjtu.mapper.EvaluationMethodMapper;
import com.cqjtu.mapper.MajorMapper;
import com.cqjtu.mapper.QualityMapper;
import com.cqjtu.mapper.ScoreMapper;
import com.cqjtu.mapper.StudentInfoMapper;
import com.cqjtu.mapper.StudentMapper;
import com.cqjtu.model.Account;
import com.cqjtu.model.AuditPermission;
import com.cqjtu.model.College;
import com.cqjtu.model.EvaluationMethod;
import com.cqjtu.model.Major;
import com.cqjtu.model.Quality;
import com.cqjtu.model.Score;
import com.cqjtu.model.Student;
import com.cqjtu.model.StudentInfo;
import com.cqjtu.service.ScoreService;
import com.cqjtu.util.ParamUtil;
import com.microsoft.sqlserver.jdbc.SQLServerException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MyBatis {

	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private ScoreMapper scoreMapper;
	@Autowired
	private StudentInfoMapper studentInfoMapper;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private CollegeMapper collegeMapper;
	@Autowired
	private MajorMapper majorMapper;
	@Autowired
	private QualityMapper qualityMapper;
	@Autowired
	private AuditPermissionMapper auditPermissionMapper;
	@Autowired
	private EvaluationMethodMapper evaluationMethodMapper;

	@Test
	public void test() {
		Quality quality = qualityMapper.selectByPrimaryKey(1015);
		System.out.println(quality.toString());
	}

	@Test
	public void test1() {
		Map<String, Object> param = ParamUtil.getParamMap();
		param.put("key", "");
		param.put("collegeId", null);
		param.put("majorId", null);
		param.put("status", null);
		param.put("page", 1);
		param.put("rows", 100);
		param.put("sort", "studentId");
		param.put("order", "asc");
		param.put("sortOrder", "studentId asc");
		param.put("status", "全部");
		System.out.println(param.toString());
		List<Map<String, Object>> list = qualityMapper.searchQualityScore(param);
		int total = qualityMapper.getTotalQualityScore(param);
		System.out.println(total + "\n");
		System.out.println(JSONArray.toJSON(list).toString());
	}

	@Test
	public void test2() {
		String academicYear = "2017-2018";
		List<EvaluationMethod> list = evaluationMethodMapper.selectByAcademicYear(academicYear);
		System.out.println(list.size());
	}
	@Transactional
	@Test
	public void transactional() {
		Student student = new Student();
		student.setStudentId(Long.parseLong("631406010210"));
		student.setAccountId(111);
		Student student1 = new Student();
		student1.setStudentId(Long.parseLong("631406010211"));
		student1.setAccountId(5259);
		int result = studentMapper.insert(student1);
		System.out.println(result);
		//studentMapper.insert(student);
		throw new RuntimeException("test");  
	}
	
}
