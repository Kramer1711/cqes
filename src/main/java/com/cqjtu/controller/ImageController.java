package com.cqjtu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cqjtu.service.AccountService;
import com.cqjtu.service.QualityService;
import com.cqjtu.service.ScoreService;
import com.cqjtu.service.StudentInfoService;
import com.cqjtu.service.StudentService;

@Controller
@RequestMapping("image")
public class ImageController {

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

	@RequestMapping("getImage")
	public void getJpg(HttpServletRequest request, HttpServletResponse response, @RequestParam("path") String path) {
		System.out.println(path);
		if (!path.equals("") && !path.equals("null")&& path != null&&!path.equals("undefined")) {
			System.out.println("searchImage");
			InputStream inputStream = null;
			OutputStream writer = null;
			try {
				inputStream = new FileInputStream(new File(path));
				writer = response.getOutputStream();
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(buf)) != -1) {
					writer.write(buf, 0, len); // 写
				}
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
					if (writer != null) {
						writer.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
