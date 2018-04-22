package com.cqjtu.model;

import java.io.Serializable;

public class AdminInfo implements Serializable {
    private Integer adminInfoId;

    private String adminName;

    private String adminSex;

    private Integer adminPhonenumber;

    private String other;

    private static final long serialVersionUID = 1L;

    public Integer getAdminInfoId() {
        return adminInfoId;
    }

    public void setAdminInfoId(Integer adminInfoId) {
        this.adminInfoId = adminInfoId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getAdminSex() {
        return adminSex;
    }

    public void setAdminSex(String adminSex) {
        this.adminSex = adminSex == null ? null : adminSex.trim();
    }

    public Integer getAdminPhonenumber() {
        return adminPhonenumber;
    }

    public void setAdminPhonenumber(Integer adminPhonenumber) {
        this.adminPhonenumber = adminPhonenumber;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }
}