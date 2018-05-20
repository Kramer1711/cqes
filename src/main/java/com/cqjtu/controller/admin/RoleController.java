package com.cqjtu.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.mapper.RoleMapper;
import com.cqjtu.mapper.StudentInfoMapper;
import com.cqjtu.mapper.StudentMapper;
import com.cqjtu.model.Role;
import com.cqjtu.service.AccountService;
import com.cqjtu.service.RoleService;
import com.cqjtu.service.impl.RoleServiceImpl;

@Controller
@RequestMapping("/admin")
public class RoleController {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	@Autowired
	RoleService roleService;
	@Autowired
	RoleMapper roleMapper;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	@ResponseBody
	@RequestMapping("/insertRole")
	public String insertRole(@RequestBody Role role) {
		JSONObject json=new JSONObject();
		if(roleService.insert(role)){
			json.put("result", "success");
		}else{
			json.put("result", "false");
		}
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping("/getAllRolesOfCombobox")
	public String getAllRolesOfCombobox() {
		return roleService.getAllRolesOfCombobox();
	}

	@ResponseBody
	@RequestMapping("/updateRole")
	public String updateRole(@RequestBody Role role) {
		JSONObject json=new JSONObject();
		if(roleService.update(role)){
			json.put("result", "success");
		}else{
			json.put("result", "false");
		}
		return json.toString();
	}
}
