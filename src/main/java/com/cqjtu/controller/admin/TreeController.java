package com.cqjtu.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.util.FunctionCheckedTreeUtil;
import com.cqjtu.util.FunctionTreeUtil;

@Controller
@RequestMapping("")
public class TreeController {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	@ResponseBody
	@RequestMapping("/getRoleTree")
	public String getRoleTree(@RequestParam("rid") int rid) {
		String id=request.getParameter("id");
		if(id!=null){
			return new JSONObject().toJSONString();
		}else{
			return FunctionTreeUtil.getRoleTree(rid);
		}
	}
	
	@ResponseBody
	@RequestMapping("/getRoleCheckTree")
	public String getRoleCheckTree(@RequestParam("rid") int rid) {
		String id=request.getParameter("id");
		if(id!=null){
			return new JSONObject().toJSONString();
		}else{
			return FunctionCheckedTreeUtil.getRoleTree(rid);
		}
	}
}
