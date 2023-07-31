package com.cg.hrresource.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hrresource.dto.ExperienceResponse;
import com.cg.hrresource.entities.JobHistory;
import com.cg.hrresource.service.JobHistoryService;

@Validated
@RestController
@RequestMapping("/api/v1")
public class JobHistoryController {

	@Autowired
	private JobHistoryService jobHistoryService;

	@PostMapping("/admin/jobhistory/{employeeId}/{startDate}/{jobId}/{departmentId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> addJobHistoryEntry(@PathVariable("employeeId") BigDecimal employeeId,
			@PathVariable("startDate") String startDate, @PathVariable("jobId") String jobId,
			@PathVariable("departmentId") BigDecimal departmentId) {
		jobHistoryService.addJobHistoryEntry(employeeId, startDate, jobId, departmentId);
		return ResponseEntity.ok("Record Created Successfully");
	}
	

	@PutMapping("/admin/jobhistory/updateEndDate/{empid}/{enddate}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> updateJobHistoryEndDate(@PathVariable BigDecimal empid,
			@PathVariable String enddate) {
		// Parse the end date string into a Date object
		java.util.Date parsedEndDate = null;
		try {
			parsedEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid end date format");
		}

		// Update the end date using the employee ID
		jobHistoryService.updateEndDateByEmployeeId(empid, parsedEndDate);

		return ResponseEntity.ok("Record Modified Successfully");
	}
	
	
	@GetMapping("/admin/jobhistory/totalyearsofexperience/{emp_id}")
	@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ExperienceResponse> findExperienceOfEmployees(@PathVariable("emp_id") BigDecimal employeeId) {
        Period totalExperience = jobHistoryService.calculateTotalExperience(employeeId);

        if (totalExperience == null) {
            return ResponseEntity.notFound().build();
        }

        ExperienceResponse experienceResponse = convertPeriodToResponse(totalExperience);
        return ResponseEntity.ok(experienceResponse);
    }

 

    private ExperienceResponse convertPeriodToResponse(Period period) {
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();
        return new ExperienceResponse(years, months, days);
    }
    
    
    @GetMapping("/admin/jobhistory/lessthanoneyearexperience")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ExperienceResponse>> listAllEmployeesWithLessThanOneYearExperience() {
        List<ExperienceResponse> response = jobHistoryService.listAllEmployeesWithLessThanOneYearExperience();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
//    /front end exctra methods
//    get all job history
    
    @GetMapping("/admin/jobhistory/getalljobhistory")
    public List<JobHistory> getAllJobHistory() {
        return jobHistoryService.getAllJobHistory();
    }

	

}
