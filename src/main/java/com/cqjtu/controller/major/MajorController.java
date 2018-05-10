package com.cqjtu.controller.major;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.cqjtu.model.Major;
import com.cqjtu.service.MajorService;

@Controller
@RequestMapping("major")
public class MajorController {
	
	@Autowired
	MajorService majorService;
	
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
	@RequestMapping("getMajorListByCollegeId")
	public String getMajorListByCollegeId(HttpServletRequest request,@RequestParam("collegeId") int collegeId) {
		System.out.println(collegeId);
		List<Major> majorList = majorService.getMajorListByCollegeId(collegeId);
		Major allSelection = new Major();
		allSelection.setCollegeId(collegeId);
		allSelection.setMajorId(-1);
		allSelection.setMajorName("全部");
		majorList.add(0,allSelection);
		return JSONArray.toJSONString(majorList);
	}
}
