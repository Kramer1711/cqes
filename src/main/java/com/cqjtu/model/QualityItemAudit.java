package com.cqjtu.model;

import java.io.Serializable;

public class QualityItemAudit implements Serializable {
	private Integer auditId;

	private Integer qualityItemId;

	private String itemStatus;

	private Integer auditorId;
	
	private String itemRemark;

	private static final long serialVersionUID = 1L;

	public Integer getAuditId() {
		return auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public Integer getQualityItemId() {
		return qualityItemId;
	}

	public void setQualityItemId(Integer qualityItemId) {
		this.qualityItemId = qualityItemId;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus == null ? null : itemStatus.trim();
	}

	public String getItemRemark() {
		return itemRemark;
	}

	public void setItemRemark(String itemRemark) {
		this.itemRemark = itemRemark == null ? null : itemRemark.trim();
	}

	public Integer getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}
}