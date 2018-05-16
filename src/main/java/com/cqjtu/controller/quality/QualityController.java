package com.cqjtu.controller.quality;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.cqjtu.service.QualityService;

@Controller
@RequestMapping("quality")
public class QualityController {
	@Autowired
	QualityService qualityService;

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
	@RequestMapping("getQualityType")
	public String getQualityType(HttpServletRequest request) {
		List<Map<String,Object>> qualityTypeList = qualityService.getTypeList();
		JSONArray result = (JSONArray) JSONArray.toJSON(qualityTypeList);
		return result.toJSONString();
	}
}
