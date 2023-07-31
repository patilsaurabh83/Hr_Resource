package com.cg.hrresource.service;

import java.math.BigDecimal;
import java.util.List;

import com.cg.hrresource.entities.Jobs;

public interface JobsService {
	Jobs addJob(Jobs jobs);

	List<Jobs> getAllJobs();

	Jobs modifyJob(Jobs jobs);

	Jobs createJob(Jobs job);

	boolean updateSalary(String jobId, BigDecimal minSalary, BigDecimal maxSalary);

	boolean deleteJobById(String jobId);

	boolean deleteJobById1(String jobId);

	Jobs getJob();

	void saveJob(Jobs job);

	Jobs findBySalary(BigDecimal minSalary, BigDecimal maxSalary);

	Jobs save(Jobs job);

	Jobs findBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary);

	Jobs getJobById(String jobId);

	Jobs findById(String jobId);

	Jobs getJobById1(String jobId);
	
	//front end extra methods
	List<String> getJobIdsByJobTitle(String jobTitle);
	
	// FOR MIN AND MaX SALARY
	BigDecimal getMinSalaryByJobTitle(String jobTitle);

    BigDecimal getMaxSalaryByJobTitle(String jobTitle);
}
