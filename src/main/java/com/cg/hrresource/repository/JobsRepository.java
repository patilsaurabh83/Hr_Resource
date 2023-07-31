package com.cg.hrresource.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.hrresource.entities.Jobs;

@Repository
public interface JobsRepository extends JpaRepository<Jobs, String> {
	// You can add custom repository methods if needed

	@Query("SELECT j FROM Jobs j WHERE j.jobId = :jobId")
	Jobs findByJobId(String jobId);

	@Query("SELECT j FROM Jobs j WHERE j.minSalary = :minSalary AND j.maxSalary = :maxSalary")
	Jobs findByMinSalaryAndMaxSalary(@Param("minSalary") BigDecimal minSalary,
			@Param("maxSalary") BigDecimal maxSalary);

	Jobs save(Jobs job);

	Optional<Jobs> findById(String jobId);
	
	//front end extra methods
	List<Jobs> findByJobTitle(String jobTitle);
	
	// FOR MIN AND MAX SALARY
	@Query("SELECT MIN(j.minSalary) FROM Jobs j WHERE j.jobTitle = :jobTitle")
    BigDecimal findMinSalaryByJobTitle(String jobTitle);

    @Query("SELECT MAX(j.maxSalary) FROM Jobs j WHERE j.jobTitle = :jobTitle")
    BigDecimal findMaxSalaryByJobTitle(String jobTitle);

}