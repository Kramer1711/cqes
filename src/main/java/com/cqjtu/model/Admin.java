package com.cqjtu.model;

import java.io.Serializable;

public class Admin implements Serializable {
    private Integer adminId;

    private Integer adminInfoId;

    private Integer accountId;

    private static final long serialVersionUID = 1L;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getAdminInfoId() {
        return adminInfoId;
    }

    public void setAdminInfoId(Integer adminInfoId) {
        this.adminInfoId = adminInfoId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}