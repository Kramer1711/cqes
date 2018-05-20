package com.cqjtu.model;

import java.io.Serializable;

public class Function implements Serializable {
    private Integer functionId;

    private String functionName;

    private Integer functionPid;

	private String functionState;

    private String functionPath;
    
    private String parentState;

    private static final long serialVersionUID = 1L;

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName == null ? null : functionName.trim();
    }

    public Integer getFunctionPid() {
        return functionPid;
    }

    public void setFunctionPid(Integer functionPid) {
        this.functionPid = functionPid;
    }

    public String getFunctionState() {
        return functionState;
    }

    public void setFunctionState(String functionState) {
        this.functionState = functionState == null ? null : functionState.trim();
    }

    public String getFunctionPath() {
        return functionPath;
    }

    public void setFunctionPath(String functionPath) {
        this.functionPath = functionPath == null ? null : functionPath.trim();
    }
    
    public String getParentState() {
		return parentState;
	}

	public void setParentState(String parentState) {
		this.parentState = parentState;
	}

}