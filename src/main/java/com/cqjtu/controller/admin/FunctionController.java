package com.cqjtu.controller.admin;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqjtu.mapper.FunctionMapper;
import com.cqjtu.model.Function;
import com.cqjtu.model.Role;
import com.cqjtu.service.FunctionService;
import com.cqjtu.service.RoleService;
import com.cqjtu.util.FunctionCheckedTreeUtil;
import com.cqjtu.util.FunctionTreeUtil;

@Controller
@RequestMapping("/function")
public class FunctionController {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@Autowired
	private FunctionService functionService;
	@Autowired
	private RoleService roleService;
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		try {
			this.request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping("insertFunction")
	public String insertFunction(@RequestBody Function function){
		JSONObject json=new JSONObject();
		function.setFunctionState("open");
		if(function.getParentState()==null){
			json.put("status", "false");
		}else if("closed".equals(function.getParentState())){
			if(functionService.insertFunction(function)==true){
				json.put("status", "true");
				FunctionTreeUtil.init();
				FunctionCheckedTreeUtil.init();
			}else{
				json.put("status", "false");
			}
		}else{
			String[] id=new String[1];
			id[0]=function.getFunctionPid().toString();
			List<Role> list=roleService.checkUseFunctions(id);
			if(list==null||list.size()==0){
				if("open".equals(function.getParentState())){
					Function parent=new Function();
					parent.setFunctionId(function.getFunctionPid());
					parent.setFunctionState("closed");
					if(functionService.updateFunction(parent)){
						if(functionService.insertFunction(function)){
							json.put("status", "true");
							FunctionTreeUtil.init();
							FunctionCheckedTreeUtil.init();
						}
					}
					json.put("status", "true");
				}else{
					json.put("status", "false");
				}
			}else{
				StringBuffer str=new StringBuffer();
				str.append("添加功能失败，存在用户名为");
				for(Role role:list){
					str.append(role.getRoleName());
					str.append("、");
				}
				str.deleteCharAt(str.length()-1);
				str.append("的用户正在使用该功能，如果需要在此功能下添加功能，请先去除上述用户的相应功能权限。");
				json.put("status", "using");
				json.put("message", str.toString());
			}
		}

		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("updateFunction")
	public String updateFunction(@RequestBody Function function){
		JSONObject json=new JSONObject();
		if(function.getFunctionState()==null){
			function.setFunctionState("");
		}
		if(functionService.updateFunction(function)){
			json.put("status", "true");
			FunctionTreeUtil.init();
			FunctionCheckedTreeUtil.init();
		}else{
			json.put("status", "false");
		}
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("deleteFunction")
	public String deleteFunction(@RequestBody Function function){
		JSONObject json=new JSONObject();
		if(function.getFunctionPid()==null){
			if(functionService.deleteFunctionByPrimaryKey(function.getFunctionId())==true){
				json.put("status", "true");
				FunctionTreeUtil.init();
				FunctionCheckedTreeUtil.init();
				
			}else{
				json.put("status", "false");
			}
		}else{
			Function parentFunction=new Function();
			parentFunction.setFunctionId(function.getFunctionPid());
			parentFunction.setFunctionState("open");
			if(functionService.deleteFunctionByPrimaryKey(function.getFunctionId())==true
				&&functionService.updateFunction(parentFunction)==true){
				FunctionTreeUtil.init();
				FunctionCheckedTreeUtil.init();
				json.put("status", "true");
			}else{
				json.put("status", "false");
			}
		}
		return json.toJSONString();
	}
}
