package com.cg.hrresource.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.cg.hrresource.entities.Departments;
import com.cg.hrresource.repository.DepartmentsRepository;
import com.cg.hrresource.service.DepartmentsService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1")
public class DepartmentsController {

	@Autowired
	private DepartmentsService departmentsService;

	@Autowired
	private DepartmentsRepository departmentsRepository;

// ADD NEW DEPARTMENT

	@PostMapping("/public/department")
//	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> addDepartment(@Valid @RequestBody Departments department) {
		departmentsService.createDepartment(department);
		return ResponseEntity.ok("Record Created Successfully");
	}

	// MODIFY DEPARTMENT

	@PutMapping("/admin/department")
	@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateDepartment(
            @PathVariable("departmentId") BigDecimal departmentId,
            @RequestBody Departments updatedDepartment) {
        Departments department = departmentsService.updateDepartment(departmentId, updatedDepartment);
        return ResponseEntity.ok("Record Modified Successfully");
    }

	// FIND EMPLOYEE MAX SALARY BY DEPARTMENT ID

	@GetMapping("/admin/department/findmaxsalary/{department_id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Map<String, Object>> getMaxSalaryByDepartmentId(
			@Valid @PathVariable("department_id") BigDecimal departmentId) {
		int maxSalary = departmentsService.getMaxSalaryByDepartmentId(departmentId);

		Optional<Departments> optionalDepartment = departmentsRepository.findById(departmentId);
		String departmentName = optionalDepartment.map(Departments::getDepartmentName).orElse(null);

		Map<String, Object> response = new HashMap<>();
		response.put("department_name", departmentName);
		response.put("max_salary", maxSalary);

		return ResponseEntity.ok(response);
	}

// FIND EMPLOYEE MINIMUM SALARY BY DEPARTMENT ID   

	@GetMapping("/admin/department/findminsalary/{department_id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Map<String, Object>> getMinSalaryByDepartmentId(
			@Valid @PathVariable("department_id") BigDecimal departmentId) {
		int minSalary = departmentsService.getMinSalaryByDepartmentId(departmentId);

		Optional<Departments> optionalDepartment = departmentsRepository.findById(departmentId);
		String departmentName = optionalDepartment.map(Departments::getDepartmentName).orElse(null);

		Map<String, Object> response = new HashMap<>();
		response.put("min_salary", minSalary);
		response.put("department_name", departmentName);

		return ResponseEntity.ok(response);
	}

	// GET ALL DPARTMENT DETAILS WHERE EMPLOYEE WORKED IN

	@GetMapping("/employee/department/{empid}")
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	public ResponseEntity<List<Departments>> getDepartmentsByEmployeeId(
			@Valid @PathVariable("empid") BigDecimal employeeId) {
		List<Departments> departments = departmentsService.getDepartmentsByEmployeeId(employeeId);
		return ResponseEntity.ok(departments);
	}

// DELETE BY DEPARTMENT ID    

	@DeleteMapping("/admin/department/{department_id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> deleteDepartment(@Valid @PathVariable("department_id") BigDecimal departmentId) {
		departmentsService.deleteDepartment(departmentId);
		return ResponseEntity.ok("Record Deleted Successfully");
	}
	
	
	
	//extra methods for front end
	//get Department Name By department ID
	
	@GetMapping("/public/departments/{departmentId}/name")
    public ResponseEntity<String> getDepartmentNameById(@Valid @PathVariable("departmentId") BigDecimal departmentId) {
        String departmentName = departmentsService.getDepartmentNameById(departmentId);
            return ResponseEntity.ok(departmentName);
    }
	
	//get min and max salary by dept id 

    @GetMapping("/public/departments/salaryStats/{departmentId}")
       public ResponseEntity<Map<String, Object>> getSalaryStatsByDepartmentId(
               @Valid @PathVariable("departmentId") BigDecimal departmentId) {
           Map<String, Object> salaryStats = departmentsService.getSalaryStatsByDepartmentId(departmentId);
           return ResponseEntity.ok(salaryStats);
       }
    
    
    // get the name and id of dept
  //get all department

    @GetMapping("/public/department/departmentname")
    public ResponseEntity<List<Departments>> getAllDepartments() {
        List<Departments> departments = departmentsService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

}