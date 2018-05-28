package com.cqjtu.controller;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

	@RequestMapping("test")
	public String test(HttpServletRequest request) {
		return "teacher/test";
	}

	@ResponseBody
	@RequestMapping(value = "uploadtest", method = RequestMethod.POST)
	public String uploadtest(HttpServletRequest request, @RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) throws Exception {
		System.out.println("upload score...");

		File xlsfile = null;
		xlsfile = File.createTempFile("E://tmp", null);
		file.transferTo(xlsfile);
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(xlsfile));
		HSSFSheet sheet = null;
		JSONObject xlsInfoJSONObject = new JSONObject();// excel信息
		JSONArray studentInfoJSONArray = new JSONArray();// 存表
		JSONObject rowName = new JSONObject();// 存储列名
		JSONObject excelInfo = new JSONObject();// excel表名、时间等信息

		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
			sheet = workbook.getSheetAt(i);
			for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
				HSSFRow row = sheet.getRow(j);
				JSONObject cellJSON = new JSONObject();
				if (row != null) {
					for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
						if (j == 0) {
							excelInfo.put("SheetName", row.getCell(k).toString());
						} else if (j == 1) {
							if (k == 0) {// 报送单位
								excelInfo.put("SubmittedUnit", row.getCell(k).toString());
							}
							if (k == 1) {// 时间
								excelInfo.put("DateTime", row.getCell(k).toString());
							}
						} else if (j == 2) {// 列名
							rowName.put("row" + k, row.getCell(k));
						} else if (row.getCell(k) != null) { // getCell 获取单元格数据
							String cellContent = row.getCell(k).toString();
							cellJSON.put(rowName.get("row" + k).toString(), cellContent);
							System.out.print(cellContent + "\t");
						} else {
							System.out.print("\t");
						}
					}
				}
				if (j > 2)
					studentInfoJSONArray.add(cellJSON);
				System.out.println(""); // 读完一行后换行
			}
			System.out.println("读取sheet表：" + workbook.getSheetName(i) + " 完成");
		}
		System.out.println("-------------------------");
		xlsInfoJSONObject.put("SheetInfo", excelInfo);
		xlsInfoJSONObject.put("StudentInfo", studentInfoJSONArray);
		System.out.println(xlsInfoJSONObject.toJSONString());

		for (int i = 0; i < studentInfoJSONArray.size(); i++) {
			JSONObject jsonObject = studentInfoJSONArray.getJSONObject(i);

			// 账号信息
			Account account = new Account();
			account.setAccountName(jsonObject.getString("学号"));
			account.setPassword("123");
			account.setRoleId(3);
			accountService.insertAccount(account);

			// 学生信息
			Student student = new Student();
			student.setStudentId(Long.parseLong(jsonObject.getString("学号")));
			student.setAccountId(account.getAccountId());
			studentService.insertStudent(student);
			// 学生详细信息
			StudentInfo studentInfo = new StudentInfo();
			studentInfo.setMajorId(1);
			studentInfo.setStudentName(jsonObject.getString("姓名"));
			studentInfo.setGrade((int) Double.parseDouble(jsonObject.getString("年级")));
			studentInfo.setStudentId(student.getStudentId());
			studentInfoService.insertStudentInfo(studentInfo);

			// 成绩信息
			Score score = new Score();
			score.setGpa(Double.parseDouble(jsonObject.getString("学年平均学分绩点")));
			score.setAveScore((score.getGpa() + 5) * 10);
			score.setAcademicYear("2017-2018");
			score.setStudentId(student.getStudentId());
			System.out.println(score);
			scoreService.insertScore(score);

			// 操行分
			Quality quality = new Quality();
			quality.setAcademicYear("2017-2018");
			quality.setStudentId(student.getStudentId());
			qualityService.insertQuality(quality);

		}
		return JSON.toJSONString(studentInfoJSONArray);
	}
	
	@RequestMapping("getJpg")
	public void getJpg(HttpServletRequest request,HttpServletResponse response)  {
		System.out.println("????");
		InputStream inputStream = null;
		OutputStream writer = null;
		String filename = "E:\\11.jpg";
		try {
			inputStream = new FileInputStream(new File(filename));
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
