package com.cqjtu.controller.audit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cqjtu.service.AuditService;

@Controller
@RequestMapping("audit")
public class AuditController {
	@Autowired
	AuditService auditService;
	
	
	@ResponseBody
	@RequestMapping("auditItem")
	public String auditItem(HttpServletRequest request,@RequestParam("itemId") Integer itemId,@RequestParam("itemStatus") String itemStatus) {
		if(auditService.auditItem(itemId, itemStatus)) {
			return "SUCCESS";
		}else {
			return "ERROR";
		}
	}
}
