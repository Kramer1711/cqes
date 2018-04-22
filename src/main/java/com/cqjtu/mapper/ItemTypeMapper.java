package com.cqjtu.mapper;

import com.cqjtu.model.ItemType;

public interface ItemTypeMapper {
    int deleteByPrimaryKey(Integer itemTypeId);

    int insert(ItemType record);

    int insertSelective(ItemType record);

    ItemType selectByPrimaryKey(Integer itemTypeId);

    int updateByPrimaryKeySelective(ItemType record);

    int updateByPrimaryKey(ItemType record);
}