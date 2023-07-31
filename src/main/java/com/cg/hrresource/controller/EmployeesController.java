package com.cg.hrresource.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hrresource.entities.Employees;
import com.cg.hrresource.repository.EmployeesRepository;
import com.cg.hrresource.service.EmployeeServiceImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;

//@Validated
@RestController
@RequestMapping("/api/v1")
public class EmployeesController {
	

	@PersistenceContext
    private EntityManager entityManager;

	@Autowired
	private EmployeesRepository employeesRepository;

	@Autowired
	private EmployeeServiceImpl employeeService;

	// sample method for Testing

	@GetMapping("/public/employees")
	public List<Employees> getAllEmployees() {
		return employeesRepository.findAll();
	}

//    ADD NEW EMPLOYEE

	@PostMapping("/admin/employees")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Employees addEmployee(@Valid @RequestBody Employees employee) {
		System.out.println(employee);
		return employeesRepository.save(employee);
	}

//    MODIFY EXISTING EMPLOYEE

	@PutMapping("/admin/employees")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> updateEmployee(@Valid @RequestBody Employees employee) {
			employeeService.updateEmployee(employee);
			return ResponseEntity.ok("Record Modified Successfully");
	}


//    ASSIGN EMPLOYEES MANAGER

	@PutMapping("/admin/employees/employeesupdateManager/{managerId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> assignManager(@Valid @PathVariable BigDecimal managerId,
			@Valid @RequestBody Employees employee) {
		Employees manager = employeeService.getEmployeeById(managerId);
		if (manager == null) {
			return ResponseEntity.notFound().build();
		}

		Employees employeeToUpdate = employeeService.getEmployeeById(employee.getEmployeeId());
		if (employeeToUpdate == null) {
			return ResponseEntity.notFound().build();
		}

		employeeToUpdate.setManager(manager);

		Employees updatedEmployee = employeeService.saveEmployee(employeeToUpdate);

		if (updatedEmployee != null) {
			return ResponseEntity.ok("Record Modified Successfully");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

//    ASSIGN EMPLOYEES DEPARTMENT

	@PutMapping("/admin/employees/updateDepartment/{departmentId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> assignDepartment(@Valid @PathVariable BigDecimal departmentId,
			@Valid @RequestBody Employees employee) {
		Employees updatedEmployee = employeeService.saveEmployeeWithDepartment(employee.getEmployeeId(), departmentId);

		if (updatedEmployee != null) {
			return ResponseEntity.ok("Record Modified Successfully");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

//    ASSIGN EMPLOYEES DEPARTMENT AND UPDATE PERCENTAGE FOR THE SALES DEPARTMENT

	@PutMapping("/admin/employees/updateDepartmentAndSales/{employeeId}/{departmentId}/{commissionPct}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> assignDepartmentAndUpdateCommission(@Valid @PathVariable BigDecimal employeeId,
			@Valid @PathVariable BigDecimal departmentId, @Valid @PathVariable BigDecimal commissionPct) {
		employeeService.assignDepartmentAndUpdateCommission(employeeId, departmentId, commissionPct);
		return ResponseEntity.ok("Record Modified Successfully");
	}

//    FIND THE EMPLOYEE BY FIRST NAME

	@GetMapping("/admin/employees/findfname/{firstname}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<Employees>> findByFirstName(@Valid @PathVariable("firstname") String firstName) {
			List<Employees> employees = employeeService.findByFirstName(firstName);
			return ResponseEntity.ok(employees);
	}

//    FIND THE EMPLOYEE BY FIRST NAME

	@GetMapping("/admin/employees/findemail/{email}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Employees> findByEmail(@Valid @PathVariable("email") String email) {
			Employees employee = employeeService.findByEmail(email);
		
				return ResponseEntity.ok(employee);
			
	}

//    FIND THE EMPLOYEE BY PHONE NUMBER

	@GetMapping("/admin/employees/findphone/{phone}")
	@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Employees> findByPhoneNumber(@Valid @PathVariable("phone") String phoneNumber) {
        Employees employee = employeeService.findByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(employee);
    }

//    FIND ALL EMPLOYEE WITH NO COMISSION

	@GetMapping("/admin/employees/findAllEmployeeWithNoCommission")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Employees> findAllEmployeeWithNoCommission() {
		return employeeService.findAllEmployeeWithNoCommission();
	}

//    FIND TOTAL COMISSION ISSUED TO EMPLOYEE BY DEPARTMENT

	@GetMapping("/admin/employees/findTotalCommissionIssuedToEmployeeByDepartment/{department_id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Map<String, Object>> CalculateCommissionIssuedToEmployeeByDepartment(
			@Valid @PathVariable("department_id") BigDecimal departmentId) {

			BigDecimal totalCommission = employeeService.CalculateCommissionIssuedToEmployeeByDepartment(departmentId);

			Map<String, Object> response = new HashMap<>();
			response.put("departmentId", departmentId);
			response.put("sum", totalCommission);

			return ResponseEntity.ok(response);
		
	}

//    LIST OF ALL THE EMPLOYEES BY DEPARTMENT

	@GetMapping("/public/employees/listAllEmployees/{department_id}")
	public ResponseEntity<List<Employees>> listAllEmployeesByDepartment(
			@Valid @PathVariable("department_id") BigDecimal departmentId) {
		List<Employees> employees = employeeService.listAllEmployeesByDepartment(departmentId);
		return ResponseEntity.ok(employees);
	}

//    COUNT ALL EMPLOYEES GROPUP BY DEPARTMENT

	@GetMapping("/public/employees/employees_departmentwise_count")
	public ResponseEntity<List<Map<String, Object>>> countAllEmployeesGroupByDepartment() {
		List<Map<String, Object>> response = employeeService.countAllEmployeesGroupByDepartment();
		return ResponseEntity.ok(response);
	}

//    LIST OF ALL MANAGER DETAILS

	@GetMapping("/public/employees/listallmanagerdetails")
//	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<Employees>> listAllManagerDetails() {
		List<Employees> managers = employeeService.getAllManagers();
		return ResponseEntity.ok(managers);
	}

// COUNT ALL EMPLOYEE GROUP BY LOCATION

	@GetMapping("/admin/employees/locationwisecountofemployees")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Map<BigDecimal, List<Employees>>> listAllEmployeesByLocation() {
		Map<BigDecimal, List<Employees>> employeesByLocation = employeeService.getAllEmployeesByLocation();
		return ResponseEntity.ok(employeesByLocation);
	}

//    FIND MAX SALARY OF JOB BY EMPLOYEE ID

	@GetMapping("/admin/employees/{empid}/findmaxsalaryofjob")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Map<String, Object>> findMaxSalaryOfJobByEmployeeId(
			@Valid @PathVariable("empid") BigDecimal employeeId) {
		Map<String, Object> response = employeeService.findMaxSalaryOfJobByEmployeeId(employeeId);
		return ResponseEntity.ok(response);
	}

//    UPDATE EMPLOYEES EMAIL

	@PutMapping("/employee/employees/updateEmail/{email}")
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	public ResponseEntity<String> updateEmployeeEmail(@Valid @PathVariable("email") String email,
			@Valid @RequestBody Map<String, String> requestBody) {
		String newEmail = requestBody.get("email");

		if (newEmail == null) {
			return ResponseEntity.badRequest().body("New email is required.");
		}

		boolean isUpdated = employeeService.updateEmployeeEmail(email, newEmail);
		if (isUpdated) {
			return ResponseEntity.ok("Record Modified Successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

//    UPDATE EMPLOYEE BY PHONE NUMBER

	@PutMapping("/employee/employees/updatePhoneNumber/{phoneNumber}")
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	public ResponseEntity<String> updateEmployeePhoneNumber(@Valid @PathVariable("phoneNumber") String phoneNumber,
			@Valid @RequestBody Map<String, String> requestBody) {
		String newPhoneNumber = requestBody.get("phoneNumber");

		if (newPhoneNumber == null) {
			return ResponseEntity.badRequest().body("New phone number is required.");
		}

		boolean isUpdated = employeeService.updateEmployeePhoneNumber(phoneNumber, newPhoneNumber);
		if (isUpdated) {
			return ResponseEntity.ok("Record Modified Successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}


//    LIST OF ALL EMPLOYEE HIRED ON PERTICULAR DATE RANGE

	@GetMapping("/admin/employees/listallemployeebyhiredate/{from_hiredate}/{to_hiredate}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Employees> listEmployeesByHireDateRange(
			@Valid @PathVariable("from_hiredate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromHireDate,
			@Valid @PathVariable("to_hiredate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toHireDate) {
		return employeeService.listEmployeesByHireDateRange(fromHireDate, toHireDate);
	}

//    DELETE EMPLOYEE BY EMPLOYEE ID

	@DeleteMapping("/admin/employees/{emp_id}")
	@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteEmployeeById(@Valid @PathVariable("emp_id") BigDecimal empId) {
        // Call the employee service method to delete the employee by ID
        employeeService.deleteEmployeeById(empId);

        return ResponseEntity.ok("Employee with ID " + empId + " has been deleted.");
    }
	
	
	// Front End extra Methods
	
		// Get Employees by dept Id

	    @GetMapping("/public/department/{departmentId}")
	    public ResponseEntity<List<Employees>> getAllEmployeesByDepartmentId(@PathVariable BigDecimal departmentId) {
	        List<Employees> employees = employeeService.getEmployeesByDepartmentId(departmentId);
	        return ResponseEntity.ok(employees);
	    }
	    
	    
////    FIND ALL EMPLOYEE WITh COMISSION

	    

    @GetMapping("/admin/employees/findAllEmployeeWithCommission")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Employees>> findAllEmployeesWithCommission() {
        List<Employees> employees = employeeService.findAllEmployeesWithCommission();
        return ResponseEntity.ok(employees);
    }  
    
    
 // get employee name by id 
    @GetMapping("/public/employees/{employeeId}/name")
    public ResponseEntity<String> getEmployeeNameById(@PathVariable("employeeId") BigDecimal employeeId) {
        Employees employee = employeeService.getEmployeeNameById(employeeId);
        if (employee == null) {
            return ResponseEntity.notFound().build(); // Employee not found
        }

 

        String fullName = employee.getFirstName() + " " + employee.getLastName();
        return ResponseEntity.ok(fullName);
    }
    
    
  //Get manger name by Id

    @GetMapping("/public/employees/{managerId}/managername")
    public ResponseEntity<String> getManagerNameById(@PathVariable("managerId") BigDecimal managerId) {
        Employees manager = employeeService.getManagerNameById(managerId);
        if (manager == null) {
            return ResponseEntity.notFound().build(); // Employee not found status
        }
        String fullName = manager.getFirstName() + " " + manager.getLastName();
        return ResponseEntity.ok(fullName);
    }
    
    
// get email and phone number by employee id
  //Get employee phone number by Id

    @GetMapping("/public/employees/{employeeId}/phone")
    public ResponseEntity<String> getEmployeePhone(@Valid @PathVariable("employeeId") BigDecimal employeeId) {
        Employees employee = employeeService.getEmployeeById(employeeId);
        if (employee != null) {
            String phoneNumber = employee.getPhoneNumber();
            return new ResponseEntity<>(phoneNumber, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // get employee email by id 


     @GetMapping("/public/employees/{employeeId}/email")
        public ResponseEntity<String> getEmployeeEmailById(@Valid @PathVariable("employeeId") BigDecimal employeeId) {
            String email = employeeService.getEmployeeEmailById(employeeId);
            if (email != null) {
                return ResponseEntity.ok(email);
            }
            return ResponseEntity.notFound().build();
        }

   
    
    
 // Assing EmployeeId USing Job ID 
    
    @PutMapping("/public/employees/{employeeId}/updateJob/{jobId}")
    public ResponseEntity<String> updateEmployeeJob(
            @PathVariable("employeeId") BigDecimal employeeId,
            @PathVariable("jobId") String jobId) {
        Employees updatedEmployee = employeeService.updateEmployeeJob(employeeId, jobId);
        if (updatedEmployee != null) {
            return ResponseEntity.ok("Record Modified Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    //updating the firstName and lastName
    
    @PutMapping("/employee/employees/updateName/{employeeId}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<String> updateEmployeeName(@PathVariable("employeeId") BigDecimal employeeId,
                                                     @Valid @RequestBody Map<String, String> requestBody) {
        String firstName = requestBody.get("firstName");
        String lastName = requestBody.get("lastName");

        if (firstName == null || lastName == null) {
            return ResponseEntity.badRequest().body("First name and last name are required.");
        }

        Optional<Employees> optionalEmployee = employeesRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            Employees employee = optionalEmployee.get();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employeesRepository.save(employee);
            return ResponseEntity.ok("Record Modified Successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}