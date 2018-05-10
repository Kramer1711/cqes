package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.cqjtu.mapper.AccountMapper;
import com.cqjtu.mapper.CollegeMapper;
import com.cqjtu.mapper.MajorMapper;
import com.cqjtu.mapper.QualityMapper;
import com.cqjtu.mapper.ScoreMapper;
import com.cqjtu.mapper.StudentInfoMapper;
import com.cqjtu.mapper.StudentMapper;
import com.cqjtu.model.Account;
import com.cqjtu.model.College;
import com.cqjtu.model.Major;
import com.cqjtu.model.Quality;
import com.cqjtu.model.Score;
import com.cqjtu.model.Student;
import com.cqjtu.model.StudentInfo;
import com.cqjtu.service.ScoreService;

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
	@Test
	public void test() {
		Quality quality = qualityMapper.selectByPrimaryKey(1015);
		System.out.println(quality.toString());
	}
	@Test
	public void test1() {
		Student account= studentMapper.selectByPrimaryKey(Long.valueOf("631406010210"));
		System.out.println(account.toString());
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
