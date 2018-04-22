package com.cqjtu.model;

import java.io.Serializable;

public class Quality implements Serializable {
    private Integer qualityId;

    private Integer qualitySumScore;

    private static final long serialVersionUID = 1L;

    public Integer getQualityId() {
        return qualityId;
    }

    public void setQualityId(Integer qualityId) {
        this.qualityId = qualityId;
    }

    public Integer getQualitySumScore() {
        return qualitySumScore;
    }

    public void setQualitySumScore(Integer qualitySumScore) {
        this.qualitySumScore = qualitySumScore;
    }
}