package com.cqjtu.model;

import java.io.Serializable;

public class StudentInfo implements Serializable {
    private Integer studentInfoId;

    private String studentName;

    private String studentSex;

    private String studentBirthday;

    private String other;

    private static final long serialVersionUID = 1L;

    public Integer getStudentInfoId() {
        return studentInfoId;
    }

    public void setStudentInfoId(Integer studentInfoId) {
        this.studentInfoId = studentInfoId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
    }

    public String getStudentSex() {
        return studentSex;
    }

    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex == null ? null : studentSex.trim();
    }

    public String getStudentBirthday() {
        return studentBirthday;
    }

    public void setStudentBirthday(String studentBirthday) {
        this.studentBirthday = studentBirthday == null ? null : studentBirthday.trim();
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }
}