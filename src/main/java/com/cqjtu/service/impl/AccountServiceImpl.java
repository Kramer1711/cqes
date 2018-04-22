package com.cqjtu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
