package com.cqjtu.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqjtu.mapper.AccountMapper;
import com.cqjtu.mapper.AdminInfoMapper;
import com.cqjtu.mapper.AdminMapper;
import com.cqjtu.mapper.ScoreMapper;
import com.cqjtu.mapper.StudentInfoMapper;
import com.cqjtu.mapper.StudentMapper;
import com.cqjtu.mapper.TeacherInfoMapper;
import com.cqjtu.mapper.TeacherMapper;
import com.cqjtu.model.Account;
import com.cqjtu.model.Admin;
import com.cqjtu.model.AdminInfo;
import com.cqjtu.model.Score;
import com.cqjtu.model.Student;
import com.cqjtu.model.StudentInfo;
import com.cqjtu.model.Teacher;
import com.cqjtu.model.TeacherInfo;
import com.cqjtu.service.AccountService;
import com.cqjtu.service.StudentService;
import com.cqjtu.util.Password;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountMapper accountMapper;
	@Autowired
	AdminMapper adminMapper;
	@Autowired
	AdminInfoMapper adminInfoMapper;
	
	@Autowired
	TeacherMapper teacherMapper;
	@Autowired
	TeacherInfoMapper teacherInfoMapper;
	
	@Autowired
	StudentMapper studentMapper;
	@Autowired
	StudentInfoMapper studentInfoMapper;
	@Autowired
	ScoreMapper scoreMapper;
	

	@Override
	public Account getAccount(String name) {
		return accountMapper.selectByName(name);
	}

	@Override
	public boolean varificationAccount(Account account) {
		if (account == null) {
			return false;
		} else {
			Account rightUser = accountMapper.selectByName(account.getAccountName());
			if (rightUser == null) {
				return false;
			} else if (rightUser.getPassword().equals(account.getPassword())) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public int insertAccount(Account account) {
		return accountMapper.insert(account);
	}

	@Override
	@Transactional
	public int insertAccounts(List<Account> accounts) {
		int result = 0;
		List<Account> failedAccount = new LinkedList<>();
		for (Account aa : accounts) {
			int temp = accountMapper.insertSelective(aa);
			if (temp == 0) {
				failedAccount.add(aa);
			}
			result += temp;
		}
		System.out.println("应插入" + accounts.size() + "条，实际插入" + result + "条");
		return result - accounts.size();
	}

	@Override
	public int insertSelective(Account account) {
		return accountMapper.insertSelective(account);
	}

	@Override
	public List<Account> getAccounts(Account account) {
		return accountMapper.selectAccount(account);
	}

	@Override
	public int countAccounts(Account account) {
		Account count=accountMapper.countAccounts(account);
		if(count==null){
			return 0;
		}else{
			return count.getNumber();
		}
	}

	@Override
	public int insertAccountsOfList(List<Account> list) {
		return accountMapper.insertAccountsOfList(list);
	}

	@Override
	public boolean hasAccount(String accountName) {
		if(accountMapper.selectByName(accountName)==null){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public boolean updateAccounts(List<Account> accountList) {
		if(accountList==null){
			return false;
		}
		for (Account account : accountList) {
			try{
				accountMapper.updateByPrimaryKeySelective(account);
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean resetPassword(List<Account> accountList) {
		try{
	        for(Account account:accountList){
	        	account.setPassword(Password.getPassword());
	        	accountMapper.updateByPrimaryKeySelective(account);
	        }
			return true;			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
}
