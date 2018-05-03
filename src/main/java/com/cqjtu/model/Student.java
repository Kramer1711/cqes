package com.cqjtu.model;

import java.io.Serializable;

public class Student implements Serializable {
    private Long studentId;

    private Integer accountId;

    private static final long serialVersionUID = 1L;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}