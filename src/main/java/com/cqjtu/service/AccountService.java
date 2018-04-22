package com.cqjtu.service;

import com.cqjtu.model.Account;

public interface AccountService {
	Account getAccount(String accountname);

	boolean varificationAccount(Account account);
	
}
