package com.cg.hrresource.service;

import java.math.BigDecimal;
import java.time.Period;
import java.util.List;

import com.cg.hrresource.dto.ExperienceResponse;
import com.cg.hrresource.entities.JobHistory;

public interface JobHistoryService {

	//
	void addJobHistoryEntry(BigDecimal employeeId, String startDate, String jobId, BigDecimal departmentId);

	void updateEndDateByEmployeeId(BigDecimal employeeId, java.util.Date parsedEndDate);

	Period calculateTotalExperience(BigDecimal employeeId);
	
	List<ExperienceResponse> listAllEmployeesWithLessThanOneYearExperience();
	
	//front end extra methods
	//get all job history
	List<JobHistory> getAllJobHistory();

}