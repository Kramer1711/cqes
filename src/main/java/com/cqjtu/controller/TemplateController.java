package com.cqjtu.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cqjtu.service.AccountService;
import com.cqjtu.service.QualityService;
import com.cqjtu.service.ScoreService;
import com.cqjtu.service.StudentInfoService;
import com.cqjtu.service.StudentService;

@Controller
@RequestMapping("template")
public class TemplateController {

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

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	@RequestMapping("downloadTemplate")
	public String test(HttpServletRequest request) {
		return "other/template";
	}

	@ResponseBody
	@RequestMapping(value="download",method=RequestMethod.GET)
	public ResponseEntity<byte[]> download(HttpServletRequest request, @RequestParam("filename") String fileName)
			throws Exception {
		// 下载文件路径
		String filePath=request.getServletContext().getRealPath("")+"\\WEB-INF\\classes\\template\\"+fileName;
        File file=new File(filePath);  
        HttpHeaders headers = new HttpHeaders();    
        String downloadFileName;
		try {
			downloadFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
        headers.setContentDispositionFormData("attachment", downloadFileName);   
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
        try {
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}    
	}
}
