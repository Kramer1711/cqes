package com.cqjtu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqjtu.mapper.QualityItemAuditMapper;
import com.cqjtu.service.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

	@Autowired
	QualityItemAuditMapper qualityItemAuditMapper; 
	
	@Override
	public boolean auditItem(Integer itemId, String itemStatus) {
		int result = qualityItemAuditMapper.updateByQualityItemId(itemId,itemStatus);
		System.out.println(result);
		if(result == 1) {
			return true;
		}else {
			return false;
		}
	}

}
