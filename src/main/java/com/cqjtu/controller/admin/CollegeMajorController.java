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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqjtu.model.Account;
import com.cqjtu.model.College;
import com.cqjtu.model.Major;
import com.cqjtu.service.CollegeService;
import com.cqjtu.service.MajorService;

@Controller
@RequestMapping("/collegeMajor")
public class CollegeMajorController {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@Autowired
	private CollegeService collegeService;
	
	@Autowired
	private MajorService majorService;
	
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
	@RequestMapping("/insertAccount")
	public String insertAccount(@RequestBody Account account){
		JSONObject json=new JSONObject();
		return json.toString();
	}
	
	
	@ResponseBody
	@RequestMapping("/getCollegeCombobox")
	public String getCollegeCombobox(){
			JSONObject json=new JSONObject();
			JSONArray collegeMajorTree=new JSONArray();
			List<College> collegeList=collegeService.getCollegeList();
			return JSONArray.toJSONString(collegeList);
	}
	
	@ResponseBody
	@RequestMapping("/insertMajor")
	public String insertMajor(@RequestBody Major major){
		JSONObject json=new JSONObject();
		if(majorService.insertMajor(major)){
			json.put("status", "true");
		}else{
			json.put("status", "false");
		}
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/insertCollege")
	public String insertMajor(@RequestBody College college){
		JSONObject json=new JSONObject();
		if(collegeService.insertCollege(college)){
			json.put("status", "true");
		}else{
			json.put("status", "false");
		}
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/updateMajor")
	public String updateMajor(@RequestBody Major major){
		JSONObject json=new JSONObject();
		if(majorService.updateMajor(major)){
			json.put("status", "true");
		}else{
			json.put("status", "false");
		}
		return json.toJSONString();
	}

	@ResponseBody
	@RequestMapping("/updateCollege")
	public String updateCollege(@RequestBody College college){
		JSONObject json=new JSONObject();
		if(collegeService.updateCollege(college)){
			json.put("status", "true");
		}else{
			json.put("status", "false");
		}
		return json.toJSONString();
	}
	
	
	@ResponseBody
	@RequestMapping("/deleteCollege")
	public String deleteCollege(@RequestParam("collegeId") Integer collegeId){
		JSONObject json=new JSONObject();
		if(collegeService.deleteByCollegeId(collegeId)){
			json.put("status", "true");
		}else{
			json.put("status", "false");
		}
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/deleteMajor")
	public String deleteMajor(@RequestParam("majorId") Integer majorId){
		JSONObject json=new JSONObject();
		if(majorService.deleteByMajorId(majorId)){
			json.put("status", "true");
		}else{
			json.put("status", "false");
		}
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/getCollegeMajorTree")
	public String getCollegeMajorTree(){
		String id=request.getParameter("id");
		if(id==null){
			JSONObject json=new JSONObject();
			JSONArray collegeMajorTree=new JSONArray();
			List<College> collegeList=collegeService.getCollegeList();
			for(College college:collegeList){
				JSONObject collegeNode=new JSONObject();
				collegeNode.put("id", college.getCollegeId());
				collegeNode.put("collegeId", college.getCollegeId());
				collegeNode.put("text", college.getCollegeName());
				collegeNode.put("state", "closed");
				collegeNode.put("status", "closed");
				collegeNode.put("kind", "college");
				List<Major> majorList=majorService.getMajorListByCollegeId(college.getCollegeId());
				JSONArray majorNodes=new JSONArray();
				for(Major major:majorList){
					JSONObject majorNode=new JSONObject();
					majorNode.put("id", college.getCollegeId().toString()+"+"+major.getMajorId().toString());
					majorNode.put("collegeId", college.getCollegeId());
					majorNode.put("text", major.getMajorName());
					majorNode.put("majorId", major.getMajorId().toString());
					majorNode.put("state", "open");
					majorNode.put("status", "open");
					majorNode.put("kind", "major");
					majorNode.put("children", new JSONArray());
					majorNodes.add(majorNode);
				}
				collegeNode.put("children", majorNodes);
				collegeMajorTree.add(collegeNode);
			}
			return collegeMajorTree.toJSONString();
		}else{
			return new JSONArray().toString();
		}
	}
}
