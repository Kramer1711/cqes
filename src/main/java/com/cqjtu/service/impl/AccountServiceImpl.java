package com.cqjtu.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqjtu.mapper.AccountMapper;
import com.cqjtu.model.Account;
import com.cqjtu.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountMapper accountMapper;

	@Override
	public Account getAccount(String name) {
		return accountMapper.selectByName(name);
	}

	@Override
	public boolean varificationAccount(Account account) {
		Account rightUser = accountMapper.selectByName(account.getAccountName());
		if (rightUser.getPassword().equals(account.getPassword()))
			return true;
		else
			return false;
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
		for(Account aa:accounts) {
			int temp = accountMapper.insertSelective(aa);
			if(temp ==0) {
				failedAccount.add(aa);
			}
			result += temp;
		}
		System.out.println("应插入"+accounts.size()+"条，实际插入"+result+"条");
		return result-accounts.size();
	}
}
