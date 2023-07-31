package com.cg.hrresource.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.hrresource.entities.JobHistory;
import com.cg.hrresource.entities.JobHistoryId;

import jakarta.transaction.Transactional;

public interface JobHistoryRepository extends JpaRepository<JobHistory, JobHistoryId> {

	@Modifying
	@Query("INSERT INTO JobHistory (employee, startDate, endDate, job, department) "
			+ "SELECT e, :startDate, null, j, d " + "FROM Employees e " + "JOIN Jobs j ON j.jobId = :jobId "
			+ "JOIN Departments d ON d.departmentId = :departmentId " + "WHERE e.employeeId = :employeeId")
	@Transactional
	void addJobHistoryEntry(@Param("employeeId") BigDecimal employeeId, @Param("startDate") String startDate,
			@Param("jobId") String jobId, @Param("departmentId") BigDecimal departmentId);

	@Modifying
	@Query("UPDATE JobHistory j SET j.endDate = :endDate WHERE j.employee.employeeId = :employeeId")
	void updateEndDateByEmployeeId(@Param("employeeId") BigDecimal employeeId,
			@Param("endDate") java.util.Date endDate);
	
	
	@Query("SELECT jh FROM JobHistory jh WHERE jh.employee.id = :employeeId")
    JobHistory findTotalExperienceByEmployeeId(@Param("employeeId") BigDecimal employeeId);
	
	
	//front end extra mrthods
	//get all the jobs history
	
	@Query("SELECT jh FROM JobHistory jh JOIN FETCH jh.employee JOIN FETCH jh.job JOIN FETCH jh.department")
    List<JobHistory> findAllWithDetails();


}
