package com.cqjtu.controller.audit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cqjtu.annotation.ControllerLog;
import com.cqjtu.mapper.QualityItemMapper;
import com.cqjtu.model.Quality;
import com.cqjtu.model.QualityItem;
import com.cqjtu.service.AuditService;
import com.cqjtu.service.QualityService;

@Controller
@RequestMapping("audit")
public class AuditController {
	@Autowired
	AuditService auditService;
	@Autowired
	QualityService qualityService;
	@Autowired
	QualityItemMapper qualityItemMapper; 
	
	@ControllerLog(description="项目审核")
	@ResponseBody
	@RequestMapping("auditItem")
	public String auditItem(HttpServletRequest request,@RequestParam("itemId") Integer itemId,@RequestParam("itemStatus") String itemStatus) {
		if(auditService.auditItem(itemId, itemStatus)) {
			QualityItem qualityItem = qualityItemMapper.selectByPrimaryKey(itemId);
			qualityService.updateQualityScore(qualityItem.getQualityId());
			return "SUCCESS";
		}else {
			return "ERROR";
		}
	}
}
