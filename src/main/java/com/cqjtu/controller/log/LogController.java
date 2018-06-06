package com.cqjtu.controller.log;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqjtu.service.LogService;
import com.cqjtu.util.ParamUtil;

@Controller
@RequestMapping("log")
public class LogController {

	@Autowired
	LogService logService;

	/**
	 * 日志管理页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("logManager")
	public String logManager(HttpServletRequest request) {
		return "teacher/logManager";
	}

	/**
	 * 获取类型
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getType")
	public JSONArray getType(HttpServletRequest request) {
		List<String> types = logService.getType();
		JSONArray jsonArray = new JSONArray();
		for (String ss : types) {
			Map<String, Object> map = ParamUtil.getParamMap();
			map.put("type", ss);
			jsonArray.add(map);
		}
		return jsonArray;
	}

	/**
	 * 获取标题
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getTitle")
	public JSONArray getTitle(HttpServletRequest request) {
		List<String> titles = logService.getTitle();
		JSONArray jsonArray = new JSONArray();
		for (String ss : titles) {
			Map<String, Object> map = ParamUtil.getParamMap();
			map.put("title", ss);
			jsonArray.add(map);
		}
		return jsonArray;
	}

	/**
	 * 日志查询
	 * 
	 * @param request
	 * @param key
	 * @param type
	 * @param title
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchLog")
	public JSONObject searchLog(HttpServletRequest request, @RequestParam("key") String key,
			@RequestParam("type") String type, @RequestParam("title") String title, @RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, @RequestParam("sort") String sort, @RequestParam("order") String order,
			@RequestParam("beginDate") String beginDate, @RequestParam("timeout") String timeout) {
		Map<String, Object> param = ParamUtil.getParamMap();
		param.put("key", key);
		param.put("type", type);
		param.put("title", title);
		param.put("page", page);
		param.put("rows", rows);
		param.put("sort", sort);
		param.put("order", order);
		param.put("beginDate", beginDate);
		param.put("timeout", timeout);
		List<Map<String, Object>> list = logService.searchLog(param);
		Integer total = logService.geTotal(param);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", total);
		jsonObject.put("rows", list);
		return jsonObject;
	}
}
