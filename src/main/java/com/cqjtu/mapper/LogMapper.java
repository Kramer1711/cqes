package com.cqjtu.mapper;

import java.util.List;
import java.util.Map;

import com.cqjtu.model.Log;
import com.cqjtu.model.LogWithBLOBs;

public interface LogMapper {
	int deleteByPrimaryKey(String logId);

	int insert(LogWithBLOBs record);

	int insertSelective(LogWithBLOBs record);

	LogWithBLOBs selectByPrimaryKey(String logId);

	int updateByPrimaryKeySelective(LogWithBLOBs record);

	int updateByPrimaryKeyWithBLOBs(LogWithBLOBs record);

	int updateByPrimaryKey(Log record);

	/**
	 * 获取类型
	 * 
	 * @return
	 */
	List<String> getTypes();

	/**
	 * 获取标题
	 * 
	 * @return
	 */
	List<String> getTitles();

	/**
	 * 日志查询
	 * 
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> search(Map<String, Object> param);

	/**
	 * 获取总数
	 * 
	 * @param param
	 * @return
	 */
	Integer getTotal(Map<String, Object> param);
}