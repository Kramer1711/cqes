package com.cqjtu.model;

import java.io.Serializable;

public class Role implements Serializable {
    private Integer roleId;

    private String roleName;

    private String roleLevel;

    private String functionIds;

    private static final long serialVersionUID = 1L;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(String roleLevel) {
        this.roleLevel = roleLevel == null ? null : roleLevel.trim();
    }

    public String getFunctionIds() {
        return functionIds;
    }

    public void setFunctionIds(String functionIds) {
        this.functionIds = functionIds == null ? null : functionIds.trim();
    }
}