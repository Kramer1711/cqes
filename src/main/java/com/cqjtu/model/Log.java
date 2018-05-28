package com.cqjtu.model;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {
    private String logId;

    private Integer operatorId;

    private String type;

    private String title;

    private String remoteAddr;

    private String requestUri;

    private String method;

    private Date beginDate;

    private Date timeout;

    private static final long serialVersionUID = 1L;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId == null ? null : logId.trim();
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr == null ? null : remoteAddr.trim();
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri == null ? null : requestUri.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getTimeout() {
        return timeout;
    }

    public void setTimeout(Date timeout) {
        this.timeout = timeout;
    }

	@Override
	public String toString() {
		return "Log [logId=" + logId + ", operatorId=" + operatorId + ", type=" + type + ", title=" + title
				+ ", remoteAddr=" + remoteAddr + ", requestUri=" + requestUri + ", method=" + method + ", beginDate="
				+ beginDate + ", timeout=" + timeout + "]";
	}
    
}