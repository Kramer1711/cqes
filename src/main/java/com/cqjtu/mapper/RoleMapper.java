package com.cqjtu.mapper;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cqjtu.model.Role;

public interface RoleMapper {
	
	List<Role> selectAll();
	
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}