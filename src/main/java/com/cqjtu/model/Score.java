package com.cqjtu.model;

import java.io.Serializable;

public class Score implements Serializable {
	private Integer scoreId;

	private Double aveScore;

	private Double gpa;

	private String academicYear;

	private static final long serialVersionUID = 1L;

	public Integer getScoreId() {
		return scoreId;
	}

	public void setScoreId(Integer scoreId) {
		this.scoreId = scoreId;
	}

	public Double getAveScore() {
		return aveScore;
	}

	public void setAveScore(Double aveScore) {
		this.aveScore = aveScore;
	}

	public Double getGpa() {
		return gpa;
	}

	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear == null ? null : academicYear.trim();
	}

	@Override
	public String toString() {
		return "Score [scoreId=" + scoreId + ", aveScore=" + aveScore + ", gpa=" + gpa + ", academicYear="
				+ academicYear + "]";
	}
}