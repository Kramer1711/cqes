package com.cqjtu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqjtu.mapper.ItemTypeMapper;
import com.cqjtu.mapper.QualityItemAuditMapper;
import com.cqjtu.mapper.QualityItemMapper;
import com.cqjtu.mapper.QualityMapper;
import com.cqjtu.model.Quality;
import com.cqjtu.model.QualityItem;
import com.cqjtu.model.QualityItemAudit;
import com.cqjtu.service.QualityService;

@Service
public class QualityServiceImpl implements QualityService {
	@Autowired
	QualityMapper qualityMapper;
	@Autowired
	ItemTypeMapper itemTypeMapper;
	@Autowired
	QualityItemMapper qualityItemMapper;
	@Autowired
	QualityItemAuditMapper qualityItemAuditMapper;

	@Override
	public int insertQuality(Quality quality) {
		return qualityMapper.insert(quality);
	}

	@Override
	public List<Map<String, Object>> getTypeList() {
		return itemTypeMapper.getList();
	}

	@Override
	public void uploadQualityItem(Map<String, Object> param) {
		Quality quality = qualityMapper.selectByStudentId(param);
		QualityItem qualityItem = new QualityItem();
		qualityItem.setQualityId(quality.getQualityId());
		qualityItem.setItemName((String) param.get("itemName"));
		qualityItem.setQualityTypeId(Integer.parseInt((String) param.get("itemType")));
		qualityItem.setItemScore(Integer.parseInt((String) param.get("itemScore")));
		qualityItem.setItemEvidenceUrl((String) param.get("filepath"));
		qualityItemMapper.insert(qualityItem);
		QualityItemAudit audit = new QualityItemAudit();
		audit.setQualityItemId(qualityItem.getQualityTiemId());
		audit.setItemStatus("待审核");
		qualityItemAuditMapper.insert(audit);
	}

	@Override
	public void updateQualityItemById(Map<String, Object> param) {
		QualityItem qualityItem = qualityItemMapper
				.selectByPrimaryKey(Integer.parseInt((String) param.get("qualityItemId")));
		String filepath = qualityItem.getItemEvidenceUrl();
		qualityItem.setItemName((String) param.get("itemName"));
		qualityItem.setItemScore(Integer.parseInt((String) param.get("itemScore")));
		qualityItem.setQualityTypeId(Integer.parseInt((String) param.get("typeId")));
		String newFilepath = (String) param.get("filepath");
		if(newFilepath != null) {
			qualityItem.setItemEvidenceUrl(newFilepath);
		}
		qualityItemMapper.updateByPrimaryKey(qualityItem);
	}

	@Transactional
	@Override
	public boolean deleteQualityItem(Integer qualityItemId) {
		int result1 = qualityItemAuditMapper.deleteByQualityItemId(qualityItemId);
		int result2 = qualityItemMapper.deleteByPrimaryKey(qualityItemId);
		System.err.println(result1 +"\t"+result2);
		if(result1 == 1 && result2 == 1) {
			return true;
		}else {
			return false;
		}
	}

}
