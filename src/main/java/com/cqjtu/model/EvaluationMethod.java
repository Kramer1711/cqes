package com.cqjtu.model;

import java.io.Serializable;

public class EvaluationMethod implements Serializable {
    private Integer evaluationMethodId;

    private String academicYear;

    private Integer typeId;

    private Double percentage;

    private static final long serialVersionUID = 1L;

    public Integer getEvaluationMethodId() {
        return evaluationMethodId;
    }

    public void setEvaluationMethodId(Integer evaluationMethodId) {
        this.evaluationMethodId = evaluationMethodId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear == null ? null : academicYear.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}