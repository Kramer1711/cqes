package com.cqjtu.controller.college;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.cqjtu.model.College;
import com.cqjtu.service.AccountService;
import com.cqjtu.service.CollegeService;
import com.cqjtu.service.QualityService;
import com.cqjtu.service.ScoreService;
import com.cqjtu.service.StudentInfoService;
import com.cqjtu.service.StudentService;

@Controller
@RequestMapping("college")
public class CollegeController {

	@Autowired
	AccountService accountService;
	@Autowired
	ScoreService scoreService;
	@Autowired
	QualityService qualityService;
	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	StudentService studentService;
	@Autowired
	CollegeService collegeService;

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
	@RequestMapping("getCollegeList")
	public String test(HttpServletRequest request) {
		List<College> collegeList = collegeService.getCollegeList();
		College allSelection = new College();
		allSelection.setCollegeId(-1);
		allSelection.setCollegeName("全部");
		collegeList.add(0, allSelection);
		return JSONArray.toJSONString(collegeList);
	}

}
