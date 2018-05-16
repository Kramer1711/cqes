package com.cqjtu.mapper;

import java.util.List;
import java.util.Map;

import com.cqjtu.model.ItemType;

public interface ItemTypeMapper {
    int deleteByPrimaryKey(Integer itemTypeId);

    int insert(ItemType record);

    int insertSelective(ItemType record);

    ItemType selectByPrimaryKey(Integer itemTypeId);

    int updateByPrimaryKeySelective(ItemType record);

    int updateByPrimaryKey(ItemType record);
	/**
	 * 获取综合素质测评项目类型
	 * @return
	 */
	List<Map<String, Object>> getList();
}