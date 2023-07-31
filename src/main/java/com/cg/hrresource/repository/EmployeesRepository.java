package com.cg.hrresource.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.hrresource.entities.Employees;

public interface EmployeesRepository extends JpaRepository<Employees, BigDecimal> {
	List<Employees> findByFirstName(String firstName);

	Employees findByEmail(String email);

	Employees findByPhoneNumber(String phoneNumber);

	List<Employees> findByCommissionPctIsNull();

	@Query("SELECT COALESCE(SUM(e.commissionPct), 0) FROM Employees e WHERE e.department.departmentId = :departmentId")
	BigDecimal calculateTotalCommissionByDepartment(@Param("departmentId") BigDecimal departmentId);

	@Query("SELECT e FROM Employees e WHERE e.department.departmentId = :departmentId")
	List<Employees> findAllByDepartmentId(BigDecimal departmentId);

	@Query("SELECT e.department.departmentName, COUNT(e) FROM Employees e GROUP BY e.department")
	List<Object[]> countAllEmployeesGroupByDepartment();

	@Query("SELECT j.jobId AS jobId, MAX(e.job.maxSalary) AS maxSalary " + "FROM Employees e " + "JOIN e.job j "
			+ "WHERE e.id = :employeeId " + "GROUP BY j.jobId")
	Map<String, Object> findMaxSalaryOfJobByEmployeeId(@Param("employeeId") BigDecimal employeeId);

	@Query("SELECT e FROM Employees e WHERE e.id IN (SELECT DISTINCT manager FROM Employees)")
	List<Employees> findAllManagers();

	@Query("SELECT e FROM Employees e JOIN e.department d JOIN d.location l GROUP BY l.locationId, e")
	List<Employees> getAllEmployeesGroupByLocation();


	
	
//	@Query("select e from employees ASC where employee_id : empl  GROUPbY  ")

	// all the red methods are here

	List<Employees> findAllByHireDateBetween(java.util.Date fromHireDate, java.util.Date toHireDate);

	// to update job
	@Query("SELECT e FROM Employees e WHERE e.job.jobId = :jobId")
	Employees findByJobId(String jobId);

	// to update sales-pct

	List<Employees> findByDepartment_DepartmentId(BigDecimal departmentId);

	@Modifying
	@Query("UPDATE Employees e SET e.department.departmentId = :departmentId, "
			+ "e.commissionPct = :commissionPct WHERE e.department.departmentName = 'Sales'")
	void assignDepartmentAndUpdateCommission(@Param("departmentId") BigDecimal departmentId,
			@Param("commissionPct") BigDecimal commissionPct);

	List<Employees> findByDepartmentDepartmentName(String departmentName);

//Employees saveEmployeeWithDepartment(BigDecimal employeeId, BigDecimal departmentId);
	Employees findByEmployeeId(Long employeeId);

	// for departments opeartions

	@Query("SELECT MAX(e.salary) FROM Employees e WHERE e.department.departmentId = :departmentId")
	int findMaxSalaryByDepartmentId(@Param("departmentId") BigDecimal departmentId);

	@Query("SELECT MIN(e.salary) FROM Employees e WHERE e.department.departmentId = :departmentId")
	int findMinSalaryByDepartmentId(@Param("departmentId") BigDecimal departmentId);
	
	
	
	void deleteById(BigDecimal empId);

	
	//front end extra methods
	
	// employee with comission

    List<Employees> findByCommissionPctIsNotNull();
    
  //find first name by employee Id
    
    Employees findByEmployeeId(BigDecimal employeeId);
    
 // get emp detail via id 

    @Query("SELECT e FROM Employees e JOIN e.department d JOIN d.location l WHERE l.locationId = :locationId")
    List<Employees> findEmployeesByLocationId(@Param("locationId") BigDecimal locationId);
    

}