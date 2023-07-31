package com.cg.hrresource.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cg.hrresource.entities.Employees;
import com.cg.hrresource.entities.Jobs;

public interface EmployeeService {

	void updateEmployee(Employees employee);

	List<Employees> findByFirstName(String firstName);

	Employees findByEmail(String email);

	Employees findByPhoneNumber(String phoneNumber);

	List<Employees> findAllEmployeeWithNoCommission();

	BigDecimal CalculateCommissionIssuedToEmployeeByDepartment(BigDecimal departmentId);

	List<Employees> listAllEmployeesByDepartment(BigDecimal departmentId);

	List<Map<String, Object>> countAllEmployeesGroupByDepartment();

//	List<Employees> getAllManagers();

//    List<Map<String, Object>> countAllEmployeesGroupByLocation();
	Map<BigDecimal, List<Employees>> getAllEmployeesByLocation();



	Map<String, Object> findMaxSalaryOfJobByEmployeeId(BigDecimal employeeId);

	// -------------------------------------------------------------------------------

//     String assignJob(String jobId);

	boolean updateEmployeeEmail(String email, String newEmail);

	boolean updateEmployeePhoneNumber(String phoneNumber, String newPhoneNumber);

	List<Employees> listEmployeesByHireDateRange(Date fromHireDate, Date toHireDate);

	// this methods are using updateManager , updateDepartment
	Employees getEmployeeById(BigDecimal employeeId);

	Employees saveEmployee(Employees employee);

	// for updating the department,and sales percentage

	Employees saveEmployeeWithDepartment(BigDecimal employeeId, BigDecimal departmentId);

	// for updating the employee job

	Jobs getJobById(String jobId);

	Employees updateEmployeeJob(String jobId);

	// to assign sales percentage
	void assignDepartmentAndUpdateCommission(BigDecimal employeeId, BigDecimal departmentId, BigDecimal commissionPct);

	Employees getEmployeeById(Long employeeId);
	
	
	//----------------------------------------------
	
//	Employees assignJobToEmployee(BigDecimal employeeId, String jobId);
	
	
	void deleteEmployeeById(BigDecimal empId);
	
	
	
	// Front end Mehtods

    // Find Employee By Department Id

    List<Employees> getEmployeesByDepartmentId(BigDecimal departmentId);
    
    //get all the manager details
    List<Employees> getAllManagers();
    
    


    
 // Find Employee who got comission

    List<Employees> findAllEmployeesWithCommission();
    
    
    //find name by employee Id
    Employees getEmployeeNameById(BigDecimal employeeId);
    
    //get manager name by manager ID
    Employees getManagerNameById(BigDecimal managerId);
    
    
  //Assign Employee using job ID

    Employees updateEmployeeJob(BigDecimal employeeId, String jobId);
    
    // get employee email by id
    String getEmployeeEmailById(BigDecimal employeeId);

}
