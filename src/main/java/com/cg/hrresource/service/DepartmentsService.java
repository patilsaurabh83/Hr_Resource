package com.cg.hrresource.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.cg.hrresource.entities.Departments;



public interface DepartmentsService {
	Departments createDepartment(Departments department);

//	Departments updateDepartment(Departments department);

	List<Departments> getDepartmentsByEmployeeId(BigDecimal employeeId);

	void deleteDepartment(BigDecimal departmentId);

	// for max salary

	int getMaxSalaryByDepartmentId(BigDecimal departmentId);

	// for min salary

	int getMinSalaryByDepartmentId(BigDecimal departmentId);
	
	
	//front end extra methods
	
	/// Get Department name by department ID

    String getDepartmentNameById(BigDecimal departmentId);
    
 // Get max and min salary by use of depatId

    Map<String, Object> getSalaryStatsByDepartmentId(BigDecimal departmentId);
    
    //get the name and dept id
    List<Departments> getAllDepartments();
    

    Departments updateDepartment(BigDecimal departmentId, Departments updatedDepartment);

}
