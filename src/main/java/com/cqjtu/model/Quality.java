package com.cqjtu.model;

import java.io.Serializable;

public class Quality implements Serializable {
    private Integer qualityId;

    private Long studentId;

    private Integer qualitySumScore;

    private Double comprehensiveQualityScore;

    private String academicYear;

    private static final long serialVersionUID = 1L;

    public Integer getQualityId() {
        return qualityId;
    }

    public void setQualityId(Integer qualityId) {
        this.qualityId = qualityId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getQualitySumScore() {
        return qualitySumScore;
    }

    public void setQualitySumScore(Integer qualitySumScore) {
        this.qualitySumScore = qualitySumScore;
    }

    public Double getComprehensiveQualityScore() {
        return comprehensiveQualityScore;
    }

    public void setComprehensiveQualityScore(Double comprehensiveQualityScore) {
        this.comprehensiveQualityScore = comprehensiveQualityScore;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear == null ? null : academicYear.trim();
    }
}