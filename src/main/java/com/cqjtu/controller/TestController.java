package com.cqjtu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cqjtu.model.Account;

@Controller
@RequestMapping("")
public class TestController {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	@RequestMapping("test")
	public ModelAndView indexPage(HttpServletRequest request) {
		Account account = (Account) session.getAttribute("account");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/admin");
		return mav;
	}
}
