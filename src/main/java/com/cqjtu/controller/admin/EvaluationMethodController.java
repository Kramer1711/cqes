package com.cqjtu.controller.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqjtu.mapper.AccountMapper;
import com.cqjtu.model.AcademicYear;
import com.cqjtu.model.Account;
import com.cqjtu.model.EvaluationMethod;
import com.cqjtu.service.AcademicYearService;
import com.cqjtu.service.AccountService;
import com.cqjtu.service.EvaluationMethodService;
import com.cqjtu.util.KnowExcel;
import com.cqjtu.util.TimeUtil;

@Controller
@RequestMapping("/evaluationMethod")
public class EvaluationMethodController {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
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
	@RequestMapping("/update")
	public String insertAcademicYearAndEvaluationMethod(@RequestParam("startYear") int startYear
			,@RequestParam("endYear") int endYear,@RequestParam("cjpjf") Double cjpjf,
			@RequestParam("cxf") Double cxf,@RequestParam("kjcxf") Double kjcxf,@RequestParam("wtf") Double wtf){
		JSONObject json=new JSONObject();
		//成绩平均分，操行分，科技创新分，文体分所取比例（页面传过来的为x%）
		Double[] percentage=new Double[4];
		percentage[0]=cjpjf/100.0;
		percentage[1]=cxf/100.0;
		percentage[2]=kjcxf/100.0;
		percentage[3]=wtf/100.0;
		Integer[] methodId=new Integer[]{1,2,3,1002};
		for(int i=startYear;i<endYear;i++){
			String academicYear=i+"-"+(i+1);
			for(int j=0;j<percentage.length;j++){
				EvaluationMethod tempMethod=new EvaluationMethod();
				tempMethod.setEvaluationMethodId(null);
				tempMethod.setAcademicYear(academicYear);
				tempMethod.setTypeId(methodId[j]);
				tempMethod.setPercentage(percentage[j]);
				evaluationMethodService.updatePercentage(tempMethod);
			}
		}
		json.put("status", "true");
		return json.toJSONString();
		
	}
	//getByAcademicYear
	@ResponseBody
	@RequestMapping("/getByAcademicYear")
	public String insertAcademicYearAndEvaluationMethod(@RequestParam("academicYear") String academicYear){
		JSONObject json=new JSONObject();
		DecimalFormat percentFormat = new DecimalFormat();   
	    percentFormat.applyPattern("#0.000%"); 
	    List<EvaluationMethod> list=evaluationMethodService.selectByAcademicYear(academicYear);
	    for(EvaluationMethod temp:list){
	    	json.put(getTypeSimpleName(temp.getTypeId()), percentFormat.format(temp.getPercentage()));
	    }
		json.put("status", "true");
		return json.toJSONString();
		
	}
	
	public String getTypeSimpleName(Integer itemId){
		if(itemId==1){
			return "cjpjf";
		}else if(itemId==2){
			return "cxf";
		}else if(itemId==3){
			return "kjcxf";
		}else if(itemId==1002){
			return "wtf";
		}else{
			return "null";
		}
	}
}
