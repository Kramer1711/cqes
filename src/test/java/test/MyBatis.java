package test;

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

import com.alibaba.fastjson.JSONArray;
import com.cqjtu.mapper.AccountMapper;
import com.cqjtu.mapper.AuditPermissionMapper;
import com.cqjtu.mapper.CollegeMapper;
import com.cqjtu.mapper.MajorMapper;
import com.cqjtu.mapper.QualityMapper;
import com.cqjtu.mapper.ScoreMapper;
import com.cqjtu.mapper.StudentInfoMapper;
import com.cqjtu.mapper.StudentMapper;
import com.cqjtu.model.Account;
import com.cqjtu.model.AuditPermission;
import com.cqjtu.model.College;
import com.cqjtu.model.Major;
import com.cqjtu.model.Quality;
import com.cqjtu.model.Score;
import com.cqjtu.model.Student;
import com.cqjtu.model.StudentInfo;
import com.cqjtu.service.ScoreService;
import com.cqjtu.util.ParamUtil;

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
	@Test
	public void test() {
		Quality quality = qualityMapper.selectByPrimaryKey(1015);
		System.out.println(quality.toString());
	}
	@Test
	public void test1() {
		Map<String,Object>param = ParamUtil.getParamMap();
		param.put("key", "");
		param.put("collegeId", null);
		param.put("majorId", null);
		param.put("status", null);
		param.put("page", 1);
		param.put("rows", 20);
		param.put("sort", "studentId");
		param.put("order", "asc");
		System.out.println(param.toString());
		List<Map<String, Object>>list = auditPermissionMapper.searchAgentAudit(param);
		int total = auditPermissionMapper.getTotal(param);
		System.out.println(total+"\n"+JSONArray.toJSON(list).toString());
	}
	@Test
	public void test2() {
		
		Map<String,Object> param = new HashMap<>();
		param.put("studentId", Long.valueOf("631306020223"));
		param.put("acdemicYear", "2017-2018");
		
		List<Map<String,Object>> result= qualityMapper.selectAuditSituationList(param);
		System.out.println(result.toString());
	}
}
