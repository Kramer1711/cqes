package com.cqjtu.mapper;

import com.cqjtu.model.Account;

public interface AccountMapper {
	int deleteByPrimaryKey(Integer accountId);

	int insert(Account record);

	int insertSelective(Account record);

	Account selectByPrimaryKey(Integer accountId);

	int updateByPrimaryKeySelective(Account record);

	int updateByPrimaryKey(Account record);

	Account selectByName(String name);
}