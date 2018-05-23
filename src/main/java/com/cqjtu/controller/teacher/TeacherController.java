package com.cqjtu.controller.teacher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.cqjtu.model.Score;
import com.cqjtu.service.AcademicYearService;
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
	@Autowired
	AcademicYearService academicYearService;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	/**
	 * 上传成绩页面
	 * 
	 * @return
	 */
	@RequestMapping("uploadScorePage")
	public String indexPage() {
		System.out.println("---------------------URL: uploadScorePage");
		return "/teacher/uploadScore";
	}

	/**
	 * 上传成绩
	 * 
	 * @param request
	 * @param name
	 * @param file
	 *            文件
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@Transactional
	@ResponseBody
	@RequestMapping(value = "uploadScore", method = RequestMethod.POST)
	public JSONObject uploadtest(HttpServletRequest request, @RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) throws IOException {
		System.out.println("---------------------URL: uploadScore");
		JSONObject resultJSON = new JSONObject();//存储返回信息
		JSONObject xlsInfoJSONObject = new JSONObject();// excel信息
		JSONArray studentInfoJSONArray = new JSONArray();// 存表
		JSONObject rowName = new JSONObject();// 存储列名
		JSONObject excelInfo = new JSONObject();// excel表名、时间等信息
		File xlsfile = null;
		xlsfile = File.createTempFile("E://tmp", null);
		file.transferTo(xlsfile);
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(xlsfile));
		HSSFSheet sheet = null;
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
			sheet = workbook.getSheetAt(i);
			for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
				HSSFRow row = sheet.getRow(j);
				JSONObject cellJSON = new JSONObject();
				if (row != null) {
					for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
						if (j == 0) {
							excelInfo.put("SheetName", row.getCell(k).toString());
						} else if (j == 1) {// 列名
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
		int successNumber = 0;// 成功数量
		int totalNumber = studentInfoJSONArray.size();
		JSONArray errorArray = new JSONArray();// 错误的记录
		System.out.println(studentInfoJSONArray);
		try {
			// 上传成绩
			for (int i = 0; i < studentInfoJSONArray.size(); i++) {
				JSONObject jsonObject = studentInfoJSONArray.getJSONObject(i);
				Score score = new Score();
				String gpaStr = jsonObject.getString("学年平均学分绩点");
				String stuIdStr = jsonObject.getString("学号");
				double gpa = gpaStr == "" ? 0.0 : Double.parseDouble(gpaStr);
				score.setStudentId(Long.parseLong(stuIdStr));
				score.setGpa(gpa);
				score.setAveScore((gpa + 5) * 10);
				score.setAcademicYear("2017-2018");
				// 更新成绩信息至数据库
				if (scoreService.updateScore(score) < 1) {// 更新失败
					try {
						scoreService.insertScore(score);
						successNumber++;
					} catch (Exception e) {
						e.printStackTrace();
						errorArray.add(jsonObject);
					}
				} else {
					successNumber++;
				}
			}		
			resultJSON.put("result", "SUCCESS");
		}catch(Exception e) {
			e.printStackTrace();
			resultJSON.put("result", "ERROR");
		}resultJSON.put("successNumber", successNumber);
		resultJSON.put("totalNumber", totalNumber);
		resultJSON.put("errorArray", errorArray);
		return resultJSON;
	}

	/**
	 * 学生信息查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("studentInfo")
	public String studentInfo(HttpServletRequest request) {
		System.out.println("---------------------URL: studentInfo");
		return "teacher/studentInfo";
	}

	/**
	 * 审核查询系统 1.判断该角色是教师or代理审核人
	 * 
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("qualityAudit")
	public String qualitAudit(HttpServletRequest request) {
		System.out.println("---------------------URL: qualityAudit");
		Account account = (Account) this.session.getAttribute("account");
		return "teacher/qualityAudit";
	}

	/**
	 * 
	 * @param request
	 * @param key
	 *            关键词:学号或者姓名
	 * @param collegeId
	 *            学院
	 * @param majorId
	 *            专业
	 * @param status
	 *            审核情况
	 * @param page
	 *            页码
	 * @param rows
	 *            页大小
	 * @param sort
	 *            排序标识
	 * @param order
	 *            排序方式：升序/降序
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchAudit")
	public String searchQuality(HttpServletRequest request, @RequestParam("key") String key,
			@RequestParam("collegeId") Integer collegeId, @RequestParam("majorId") Integer majorId,
			@RequestParam("status") String status, @RequestParam("page") int page, @RequestParam("rows") int rows,
			@RequestParam("sort") String sort, @RequestParam("order") String order,
			@RequestParam("existSelf") boolean existSelf) {
		System.out.println("---------------------URL: searchAudit");
		Map<String, Object> param = new HashMap<>();
		param.put("key", key);
		param.put("collegeId", collegeId);
		param.put("majorId", majorId);
		param.put("status", status);
		param.put("academicYear", academicYearService.getDoingYear());
		param.put("page", page);
		param.put("rows", rows);
		param.put("sort", sort);
		param.put("order", order);
		if (existSelf) {
			Long studentId = Long.parseLong(((Account) request.getSession().getAttribute("account")).getAccountName());
			param.put("studentId", studentId);
		}
		System.out.println(param.toString());
		// 分页结果集
		List<Map<String, Object>> result = studentService.searchAudit(param);
		int total = studentService.getAuditTotal(param);
		// 转化为json
		JSONObject resultJson = new JSONObject();
		resultJson.put("total", total);
		resultJson.put("rows", result);
		// 返回到页面
		return resultJson.toJSONString();
	}

	/**
	 * 详细审核情况
	 * 
	 * @param request
	 * @param studentId
	 *            学号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchAuditOfStudent")
	public String searchAuditOfStudent(HttpServletRequest request, @RequestParam("studentId") String studentId) {
		System.out.println("---------------------URL: searchAuditOfStudent");
		Map<String, Object> param = new HashMap<>();
		param.put("studentId", studentId);
		param.put("academicYear", academicYearService.getDoingYear());
		List<Map<String, Object>> result = studentService.searchAuditDetailOfStudent(param);
		// 转化为json
		JSONArray resultJson = (JSONArray) JSONArray.toJSON(result);
		// 返回到页面
		return resultJson.toJSONString();
	}

	/**
	 * 代理审核页面 1.选择代理审核人
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("agentAuditManager")
	public String auditSystemPage(HttpServletRequest request) {
		return "teacher/agentAuditManager";
	}

	/**
	 * 搜索
	 * 
	 * @param request
	 * @param key
	 *            关键字：姓名或学号
	 * @param collegeId
	 *            学院
	 * @param majorId
	 *            专业
	 * @param page
	 *            页码
	 * @param rows
	 *            每页条数
	 * @param sort
	 *            排序标识
	 * @param order
	 *            排序方式：升序/降序
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchStudent")
	public String searchStudent(HttpServletRequest request, @RequestParam("key") String key,
			@RequestParam("collegeId") Integer collegeId, @RequestParam("majorId") Integer majorId,
			@RequestParam("page") int page, @RequestParam("rows") int rows, @RequestParam("sort") String sort,
			@RequestParam("order") String order) {
		System.out.println("---------------------URL: searchStudent");
		// 组装参数
		Map<String, Object> param = new HashMap<>();
		param.put("key", key);
		param.put("collegeId", collegeId);
		param.put("majorId", majorId);
		param.put("page", page);
		param.put("rows", rows);
		param.put("sort", sort);
		param.put("order", order);
		System.out.println(param.toString());
		// 分页结果集
		List<Map<String, Object>> result = studentService.searchStudent(param);
		int total = studentService.getTotal(param);
		// 转化为json
		JSONObject resultJson = new JSONObject();
		resultJson.put("total", total);
		resultJson.put("rows", result);
		// 返回到页面
		return resultJson.toJSONString();
	}

}
