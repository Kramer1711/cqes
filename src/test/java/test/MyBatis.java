package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cqjtu.mapper.AccountMapper;
import com.cqjtu.model.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MyBatis {

	@Autowired
	private AccountMapper accountMapper;

	@Test
	public void testAccount() {
			Account users = accountMapper.selectByName("111");
			System.out.println(users.toString());
	}

}
