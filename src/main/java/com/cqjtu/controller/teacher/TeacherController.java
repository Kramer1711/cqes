package com.cqjtu.controller.teacher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cqjtu.service.AccountService;
import com.cqjtu.util.UploadAndDownloadUtil;

@Controller
@RequestMapping("teacher")
public class TeacherController {
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

	@RequestMapping("uploadScorePage")
	public String indexPage() {
		return "/teacher/uploadScore";
	}

	@ResponseBody
	@RequestMapping(value = "uploadScore", method = RequestMethod.POST)
	public String uploadtest(HttpServletRequest request, @RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) throws Exception {
		System.out.println(name);
		String savepath = "E:/map/";
		// 文件上传
		boolean result = UploadAndDownloadUtil.upload(file, savepath);
		if (result)
			return "SUCCESS";
		else
			return "ERROR";
	}

}
