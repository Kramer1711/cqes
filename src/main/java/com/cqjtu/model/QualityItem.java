package com.cqjtu.model;

import java.io.Serializable;

public class QualityItem implements Serializable {
    private Integer qualityTiemId;

    private Integer qualityId;

    private Integer qualityTypeId;

    private String itemName;

    private Integer itemScore;

    private String itemEvidenceUrl;

    private static final long serialVersionUID = 1L;

    public Integer getQualityTiemId() {
        return qualityTiemId;
    }

    public void setQualityTiemId(Integer qualityTiemId) {
        this.qualityTiemId = qualityTiemId;
    }

    public Integer getQualityId() {
        return qualityId;
    }

    public void setQualityId(Integer qualityId) {
        this.qualityId = qualityId;
    }

    public Integer getQualityTypeId() {
        return qualityTypeId;
    }

    public void setQualityTypeId(Integer qualityTypeId) {
        this.qualityTypeId = qualityTypeId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public Integer getItemScore() {
        return itemScore;
    }

    public void setItemScore(Integer itemScore) {
        this.itemScore = itemScore;
    }

    public String getItemEvidenceUrl() {
        return itemEvidenceUrl;
    }

    public void setItemEvidenceUrl(String itemEvidenceUrl) {
        this.itemEvidenceUrl = itemEvidenceUrl == null ? null : itemEvidenceUrl.trim();
    }
}