package com.cqjtu.mapper;

import java.util.List;

import com.cqjtu.model.Role;

public interface RoleMapper {
	
	List<Role> selectAll();
	
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}