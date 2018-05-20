package com.cqjtu.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.model.Account;

public interface AccountService {
	Account getAccount(String accountname);

	boolean varificationAccount(Account account);
	
	/**
	 * 插入一条账户信息
	 * @param account
	 * @return
	 */
	int insertAccount(Account account);
	/**
	 * 一次性插入数据
	 * @return 成功插入的条数
	 */
	int insertAccountsOfList(List<Account> list);
	int insertSelective(Account account);
	
	/**
	 * 事务：批量插入账户信息
	 * @param accounts 
	 * @return 返回为0则全部插入成功，失败则返回失败的条数
	 */
	int insertAccounts(List<Account> accounts);
	
	/**
	 * 
	 */
	List<Account> getAccounts(Account account);
	boolean hasAccount(String accountName);
	int countAccounts(Account account);
	boolean updateAccounts(List<Account> accountList);

	boolean resetPassword(List<Account> accountList);
}
