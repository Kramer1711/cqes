package com.cqjtu.service;

import com.cqjtu.model.Log;
import com.cqjtu.model.LogWithBLOBs;

public interface LogService {
	/**
	 * 创建日志
	 * @param log
	 */
	void createLog(LogWithBLOBs log);
	/**
	 * 更新日志
	 * @param log
	 */
	void updateLog(LogWithBLOBs log);
	

}
