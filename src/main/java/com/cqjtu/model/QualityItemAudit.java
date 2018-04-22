package com.cqjtu.model;

import java.io.Serializable;

public class QualityItemAudit implements Serializable {
    private Integer auditId;

    private Integer qualityItemId;

    private String auditRemark;

    private Integer auditorId;

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

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark == null ? null : auditRemark.trim();
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }
}