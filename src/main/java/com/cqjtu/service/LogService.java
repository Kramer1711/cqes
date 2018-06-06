package com.cqjtu.service;

import java.util.List;
import java.util.Map;

import com.cqjtu.model.Log;
import com.cqjtu.model.LogWithBLOBs;

public interface LogService {
	/**
	 * 创建日志
	 * 
	 * @param log
	 */
	void createLog(LogWithBLOBs log);

	/**
	 * 更新日志
	 * 
	 * @param log
	 */
	void updateLog(LogWithBLOBs log);

	/**
	 * 获取标题
	 * 
	 * @return
	 */
	List<String> getTitle();

	/**
	 * 获取类型
	 * 
	 * @return
	 */
	List<String> getType();

	/**
	 * 日志查询
	 * 
	 * @param param
	 * @return
	 */
	List<Map<String,Object>> searchLog(Map<String, Object> param);
	/**
	 * 获取日志查询的总数
	 * @param param
	 * @return
	 */
	Integer geTotal(Map<String, Object> param);

}
