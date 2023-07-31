package com.cg.hrresource.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrresource.dto.ExperienceResponse;
import com.cg.hrresource.entities.Departments;
import com.cg.hrresource.entities.Employees;
import com.cg.hrresource.entities.JobHistory;
import com.cg.hrresource.entities.Jobs;
import com.cg.hrresource.repository.DepartmentsRepository;
import com.cg.hrresource.repository.EmployeesRepository;
import com.cg.hrresource.repository.JobHistoryRepository;
import com.cg.hrresource.repository.JobsRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class JobHistoryServiceImpl implements JobHistoryService {

	@Autowired
	private JobHistoryRepository jobHistoryRepository;

	@Autowired
	private EmployeesRepository employeesRepository;

	@Autowired
	private DepartmentsRepository departmentsRepository;

	@Autowired

	private JobsRepository jobsRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void addJobHistoryEntry(BigDecimal employeeId, String startDate, String jobId, BigDecimal departmentId) {
		Employees employee = entityManager
				.createQuery("SELECT e FROM Employees e WHERE e.employeeId = :employeeId", Employees.class)
				.setParameter("employeeId", employeeId).getSingleResult();

		// Retrieve the Department entity based on the provided departmentId using JPQL
		Departments department = entityManager
				.createQuery("SELECT d FROM Departments d WHERE d.departmentId = :departmentId", Departments.class)
				.setParameter("departmentId", departmentId).getSingleResult();

		Jobs job = entityManager.createQuery("SELECT j FROM Jobs j WHERE j.jobId = :jobId", Jobs.class)
				.setParameter("jobId", jobId).getSingleResult();

		// Convert the startDate String to a Date object

		LocalDate endDate1 = LocalDate.now();
		Date endDate = java.sql.Date.valueOf(endDate1);

		JobHistory jobHistory = new JobHistory();

		jobHistory.setEmployee(employee);
		jobHistory.setStartDate(Date.valueOf(startDate));
		jobHistory.setEndDate(endDate);
		jobHistory.setJob(job);
		jobHistory.setDepartment(department);

		jobHistoryRepository.save(jobHistory);
	}

	@Override
	@Transactional
	public void updateEndDateByEmployeeId(BigDecimal employeeId, java.util.Date endDate) {
		jobHistoryRepository.updateEndDateByEmployeeId(employeeId, endDate);
	}
	
	
	@Override
    public Period calculateTotalExperience(BigDecimal employeeId) {
        JobHistory jobHistory = jobHistoryRepository.findTotalExperienceByEmployeeId(employeeId);
        if (jobHistory == null || jobHistory.getEndDate() == null) {
            return null;
        }

 

        // Convert java.sql.Date to java.util.Date
        java.util.Date startDateUtil = new java.util.Date(jobHistory.getStartDate().getTime());
        java.util.Date endDateUtil = new java.util.Date(jobHistory.getEndDate().getTime());

 

        LocalDate startDate = startDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = endDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

 

        return Period.between(startDate, endDate);
    }
	
	
	@Override
    public List<ExperienceResponse> listAllEmployeesWithLessThanOneYearExperience() {
        List<ExperienceResponse> employeesWithLessThanOneYearExperience = new ArrayList<>();

 

        List<JobHistory> jobHistories = jobHistoryRepository.findAll();

 

        for (JobHistory jobHistory : jobHistories) {
            java.util.Date startDateUtil = new java.util.Date(jobHistory.getStartDate().getTime());
            java.util.Date endDateUtil = new java.util.Date(jobHistory.getEndDate().getTime());

 

            LocalDate startDate = startDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = endDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

 

            Period period = Period.between(startDate, endDate);

 

            if (period.getYears() < 1) {
                ExperienceResponse response = new ExperienceResponse(period.getYears(), period.getMonths(), period.getDays());
                employeesWithLessThanOneYearExperience.add(response);
            }
        }

 

        return employeesWithLessThanOneYearExperience;
    }
	
	
	//front end extra mrthods
	//get all job history
	
	@Override
    public List<JobHistory> getAllJobHistory() {
        return jobHistoryRepository.findAllWithDetails();
    }

}