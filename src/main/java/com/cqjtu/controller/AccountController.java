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
import com.cqjtu.model.Account;
import com.cqjtu.service.AccountService;
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
	public ModelAndView indexPage(HttpServletRequest request) {
		Account account = (Account) session.getAttribute("account");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/index");
		return mav;
	}
	@RequestMapping("loginPage")
	public ModelAndView intoLoginPage(HttpServletRequest request) {
		Account account = (Account) session.getAttribute("account");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/loginPage");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("/login")
	public String login(@RequestBody Account account) {
		System.out.println("login...");
		//模拟网络延迟
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(account);
		JSONObject json = new JSONObject();
		//加密
		//account.setPassword(MD5Util.toMD5(account.getPassword()));
		//验证
		boolean result = accountService.varificationAccount(account);
		if (result) {
			session.setAttribute("account", account);
			System.out.println(session.getId());
			json.put("result", "SUCCESS");
		} else
			json.put("result", "PASSWORD ERROR");
		System.err.println(json.toString());
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
