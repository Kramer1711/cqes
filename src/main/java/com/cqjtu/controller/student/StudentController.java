package com.cqjtu.controller.student;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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

import com.cqjtu.service.QualityService;

@Controller
@RequestMapping("student")
public class StudentController {
	
	@Autowired
	QualityService qualityService;
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	private static String savePath = "E://temp";

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

	@ResponseBody
	@RequestMapping("uploadQuality")
	public String uploadQuality(HttpServletRequest request, @RequestParam("itemType") String itemType[],
			@RequestParam("itemName") String itemName[], @RequestParam("itemScore") String itemScore[],
			@RequestParam("fb") MultipartFile[] fb) throws IllegalStateException, IOException {
		Map<String,Object> param = new HashMap<>();//存储参数
		for (int i = 0; i < fb.length; i++) {//遍历综合素质测评项目
			String filepath = null;//证明材料存储路径
			System.out.print("itemType:" + itemType[i] + "\titemName" + itemName[i] + "\titemScore:" + itemScore[i] + "\n");
			if (!fb[i].isEmpty()) {
				// 上传文件名
				String filename = makeFileName(fb[i].getOriginalFilename());
				File file = new File(makePath(filename, savePath),filename);
				filepath = file.getPath();
				// 判断路径是否存在，如果不存在就创建一个
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				// 将上传文件保存到一个目标文件当中
				fb[i].transferTo(file);
			}
			//组装参数
			param.put("studentId", Long.parseLong("631406010210"));
			param.put("academicYear", "2017-2018");
			/**
			 * 
			 */
			param.put("itemType", itemType[i]);
			param.put("itemName", itemName[i]);
			param.put("itemScore", itemScore[i]);
			param.put("filepath", filepath);
			System.out.println(param.toString());
			qualityService.uploadQualityItem(param);
		}
		return "";
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
}
