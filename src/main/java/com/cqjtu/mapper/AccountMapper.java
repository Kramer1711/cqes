package com.cqjtu.mapper;

import java.util.List;

import com.cqjtu.model.Account;

public interface AccountMapper {
	int deleteByPrimaryKey(Integer accountId);

	int insert(Account record);

	int insertSelective(Account record);

	Account selectByPrimaryKey(Integer accountId);

	int updateByPrimaryKeySelective(Account record);

	int updateByPrimaryKey(Account record);

	Account selectByName(String name);
	
	Account countAccounts(Account account);
	
	List<Account> selectAccount(Account account);
	
	int insertAccountsOfList(List<Account> list);
}