package com.cg.hrresource.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.hrresource.entities.Departments;
import com.cg.hrresource.repository.DepartmentsRepository;
import com.cg.hrresource.service.DepartmentsService;
@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {

    @InjectMocks
    private DepartmentsController departmentController;

    @Mock
    private DepartmentsService departmentsService;
    
    @Mock
	private DepartmentsRepository departmentsRepository;  
    

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddDepartment() {
        Departments department = new Departments(); // Create a mock department object

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Record Created Successfully");

        // Mock the behavior of the departmentsService.createDepartment method
        when(departmentsService.createDepartment(department)).thenReturn(department/* return value */);

        ResponseEntity<String> actualResponse = departmentController.addDepartment(department);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        // Verify that the departmentsService.createDepartment method was called once with the correct parameter
        verify(departmentsService, times(1)).createDepartment(department);
    }
    
    @Test
    void testUpdateDepartment() {
        BigDecimal departmentId = new BigDecimal(1); // Set the department ID
        Departments updatedDepartment = new Departments(); // Create a mock updated department object
        Departments mockDepartment = new Departments(); // Create a mock department object

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Record Modified Successfully");

        // Mock the behavior of the departmentsService.updateDepartment method
        when(departmentsService.updateDepartment(departmentId, updatedDepartment)).thenReturn(mockDepartment);

        ResponseEntity<String> actualResponse = departmentController.updateDepartment(departmentId, updatedDepartment);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        // Verify that the departmentsService.updateDepartment method was called once with the correct parameters
        verify(departmentsService, times(1)).updateDepartment(departmentId, updatedDepartment);
    }
    
    @Test
    void testGetMaxSalaryByDepartmentId() {
        BigDecimal departmentId = new BigDecimal(1); // Set the department ID
        int maxSalary = 5000; // Set the expected max salary
        String departmentName = "Finance"; // Set the expected department name

        Optional<Departments> optionalDepartment = Optional.of(new Departments());
        optionalDepartment.get().setDepartmentName(departmentName);

        // Mock the behavior of the departmentsService.getMaxSalaryByDepartmentId method
        when(departmentsService.getMaxSalaryByDepartmentId(departmentId)).thenReturn(maxSalary);

        // Mock the behavior of the departmentsRepository.findById method
        when(departmentsRepository.findById(departmentId)).thenReturn(optionalDepartment);

        ResponseEntity<Map<String, Object>> responseEntity = departmentController.getMaxSalaryByDepartmentId(departmentId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Map<String, Object> response = responseEntity.getBody();
        assertEquals(departmentName, response.get("department_name"));
        assertEquals(maxSalary, response.get("max_salary"));

        // Verify that the departmentsService.getMaxSalaryByDepartmentId method was called once with the correct parameter
        verify(departmentsService, times(1)).getMaxSalaryByDepartmentId(departmentId);

        // Verify that the departmentsRepository.findById method was called once with the correct parameter
        verify(departmentsRepository, times(1)).findById(departmentId);
    }
    @Test
    void testGetMinSalaryByDepartmentId() {
        BigDecimal departmentId = new BigDecimal(1); // Set the department ID
        int minSalary = 2000; // Set the expected min salary
        String departmentName = "Finance"; // Set the expected department name

        Optional<Departments> optionalDepartment = Optional.of(new Departments());
        optionalDepartment.get().setDepartmentName(departmentName);

        // Mock the behavior of the departmentsService.getMinSalaryByDepartmentId method
        when(departmentsService.getMinSalaryByDepartmentId(departmentId)).thenReturn(minSalary);

        // Mock the behavior of the departmentsRepository.findById method
        when(departmentsRepository.findById(departmentId)).thenReturn(optionalDepartment);

        ResponseEntity<Map<String, Object>> responseEntity = departmentController.getMinSalaryByDepartmentId(departmentId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Map<String, Object> response = responseEntity.getBody();
        assertEquals(departmentName, response.get("department_name"));
        assertEquals(minSalary, response.get("min_salary"));

        // Verify that the departmentsService.getMinSalaryByDepartmentId method was called once with the correct parameter
        verify(departmentsService, times(1)).getMinSalaryByDepartmentId(departmentId);

        // Verify that the departmentsRepository.findById method was called once with the correct parameter
        verify(departmentsRepository, times(1)).findById(departmentId);
    }
    @Test
    void testGetDepartmentsByEmployeeId() {
        BigDecimal employeeId = new BigDecimal(1); // Set the employee ID
        List<Departments> expectedDepartments = List.of(new Departments(), new Departments()); // Set the expected departments

        // Mock the behavior of the departmentsService.getDepartmentsByEmployeeId method
        when(departmentsService.getDepartmentsByEmployeeId(employeeId)).thenReturn(expectedDepartments);

        ResponseEntity<List<Departments>> responseEntity = departmentController.getDepartmentsByEmployeeId(employeeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<Departments> actualDepartments = responseEntity.getBody();
        assertEquals(expectedDepartments, actualDepartments);

        // Verify that the departmentsService.getDepartmentsByEmployeeId method was called once with the correct parameter
        verify(departmentsService, times(1)).getDepartmentsByEmployeeId(employeeId);
    }
    @Test
    void testDeleteDepartment() {
        BigDecimal departmentId = new BigDecimal(1); // Set the department ID

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Record Deleted Successfully");

        // Mock the behavior of the departmentsService.deleteDepartment method
        doNothing().when(departmentsService).deleteDepartment(departmentId);

        ResponseEntity<String> actualResponse = departmentController.deleteDepartment(departmentId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        // Verify that the departmentsService.deleteDepartment method was called once with the correct parameter
        verify(departmentsService, times(1)).deleteDepartment(departmentId);
    }
    @Test
    void testGetDepartmentNameById() {
        // Mock data
        BigDecimal departmentId = new BigDecimal(123);
        String departmentName = "Test Department";

        // Mock the service method
        when(departmentsService.getDepartmentNameById(departmentId)).thenReturn(departmentName);

        // Invoke the controller method
        ResponseEntity<String> response = departmentController.getDepartmentNameById(departmentId);

        // Verify the service method is called
        verify(departmentsService).getDepartmentNameById(departmentId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departmentName, response.getBody());
    }
    @Test
    void testGetSalaryStatsByDepartmentId() {
        // Mock data
        BigDecimal departmentId = new BigDecimal(123);
        Map<String, Object> salaryStats = new HashMap<>();
        salaryStats.put("department_name", "Test Department");
        salaryStats.put("max_salary", 10000);
        salaryStats.put("min_salary", 5000);

        // Mock the service method
        when(departmentsService.getSalaryStatsByDepartmentId(departmentId)).thenReturn(salaryStats);

        // Invoke the controller method
        ResponseEntity<Map<String, Object>> response = departmentController.getSalaryStatsByDepartmentId(departmentId);

        // Verify the service method is called
        verify(departmentsService).getSalaryStatsByDepartmentId(departmentId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(salaryStats, response.getBody());
    }
    @Test
    void testGetAllDepartments() {
        // Mock data
        List<Departments> departments = new ArrayList<>();
        departments.add(new Departments());
        departments.add(new Departments());

        // Mock the service method
        when(departmentsService.getAllDepartments()).thenReturn(departments);

        // Invoke the controller method
        ResponseEntity<List<Departments>> response = departmentController.getAllDepartments();

        // Verify the service method is called
        verify(departmentsService).getAllDepartments();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departments, response.getBody());
    }
}
