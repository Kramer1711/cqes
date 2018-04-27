package com.cqjtu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqjtu.model.Account;
import com.cqjtu.model.Quality;
import com.cqjtu.model.Score;
import com.cqjtu.model.Student;
import com.cqjtu.model.StudentInfo;
import com.cqjtu.service.AccountService;
import com.cqjtu.service.QualityService;
import com.cqjtu.service.ScoreService;
import com.cqjtu.service.StudentInfoService;
import com.cqjtu.service.StudentService;

@Controller
@RequestMapping("test")
public class TestController {

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

	@RequestMapping("index")
	public String test(HttpServletRequest request) {
		return "teacher/test";
	}

	@Transactional
	@ResponseBody
	@RequestMapping(value = "uploadtest", method = RequestMethod.POST)
	public String uploadtest(HttpServletRequest request, @RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) throws Exception {

		System.out.println("uploadtest...");

		File xlsfile = null;
		xlsfile = File.createTempFile("E://tmp", null);
		file.transferTo(xlsfile);
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(xlsfile));
		HSSFSheet sheet = null;
		JSONArray xlsJSONArray = new JSONArray();// 存表
		JSONObject rowName = new JSONObject();// 存储列名

		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
			sheet = workbook.getSheetAt(i);
			for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
				HSSFRow row = sheet.getRow(j);
				JSONObject cellJSON = new JSONObject();
				if (row != null) {
					for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
						if (j == 0) {// 列名
							rowName.put("列" + k, row.getCell(k));
						} else if (row.getCell(k) != null) { // getCell 获取单元格数据
							String cellContent = row.getCell(k).toString();
							cellJSON.put(rowName.get("列" + k).toString(), cellContent);
							System.out.print(cellContent + "\t");
						} else {
							System.out.print("\t");
						}
					}
				}
				if (j != 0)
					xlsJSONArray.add(cellJSON);
				System.out.println(""); // 读完一行后换行
			}
			System.out.println("读取sheet表：" + workbook.getSheetName(i) + " 完成");
		}
		System.out.println("-------------------------");
		System.out.println(xlsJSONArray.toJSONString());

		List<Account> accounts = new LinkedList<>();
		for (int i = 0; i < xlsJSONArray.size(); i++) {
			JSONObject jsonObject = xlsJSONArray.getJSONObject(i);
			
			//账号信息
			Account account = new Account();
			account.setAccountName(jsonObject.getString("学号"));
			account.setPassword("123");
			account.setRoleId(3);
			accountService.insertAccount(account);
			
			//成绩信息
			Score score = new Score();
			score.setGpa(Double.parseDouble(jsonObject.getString("学年平均学分绩点")));
			score.setAveScore((score.getGpa()+5)*10);
			score.setAcademicYear("2017-2018");
			System.out.println(score);
			scoreService.insertScore(score);
			
			//操行分
			Quality quality = new Quality();
			qualityService.insertQuality(quality);
			
			//学生详细信息
			StudentInfo studentInfo = new StudentInfo();
			studentInfo.setMajorId(1);
			studentInfo.setStudentName(jsonObject.getString("姓名"));
			studentInfo.setGrade((int)Double.parseDouble(jsonObject.getString("年级")));
			studentInfoService.insertStudentInfo(studentInfo);
			
			//学生信息
			Student student = new Student();
			student.setStudentId(Long.parseLong(jsonObject.getString("学号")));
			student.setAccountId(account.getAccountId());
			student.setQualityId(quality.getQualityId());
			student.setScoreId(score.getScoreId());
			student.setStudentInfoId(studentInfo.getStudentInfoId());
			studentService.insertStudent(student);
		}
		return JSON.toJSONString(xlsJSONArray);
	}
}
