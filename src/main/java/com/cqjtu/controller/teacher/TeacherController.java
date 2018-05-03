package com.cqjtu.controller.teacher;

import java.io.File;
import java.io.FileInputStream;

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
import com.cqjtu.model.Score;
import com.cqjtu.model.Student;
import com.cqjtu.service.AccountService;
import com.cqjtu.service.QualityService;
import com.cqjtu.service.ScoreService;
import com.cqjtu.service.StudentInfoService;
import com.cqjtu.service.StudentService;

@Controller
@RequestMapping("teacher")
public class TeacherController {
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

	@RequestMapping("uploadScorePage")
	public String indexPage() {
		return "/teacher/uploadScore";
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping(value = "uploadScore", method = RequestMethod.POST)
	public String uploadtest(HttpServletRequest request, @RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) throws Exception {

		System.out.println("upload score...");

		File xlsfile = null;
		xlsfile = File.createTempFile("E://tmp", null);
		file.transferTo(xlsfile);
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(xlsfile));
		HSSFSheet sheet = null;
		JSONObject xlsInfoJSONObject = new JSONObject();//excel信息
		JSONArray studentInfoJSONArray = new JSONArray();// 存表
		JSONObject rowName = new JSONObject();// 存储列名
		JSONObject excelInfo = new JSONObject();//excel表名、时间等信息

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
							if(k == 0) {//报送单位
								excelInfo.put("SubmittedUnit", row.getCell(k).toString());
							}
							if(k == 1) {//时间
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
		
		//上传成绩
		for (int i = 0; i < studentInfoJSONArray.size(); i++) {
			JSONObject jsonObject = studentInfoJSONArray.getJSONObject(i);
			
			Score score = new Score();
			double gpa = Double.parseDouble(jsonObject.getString("学年平均学分绩点"));
			score.setStudentId(Long.parseLong(jsonObject.getString("学号")));
			score.setGpa(gpa);
			score.setAveScore((gpa+5)*10);
			score.setAcademicYear("2017-2018");
			//更新成绩信息至数据库
			scoreService.uploadScore(score);
		}
		return JSON.toJSONString(studentInfoJSONArray);
	}

}
