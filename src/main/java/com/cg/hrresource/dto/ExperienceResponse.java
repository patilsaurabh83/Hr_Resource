package com.cg.hrresource.dto;

public class ExperienceResponse {

	private int years;
	private int months;
	private int days;
	private String error;

	public ExperienceResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExperienceResponse(String error) {

		this.error = error;
	}

	public ExperienceResponse(int years, int months, int days) {
		this.years = years;
		this.months = months;
		this.days = days;
	}

	public int getYears() {
		return years;
	}

	public int getMonths() {
		return months;
	}

	public int getDays() {
		return days;
	}

}