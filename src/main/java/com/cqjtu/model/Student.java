package com.cqjtu.model;

import java.io.Serializable;

public class Student implements Serializable {
    private Integer studentId;

    private Integer accountId;

    private Integer scoreId;

    private Integer studentInfoId;

    private Integer qualityId;

    private static final long serialVersionUID = 1L;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public Integer getStudentInfoId() {
        return studentInfoId;
    }

    public void setStudentInfoId(Integer studentInfoId) {
        this.studentInfoId = studentInfoId;
    }

    public Integer getQualityId() {
        return qualityId;
    }

    public void setQualityId(Integer qualityId) {
        this.qualityId = qualityId;
    }
}