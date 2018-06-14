package com.cqjtu.controller.student;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqjtu.model.Account;
import com.cqjtu.service.AcademicYearService;
import com.cqjtu.service.QualityService;
import com.cqjtu.service.StudentService;

@Controller
@RequestMapping("student")
public class StudentController {

	@Autowired
	QualityService qualityService;
	@Autowired
	StudentService studentService;
	@Autowired
	AcademicYearService academicYearService;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	private static String savePath = System.getProperty("user.home") + "/evidence";

	
	private Long getStudentIdFromSession(HttpServletRequest request) {
		Account account = (Account) request.getSession().getAttribute("account");
		String studentId = account.getAccountName();
		return Long.parseLong(studentId);
	}
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	@RequestMapping("uploadQualityPage")
	public String uploadQualityPage(HttpServletRequest request) {
		return "student/uploadQualityPage";
	}

	/**
	 * 上传学生综合素质测评表
	 * 
	 * @param request
	 * @param itemType
	 *            类型
	 * @param itemName
	 *            项目
	 * @param itemScore
	 *            分数
	 * @param fb
	 *            证明材料
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("uploadQuality")
	public String uploadQuality(HttpServletRequest request, @RequestParam("itemType") String itemType[],
			@RequestParam("itemName") String itemName[], @RequestParam("itemScore") String itemScore[],
			@RequestParam("fb") MultipartFile[] fb) throws IllegalStateException, IOException {
		try {
			Map<String, Object> param = new HashMap<>();// 存储参数
			for (int i = 0; i < itemType.length; i++) {// 遍历综合素质测评项目
				String filepath = null;// 证明材料存储路径
				System.out.print(
						"itemType:" + itemType[i] + "\titemName" + itemName[i] + "\titemScore:" + itemScore[i] + "\n");
				if (!fb[i].isEmpty()) {
					// 上传文件名
					String filename = makeFileName(fb[i].getOriginalFilename());
					File file = new File(makePath(filename, savePath), filename);
					filepath = file.getPath();
					// 判断路径是否存在，如果不存在就创建一个
					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
					// 将上传文件保存到一个目标文件当中
					fb[i].transferTo(file);
					param.put("filepath", filepath.replace('\\', '/'));
				}
				// 组装参数
				param.put("studentId", getStudentIdFromSession(request));
				param.put("academicYear", academicYearService.getDoingYear());
				param.put("itemType", itemType[i]);
				param.put("itemName", itemName[i]);
				param.put("itemScore", itemScore[i]);
				System.out.println(param.toString());
				qualityService.uploadQualityItem(param);
			}
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}

	private String makeFileName(String filename) { // 2.jpg
		// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return UUID.randomUUID().toString() + "_" + filename;
	}

	private String makePath(String filename, String savePath) {
		// 得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
		int hashcode = filename.hashCode();
		int dir1 = hashcode & 0xf; // 0--15
		int dir2 = (hashcode & 0xf0) >> 4; // 0-15
		// 构造新的保存目录
		String dir = savePath + "\\" + dir1 + "\\" + dir2; // upload\2\3
															// upload\3\5
		// File既可以代表文件也可以代表目录
		File file = new File(dir);
		// 如果目录不存在
		if (!file.exists()) {
			// 创建目录
			file.mkdirs();
		}
		return dir;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("updateQualityPage")
	public String updataQualityPage(HttpServletRequest request) {
		return "student/updateQualityPage";
	}

	@ResponseBody
	@RequestMapping("searchAudit")
	public String searchAudit(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		param.put("studentId", getStudentIdFromSession(request));
		param.put("academicYear", academicYearService.getDoingYear());
		List<Map<String, Object>> result = studentService.searchAuditDetailOfStudent(param);
		System.out.println(JSONArray.toJSONString(result));
		return JSONArray.toJSONString(result);
	}

	@ResponseBody
	@RequestMapping("updateQualityItem")
	public String updateQualityItem(HttpServletRequest request, @RequestParam("qualityItemId") String qualityItemId,
			@RequestParam("typeId") String typeId, @RequestParam("itemName") String itemName,
			@RequestParam("itemScore") String itemScore, @RequestParam("fb") MultipartFile fb)
			throws IllegalStateException, IOException {
		System.out.println(savePath);
		try {
			Map<String, Object> param = new HashMap<>();// 存储参数
			String filepath = null;// 证明材料存储路径
			System.out.print("qualityItemId:" + qualityItemId + "\ttypeId:" + typeId + "\titemName:" + itemName
					+ "\t\titemScore:" + itemScore + "\n");
			if (!fb.isEmpty()) {
				System.out.println("----------上传文件------------");
				// 上传文件名
				String filename = makeFileName(fb.getOriginalFilename());
				File file = new File(makePath(filename, savePath), filename);
				filepath = file.getPath();
				// 判断路径是否存在，如果不存在就创建一个
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				// 将上传文件保存到一个目标文件当中
				fb.transferTo(file);
				param.put("filepath", filepath.replace('\\', '/'));
			} else {
				param.put("filepath", null);
			}
			// 组装参数
			param.put("qualityItemId", qualityItemId);
			param.put("typeId", typeId);
			param.put("itemName", itemName);
			param.put("itemScore", itemScore);
			System.out.println(param.toString());
			qualityService.updateQualityItemById(param);
			return "SUCCESS";
		} catch (Exception e) {
			return "ERROR";
		}
	}

	@ResponseBody
	@RequestMapping("deleteQualityItem")
	public String deleteQualityItem(HttpServletRequest request, @RequestParam("deleteId") String deleteId) {
		Integer qualityItemId = Integer.parseInt(deleteId);
		System.out.println(qualityItemId);
		boolean result = qualityService.deleteQualityItem(qualityItemId);
		if (result) {
			return "SUCCESS";
		} else {
			return "ERROR";
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getGrades")
	public String getGrades(HttpServletRequest request) {
		List<String> list = studentService.getGrades();
		JSONArray result = new JSONArray();
		for (String s : list) {
			JSONObject obj = new JSONObject();
			obj.put("id", s);
			obj.put("grade", s);
			result.add(obj);
		}
		return JSONArray.toJSONString(result);
	}
}
