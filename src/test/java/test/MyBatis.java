package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cqjtu.mapper.AccountMapper;
import com.cqjtu.mapper.ScoreMapper;
import com.cqjtu.mapper.StudentInfoMapper;
import com.cqjtu.mapper.StudentMapper;
import com.cqjtu.model.Account;
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
	@Test
	public void testAccount() {
		Account account = new Account();
		account.setAccountName("631423qwreq3");
		account.setPassword("123");
		account.setRoleId(3);
		System.out.println("-------------------------");
		int i = accountMapper.insert(account);
		System.out.println("-------------------------\n"+i);
		System.out.println("-------------------------\n"+account.getAccountId());
	}
	@Test
	public void test() {
		Student student = studentMapper.selectByAccountId(1213);
		System.out.println(student.toString());
	}
}
