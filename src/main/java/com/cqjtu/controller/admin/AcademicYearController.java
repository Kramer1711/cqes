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

import com.alibaba.fastjson.JSONObject;
import com.cqjtu.model.AcademicYear;
import com.cqjtu.model.EvaluationMethod;
import com.cqjtu.service.AcademicYearService;
import com.cqjtu.service.EvaluationMethodService;

@Controller
@RequestMapping("/academicYear")
public class AcademicYearController {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@Autowired
	private AcademicYearService academicYearService;
	
	@Autowired
	private EvaluationMethodService evaluationMethodService;
	
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
	@RequestMapping("/getAcademicYears")
	public String getAccounts(@RequestParam("page") int page,@RequestParam("rows") int rows,@RequestParam("status") String status,
			@RequestParam("startYear") String startYear,@RequestParam("endYear") String endYear){
		//数据处理
		if(startYear!=null&&startYear.length()==0){
			startYear=null;
		}
		if(endYear!=null&&endYear.length()==0){
			endYear=null;
		}
		if(status!=null&&status.equals("all")){
			status=null;
		}
		
		//处理完毕，进行初始化
		JSONObject json=new JSONObject();
		AcademicYear search=new AcademicYear();
		search.setStatus(status);
		int startNum=(page-1)*rows;
		int endNum=page*rows+1;
		search.setStartNum(startNum);
		search.setEndNum(endNum);
		search.setStartYear(startYear);
		search.setEndYear(endYear);
		search.setStatus(status);
		//查询结果
		List<AcademicYear> list= academicYearService.getAcademicYears(search);
		json.put("total", academicYearService.countSelective(search));
		json.put("rows", list);
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping("/updateAcademicYear")
	public String updateAcademicYear(@RequestBody AcademicYear year){
		JSONObject json=new JSONObject();
		if("doing".equals(year.getStatus())){
			AcademicYear search=new AcademicYear();
			search.setStatus("doing");
			List<AcademicYear> list=academicYearService.getAcademicYears(search);
			if(list.size()==0){
				if(academicYearService.updateByPrimaryKeySelective(year)){
					json.put("status", "true");
				}else{
					json.put("status", "false");
				}
			}else{
				json.put("year", list.get(0).getAcademicYear().toString());
				json.put("status", "having");
			}
		}else if(academicYearService.updateByPrimaryKeySelective(year)){
			json.put("status", "true");
		}else{
			json.put("status", "false");
		}
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("/insertAcademicYearAndEvaluationMethod")
	public String insertAcademicYearAndEvaluationMethod(@RequestParam("add_startYear") int startYear
			,@RequestParam("add_endYear") int endYear,@RequestParam("add_cjpjf") Double cjpjf,
			@RequestParam("add_cxf") Double cxf,@RequestParam("add_kjcxf") Double kjcxf,@RequestParam("add_wtf") Double wtf){
		JSONObject json=new JSONObject();
		//成绩平均分，操行分，科技创新分，文体分所取比例（页面传过来的为x%）
		Double[] percentage=new Double[4];
		percentage[0]=cjpjf/100.0;
		percentage[1]=cxf/100.0;
		percentage[2]=kjcxf/100.0;
		percentage[3]=wtf/100.0;
		Integer[] methodId=new Integer[]{1,2,3,1002};
		int insert=0;
		int sum=endYear-startYear;
		for(int i=startYear;i<endYear;i++){
			String academicYear=i+"-"+(i+1);
			AcademicYear tempYear=new AcademicYear();
			tempYear.setAcademicYear(academicYear);
			if(academicYearService.having(tempYear)==false){
				tempYear.setStatus("undo");
				academicYearService.insertAcademicYear(tempYear);
				insert++;
				for(int j=0;j<percentage.length;j++){
					EvaluationMethod tempMethod=new EvaluationMethod();
					tempMethod.setEvaluationMethodId(null);
					tempMethod.setAcademicYear(academicYear);
					tempMethod.setTypeId(methodId[j]);
					tempMethod.setPercentage(percentage[j]);
					evaluationMethodService.insertSelective(tempMethod);
				}
			}
		}
		StringBuffer message=new StringBuffer();
		message.append("添加评测学年成功，其中实际添加").append(insert).append("个评测学年，");
		message.append("已存在").append(sum-insert).append("个评测学年。");
		json.put("message", message.toString());
		json.put("status", "true");
		return json.toJSONString();
		
	}
}
