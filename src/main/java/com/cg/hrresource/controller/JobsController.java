package com.cg.hrresource.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hrresource.dto.SalaryRangeResponse;
import com.cg.hrresource.entities.Jobs;
import com.cg.hrresource.service.JobsService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1")
public class JobsController {

	@Autowired
	private JobsService jobsService;

	@Autowired
	public JobsController(JobsService jobsService) {
		this.jobsService = jobsService;
	}

	// ADD NEW JOB -- POST

	@PostMapping("/admin/job")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> addJob(@Valid @RequestBody Jobs job) {
		jobsService.addJob(job);
		return ResponseEntity.status(HttpStatus.CREATED).body("Record Created Successfully");
	}

	// MODIFY THE JOB

	@PutMapping("/admin/job/{jobId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> modifyJob(@Valid @PathVariable String jobId, @Valid @RequestBody Jobs job) {
		Jobs modifiedJob = jobsService.modifyJob(job);
		if (modifiedJob != null) {
			return ResponseEntity.ok("Record Modified Successfully");
		} else {
			return ResponseEntity.badRequest().body("Validation failed");
		}
	}

	// GET ALL THE JOBS

	@GetMapping("/public/job")
//	@PreAuthorize("hasAuthority('EMPLOYEE')")
	public ResponseEntity<List<Jobs>> getAllJobs() {
		List<Jobs> jobs = jobsService.getAllJobs();
		return new ResponseEntity<>(jobs, HttpStatus.OK);
	}

	// UPDATE MIN AND MAX SALARY IF ANY CHANGES

	@PutMapping("/admin/job/{jobId}/{minSalary}/{maxSalary}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> updateSalary(@Valid @PathVariable String jobId,
			@Valid @PathVariable BigDecimal minSalary, @Valid @PathVariable BigDecimal maxSalary) {
		boolean isUpdated = jobsService.updateSalary(jobId, minSalary, maxSalary);

		if (isUpdated) {
			return ResponseEntity.ok("Job Salary Updated Successfully");
		} else {
			return ResponseEntity.badRequest().body("No changes made to the Job Salary");
		}
	}

	// DELETE JOB BY JOB ID

	@DeleteMapping("/admin/job/{jobId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> deleteJobById(@Valid @PathVariable String jobId) {
		boolean deleted = jobsService.deleteJobById(jobId);
		if (deleted) {
			return ResponseEntity.ok("Record deleted Successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	//Front end extra methods
	
	@GetMapping("/public/job/{jobTitle}/getJobIds")
    public ResponseEntity<List<String>> getJobIdsByJobTitle(@PathVariable String jobTitle) {
        try {
            List<String> jobIds = jobsService.getJobIdsByJobTitle(jobTitle);
            if (jobIds != null && !jobIds.isEmpty()) {
                return ResponseEntity.ok(jobIds);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
	
	// GET MIN AND MAX SALARY
	@GetMapping("/admin/job/{jobTitle}/salaryrange")
	@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SalaryRangeResponse> getJobSalaryRange(@PathVariable String jobTitle) {
        BigDecimal minSalary = jobsService.getMinSalaryByJobTitle(jobTitle);
        BigDecimal maxSalary = jobsService.getMaxSalaryByJobTitle(jobTitle);
        if (minSalary != null && maxSalary != null) {
            SalaryRangeResponse salaryRangeResponse = new SalaryRangeResponse(minSalary, maxSalary);
            return ResponseEntity.ok(salaryRangeResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
