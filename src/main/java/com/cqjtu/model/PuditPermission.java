package com.cqjtu.model;

import java.io.Serializable;

public class PuditPermission implements Serializable {
    private Integer auditPermissionId;

    private Integer auditorId;

    private Integer collegeId;

    private Integer majorId;

    private static final long serialVersionUID = 1L;

    public Integer getAuditPermissionId() {
        return auditPermissionId;
    }

    public void setAuditPermissionId(Integer auditPermissionId) {
        this.auditPermissionId = auditPermissionId;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }
}