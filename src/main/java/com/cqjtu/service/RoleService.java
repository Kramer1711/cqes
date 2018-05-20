package com.cqjtu.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.cqjtu.model.Role;

public interface RoleService {

	public boolean insert(Role role);
	
	public String getAllRolesOfCombobox();
	
	public boolean update(Role role);

	List<Role> getAllRoles();

	List<Role> checkUseFunctions(String[] functionId);
}
