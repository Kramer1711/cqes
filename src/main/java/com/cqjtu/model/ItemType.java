package com.cqjtu.model;

import java.io.Serializable;

public class ItemType implements Serializable {
    private Integer itemTypeId;

    private String typeName;

    private static final long serialVersionUID = 1L;

    public Integer getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Integer itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }
}