package com.cqjtu.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cqjtu.mapper.RoleMapper;
import com.cqjtu.model.Role;
import com.cqjtu.service.RoleService;
import com.cqjtu.util.FunctionCheckedTreeUtil;
import com.cqjtu.util.FunctionTreeUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleMapper roleMapper;

	@Override
	public boolean insert(Role role) {
		System.out.println(roleMapper == null);
		if (roleMapper.insert(role) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getAllRolesOfCombobox() {
		List<Role> list = roleMapper.selectAll();
		JSONArray roles = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			Role tempRole = list.get(i);
			obj.put("id", tempRole.getRoleId());
			obj.put("text", tempRole.getRoleName());
			roles.add(obj);
		}
		return roles.toJSONString();
	}
	/**
	 * 检查是否有使用到相应功能的用户
	 * @return 使用到相应功能的用户
	 */
	@Override
	public List<Role> checkUseFunctions(String[] functionId){
		List<Role> roleList=this.getAllRoles();
		List<Role> useRoleList=new LinkedList<Role>();
		for(Role role:roleList){
			if(role.getFunctionIds()!=null&&role.getFunctionIds().length()>0){
				String[] useFunctionId=role.getFunctionIds().split(",");
				boolean using=false;
				for(int i=0;i<useFunctionId.length&&using==false;i++){
					for(int j=0;j<functionId.length;j++){
						if(useFunctionId[i].equals(functionId[j])){
							useRoleList.add(role);
							using=true;
							break;
						}
					}
				}
			}
		}
		return useRoleList;
	}
	
	@Override
	public List<Role> getAllRoles(){
		return roleMapper.selectAll();
	}
	
	@Override
	public boolean update(Role role) {
		if(roleMapper.updateByPrimaryKeySelective(role)>0){
			FunctionTreeUtil.init();
			FunctionCheckedTreeUtil.init();
			return true;
//			if(FunctionTreeUtil.updateRoleTree(role.getRoleId())==true
//					&&FunctionCheckedTreeUtil.updateRoleTree(role.getRoleId())==true){
//				return true;
//			}else{
//				return false;
//			}
		}else{
			return false;
		}
	}

}