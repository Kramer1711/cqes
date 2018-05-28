package com.cqjtu.controller.teacher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.alibaba.fastjson.serializer.FilterUtils;
import com.cqjtu.annotation.SystemControllerLog;
import com.cqjtu.mapper.CollegeMapper;
import com.cqjtu.mapper.MajorMapper;
import com.cqjtu.model.Account;
import com.cqjtu.model.Score;
import com.cqjtu.service.AcademicYearService;
import com.cqjtu.service.AccountService;
import com.cqjtu.service.QualityService;
import com.cqjtu.service.ScoreService;
import com.cqjtu.service.StudentInfoService;
import com.cqjtu.service.StudentService;
import com.cqjtu.util.ParamUtil;

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
	@Autowired
	CollegeMapper collegeMapper;
	@Autowired
	MajorMapper majorMapper;
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
	@SystemControllerLog(description="上传成绩")
	@ResponseBody
	@RequestMapping(value = "uploadScore", method = RequestMethod.POST)
	public JSONObject uploadtest(HttpServletRequest request, @RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) throws IOException {
		System.out.println("---------------------URL: uploadScore");
		JSONObject resultJSON = new JSONObject();// 存储返回信息
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
		} catch (Exception e) {
			e.printStackTrace();
			resultJSON.put("result", "ERROR");
		}
		resultJSON.put("successNumber", successNumber);
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

	/**
	 * 素质操行分表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("searchQualityScorePage")
	public String searchQualityScorePage(HttpServletRequest request) {
		return "teacher/searchQualityScorePage";
	}

	/**
	 * 素质操行分搜索
	 * 
	 * @param request
	 * @param key
	 * @param collegeId
	 * @param majorId
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchQualityScore")
	public String searchQualityScore(HttpServletRequest request, @RequestParam("key") String key,
			@RequestParam("collegeId") Integer collegeId, @RequestParam("majorId") Integer majorId,
			@RequestParam("page") Integer page, @RequestParam("rows") Integer rows, @RequestParam("sort") String sort,
			@RequestParam("order") String order, @RequestParam("grade") Integer grade) {
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
		param.put("grade", grade);
		System.out.println(param.toString());
		// 分页结果集
		List<Map<String, Object>> result = qualityService.searchQualityScore(param);
		int total = qualityService.getTotalQualityScore(param);
		// 转化为json
		JSONObject resultJson = new JSONObject();
		resultJson.put("total", total);
		resultJson.put("rows", result);
		// 返回到页面
		return resultJson.toJSONString();
	}

	/**
	 * 学生素质操行分表导出
	 * 
	 * @param request
	 * @param key
	 * @param majorId
	 * @param collegeId
	 * @return
	 * @throws FileNotFoundException
	 */
	@SystemControllerLog(description="Excel导出")
	@RequestMapping("exportExcel")
	public ResponseEntity<byte[]> exportExcel(HttpServletRequest request, @RequestParam("key") String key,
			@RequestParam("majorId") Integer majorId, @RequestParam("collegeId") Integer collegeId,
			@RequestParam("type") String type, @RequestParam("grade") Integer grade) {
		// 参数
		Map<String, Object> param = ParamUtil.getParamMap();
		System.out.println(key + " " + majorId + " " + collegeId + " " + type);
		param.put("key", key);
		param.put("majorId", majorId);
		param.put("collegeId", collegeId);
		param.put("type", type);
		param.put("grade", grade);
		param.put("templateFilePath", request.getServletContext().getRealPath("") + "\\WEB-INF\\classes\\template\\");
		String academicYear = academicYearService.getDoingYear();
		param.put("academicYear", academicYearService.getDoingYear());
		HttpHeaders headers = new HttpHeaders();
		// 文件名
		String collegeStr = collegeId == null ? "" : collegeMapper.selectByPrimaryKey(collegeId).getCollegeName();
		String majorStr = majorId == null ? "" : majorMapper.selectByPrimaryKey(majorId).getMajorName() + "专业";
		String gradeStr = grade == null ? "" : grade + "级";
		String downloadFileName = academicYear + "学年" + collegeStr + gradeStr + majorStr + "学生";
		if (type.equals("qualityScore"))
			downloadFileName += "素质操行分登记汇总表.xls";
		else
			downloadFileName += "综合素质测评排名表.xls";
		param.put("fileName", downloadFileName);
		try {
			downloadFileName = new String(downloadFileName.getBytes("UTF-8"), "iso-8859-1");
			// 二进制字节流
			byte[] bytes = qualityService.exportExcel(param);
			headers.setContentDispositionFormData("attachment", downloadFileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 综合素质分测评排名表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("comprehensiveQualityPage")
	public String comprehensiveQualityPage(HttpServletRequest request) {
		return "teacher/comprehensiveQualityPage";
	}

	/**
	 * 
	 * @param request
	 * @param key
	 * @param collegeId
	 * @param majorId
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchComprehensiveQualityScore")
	public String searchComprehensiveQualityScore(HttpServletRequest request, @RequestParam("key") String key,
			@RequestParam("collegeId") Integer collegeId, @RequestParam("majorId") Integer majorId,
			@RequestParam("page") Integer page, @RequestParam("rows") Integer rows,
			@RequestParam("grade") Integer grade) {
		System.out.println("---------------------URL: searchStudent");
		// 组装参数
		Map<String, Object> param = new HashMap<>();
		param.put("key", key);
		param.put("collegeId", collegeId);
		param.put("majorId", majorId);
		param.put("page", page);
		param.put("rows", rows);
		param.put("grade", grade);
		System.out.println(param.toString());
		// 分页结果集
		List<Map<String, Object>> result = qualityService.searchComprehensiveQualityScore(param);
		int total = studentService.getTotal(param);
		// 转化为json
		JSONObject resultJson = new JSONObject();
		resultJson.put("total", total);
		resultJson.put("rows", result);
		// 返回到页面
		return resultJson.toJSONString();
	}
}
