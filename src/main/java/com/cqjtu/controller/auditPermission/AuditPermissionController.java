package com.cqjtu.controller.auditPermission;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.model.Account;
import com.cqjtu.service.AccountService;
import com.cqjtu.service.AuditPermissionService;

@Controller
@RequestMapping("auditPermission")
public class AuditPermissionController {
	
	@Autowired
	AccountService accountService;
	@Autowired
	AuditPermissionService auditPermissionService;

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
	@RequestMapping("getPermission")
	public JSONObject getPermission(HttpServletRequest request) {
		Account account = (Account) request.getSession().getAttribute("account");
		Map<String,Object> result = auditPermissionService.getAccountAndPermission(account);
		JSONObject resultJSON = (JSONObject) JSONObject.toJSON(result);
		return resultJSON;
	}
}
