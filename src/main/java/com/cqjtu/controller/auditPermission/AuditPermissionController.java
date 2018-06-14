package com.cqjtu.controller.auditPermission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.MapComparator;
import com.alibaba.fastjson.JSONObject;
import com.cqjtu.annotation.ControllerLog;
import com.cqjtu.mapper.AuditPermissionMapper;
import com.cqjtu.model.Account;
import com.cqjtu.model.AuditPermission;
import com.cqjtu.service.AccountService;
import com.cqjtu.service.AuditPermissionService;
import com.cqjtu.util.ParamUtil;

@Controller
@RequestMapping("auditPermission")
public class AuditPermissionController {

	@Autowired
	AccountService accountService;
	@Autowired
	AuditPermissionService auditPermissionService;

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
	 * 获取代理权限信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getPermission")
	public JSONObject getPermission(HttpServletRequest request) {
		Account account = (Account) request.getSession().getAttribute("account");
		Map<String, Object> result = auditPermissionService.getAccountAndPermission(account);
		JSONObject resultJSON = (JSONObject) JSONObject.toJSON(result);
		return resultJSON;
	}

	/**
	 * 搜索审核代理人
	 * 
	 * @param request
	 * @param key
	 * @param collegeId
	 * @param majorId
	 * @param status
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchAgentAudit")
	public JSONObject searchAgentAudit(HttpServletRequest request, @RequestParam("key") String key,
			@RequestParam("collegeId") Integer collegeId, @RequestParam("majorId") Integer majorId,
			@RequestParam("status") Integer status, @RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, @RequestParam("sort") String sort,
			@RequestParam("order") String order) {
		// 组装参数
		Map<String, Object> param = ParamUtil.getParamMap();
		param.put("key", key);
		param.put("collegeId", collegeId);
		param.put("majorId", majorId);
		param.put("status", status);
		param.put("page", page);
		param.put("rows", rows);
		param.put("sort", sort);
		param.put("order", order);
		System.out.println(param.toString());
		// 查询总数
		int total = auditPermissionService.getTotal(param);
		// 查询分页记录
		List<Map<String, Object>> resultList = auditPermissionService.searchAgentAudit(param);
		// 组装json
		JSONObject result = new JSONObject();
		result.put("total", total);
		result.put("rows", resultList);
		// 返回数据
		return result;
	}

	/**
	 * 
	 * 修改代理审核人状态
	 * 
	 * @param request
	 * @param auditPermissionId
	 * @param newStatus
	 * @return
	 */

	@ControllerLog(description = "代理审核人状态变更")
	@ResponseBody
	@RequestMapping("changeStatus")
	public boolean changeStatus(HttpServletRequest request,
			@RequestParam("auditPermissionId") Integer auditPermissionId, @RequestParam("status") Integer newStatus) {
		return auditPermissionService.changeStatus(auditPermissionId, newStatus);
	}

	/**
	 * 添加代理审核人
	 * 
	 * 
	 * @param request
	 * @param studentId
	 *            学生id
	 * @param collegeId
	 *            学院id
	 * @param majorId
	 *            专业id
	 * @param newStatus
	 *            状态
	 * @return
	 */
	@ControllerLog(description = "添加代理审核人状态")
	@ResponseBody
	@RequestMapping("addAuditPermission")
	public boolean addAuditPermission(HttpServletRequest request, @RequestParam("studentId") String studentId,
			@RequestParam("collegeId") Integer collegeId, @RequestParam("majorId") Integer majorId,
			@RequestParam("status") Integer status) {
		try {
			// 获取accountId
			Account account = accountService.getAccount(studentId);
			// 组装参数
			Map<String, Object> param = ParamUtil.getParamMap();
			param.put("auditorId", account.getAccountId());
			param.put("collegeId", collegeId);
			param.put("majorId", majorId);
			param.put("status", status);
			// 结果
			boolean result = auditPermissionService.addAuditPermission(param);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@ControllerLog(description = "删除代理审核人")
	@ResponseBody
	@RequestMapping("delete")
	public boolean addAuditPermission(HttpServletRequest request,
			@RequestParam("auditPermissionId") Integer auditPermissionId) {
		return auditPermissionService.delete(auditPermissionId);
	}
}
