package com.cqjtu.controller.common;

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
import com.cqjtu.service.CommonService;

@Controller
@RequestMapping("common")
public class CommonController {

	@Autowired
	AccountService accountService;

	@Autowired
	CommonService commonService;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	/**
	 * 修改密码页面
	 * @param request
	 * @return
	 */
	@RequestMapping("updatePasswordPage")
	public String updatePasswordPage(HttpServletRequest request) {
		return "admin/updatePassword";
	}
	/**
	 * 个人信息页面
	 * @param request
	 * @return
	 */
	@RequestMapping("personalInfoPage")
	public String personInfoPage(HttpServletRequest request) {
		return "common/personalInfoPage";
	}
	
	@ResponseBody
	@RequestMapping("getPersonalInfo")
	public JSONObject getPersonalInfo(HttpServletRequest request) {
		Account account = (Account) request.getSession().getAttribute("account");
		return commonService.getPersonalInfo(account);
	}
}
