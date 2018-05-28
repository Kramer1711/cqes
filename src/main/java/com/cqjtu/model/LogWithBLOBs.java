package com.cqjtu.model;

import java.io.Serializable;
import java.util.Map;

public class LogWithBLOBs extends Log implements Serializable {
	private String params;

	private String exception;

	private static final long serialVersionUID = 1L;

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params == null ? null : params.trim();
	}

	/**
	 * 设置请求参数
	 * 
	 * @param paramMap
	 */
	public void setMapToParams(Map<String, String[]> paramMap) {
		if (paramMap == null) {
			return;
		}
		StringBuilder params = new StringBuilder();
		for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap).entrySet()) {
			params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
			String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
			params.append(param.getKey().toLowerCase().endsWith("password") ? "" : paramValue);
		}
		this.params = params.toString();
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception == null ? null : exception.trim();
	}

	@Override
	public String toString() {
		String superString = super.toString();
		return superString.substring(0, superString.length() - 1) + ", params=" + params + ", exception=" + exception
				+ "]";
	}

}