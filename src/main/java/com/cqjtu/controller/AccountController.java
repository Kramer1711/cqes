package com.cqjtu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.mapper.StudentInfoMapper;
import com.cqjtu.mapper.StudentMapper;
import com.cqjtu.model.Account;
import com.cqjtu.model.Student;
import com.cqjtu.model.StudentInfo;
import com.cqjtu.service.AccountService;
import com.cqjtu.service.StudentInfoService;
import com.cqjtu.service.StudentService;
import com.cqjtu.util.MD5Util;
/**
 * 
 * @author 邱凯
 *
 */
@Controller
@RequestMapping("")
public class AccountController {

	@Autowired
	AccountService accountService;
	@Autowired
	StudentMapper studentMapper;
	@Autowired 
	StudentInfoMapper studentInfoMapper;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	@RequestMapping("index")
	public String indexPage(HttpServletRequest request) {
		Account account = (Account) session.getAttribute("account");
		return "/index";
	}
	
	@RequestMapping("loginPage")
	public String intoLoginPage(HttpServletRequest request) {
		Account account = (Account) session.getAttribute("account");
		return "/loginPage";
	}
	
	@RequestMapping("/main")
	public String mainPage(HttpServletRequest request) {
		Account account = (Account) session.getAttribute("account");
		return "/main";
	}
	
	@ResponseBody
	@RequestMapping("/login")
	public String login(@RequestBody Account account) {
		System.out.println("login...");
		System.out.println(account);
		JSONObject json = new JSONObject();
		//加密
		//account.setPassword(MD5Util.toMD5(account.getPassword()));
		//验证
		boolean result = accountService.varificationAccount(account);
		if (result) {
			account = accountService.getAccount(account.getAccountName());
			System.out.println(account);
			session.setAttribute("account", account);
//			Student student = studentMapper.selectByAccountId(account.getAccountId());
//			StudentInfo studentInfo = studentInfoMapper.selectByStudentId(student.getStudentId());
//			JSONObject studentJSON = (JSONObject) JSONObject.toJSON(student);
//			studentJSON.put("studentInfo", studentInfo);
			System.out.println(session.getId());
//			json.put("info", studentJSON);
			json.put("result", "SUCCESS");
		} else{
			json.put("result", "帐号或密码错误");
		}
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping("/logout")
	public String logout() {
		System.out.println("logout...");
		session.invalidate();
		return "SUCCESS";
	}
}
