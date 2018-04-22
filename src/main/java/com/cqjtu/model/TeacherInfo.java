package com.cqjtu.model;

import java.io.Serializable;
import java.util.Date;

public class TeacherInfo implements Serializable {
    private Integer teacherInfoId;

    private String teacherName;

    private String teacherSex;

    private Date teacherBirthday;

    private Integer teacherPhonenumber;

    private String other;

    private static final long serialVersionUID = 1L;

    public Integer getTeacherInfoId() {
        return teacherInfoId;
    }

    public void setTeacherInfoId(Integer teacherInfoId) {
        this.teacherInfoId = teacherInfoId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName == null ? null : teacherName.trim();
    }

    public String getTeacherSex() {
        return teacherSex;
    }

    public void setTeacherSex(String teacherSex) {
        this.teacherSex = teacherSex == null ? null : teacherSex.trim();
    }

    public Date getTeacherBirthday() {
        return teacherBirthday;
    }

    public void setTeacherBirthday(Date teacherBirthday) {
        this.teacherBirthday = teacherBirthday;
    }

    public Integer getTeacherPhonenumber() {
        return teacherPhonenumber;
    }

    public void setTeacherPhonenumber(Integer teacherPhonenumber) {
        this.teacherPhonenumber = teacherPhonenumber;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }
}