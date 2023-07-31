package com.cg.hrresource.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.hrresource.entities.Employees;
import com.cg.hrresource.repository.EmployeesRepository;
import com.cg.hrresource.service.EmployeeServiceImpl;

@SpringBootTest
public class EmployeesControllerTest {
    
    @Mock
    private EmployeesRepository employeesRepository;
    
    @Mock
    private EmployeeServiceImpl employeeService;
    
    @InjectMocks
    private EmployeesController employeeController;
    
    @Test
    public void testAddEmployee() {
        // Arrange
        Employees employee = new Employees();
        // Set employee properties
        
        when(employeesRepository.save(any(Employees.class))).thenReturn(employee);
        
        // Act
        Employees result = employeeController.addEmployee(employee);
        
        // Assert
        assertNotNull(result);
    }
    
    @Test
    public void testUpdateEmployee() {
        // Arrange
        Employees employee = new Employees();
        // Set employee properties
        
        // Act
        ResponseEntity<String> response = employeeController.updateEmployee(employee);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record Modified Successfully", response.getBody());
    }
    
    @Test
    public void testFindByFirstName() {
        // Arrange
        String firstName = "John";
        List<Employees> employees = new ArrayList<>();
        // Add some employees to the list
        
        when(employeeService.findByFirstName(eq(firstName))).thenReturn(employees);
        
        // Act
        ResponseEntity<List<Employees>> response = employeeController.findByFirstName(firstName);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(employees, response.getBody());
    }
    @Test
    public void testFindByEmail() {
        // Arrange
        String email = "john@example.com";
        Employees employee = new Employees();
        // Set employee properties
        
        when(employeeService.findByEmail(eq(email))).thenReturn(employee);
        
        // Act
        ResponseEntity<Employees> response = employeeController.findByEmail(email);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(employee, response.getBody());
    }
    @Test
    public void testFindByPhoneNumber() {
        // Arrange
        String phoneNumber = "1234567890";
        Employees employee = new Employees();
        // Set employee properties
        
        when(employeeService.findByPhoneNumber(eq(phoneNumber))).thenReturn(employee);
        
        // Act
        ResponseEntity<Employees> response = employeeController.findByPhoneNumber(phoneNumber);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(employee, response.getBody());
    }
    @Test
    public void testFindAllEmployeeWithNoCommission() {
        // Arrange
        List<Employees> employees = new ArrayList<>();
        // Add some employees to the list
        
        when(employeeService.findAllEmployeeWithNoCommission()).thenReturn(employees);
        
        // Act
        List<Employees> result = employeeController.findAllEmployeeWithNoCommission();
        
        // Assert
        assertNotNull(result);
        assertEquals(employees, result);
    }
    @Test
    public void testCalculateCommissionIssuedToEmployeeByDepartment() {
        // Arrange
        BigDecimal departmentId = new BigDecimal(1);
        BigDecimal totalCommission = new BigDecimal(1000);
        
        when(employeeService.CalculateCommissionIssuedToEmployeeByDepartment(eq(departmentId))).thenReturn(totalCommission);
        
        // Act
        ResponseEntity<Map<String, Object>> response = employeeController.CalculateCommissionIssuedToEmployeeByDepartment(departmentId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(departmentId, response.getBody().get("departmentId"));
        assertEquals(totalCommission, response.getBody().get("sum"));
    }
    @Test
    public void testListAllEmployeesByDepartment() {
        // Arrange
        BigDecimal departmentId = new BigDecimal(1);
        List<Employees> employees = new ArrayList<>();
        // Add some employees to the list
        
        when(employeeService.listAllEmployeesByDepartment(eq(departmentId))).thenReturn(employees);
        
        // Act
        ResponseEntity<List<Employees>> response = employeeController.listAllEmployeesByDepartment(departmentId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(employees, response.getBody());
    }
    @Test
    public void testCountAllEmployeesGroupByDepartment() {
        // Arrange
        List<Map<String, Object>> response = new ArrayList<>();
        // Add some maps to the response
        
        when(employeeService.countAllEmployeesGroupByDepartment()).thenReturn(response);
        
        // Act
        ResponseEntity<List<Map<String, Object>>> result = employeeController.countAllEmployeesGroupByDepartment();
        
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(response, result.getBody());
    }
    @Test
    public void testListAllManagerDetails() {
        // Arrange
        List<Employees> managers = new ArrayList<>();
        // Add some managers to the list
        
        when(employeeService.getAllManagers()).thenReturn(managers);
        
        // Act
        ResponseEntity<List<Employees>> response = employeeController.listAllManagerDetails();
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(managers, response.getBody());
    }
    @Test
    public void testListAllEmployeesByLocation() {
        // Arrange
        Map<BigDecimal, List<Employees>> employeesByLocation = new HashMap<>();
        // Add some entries to the map
        
        when(employeeService.getAllEmployeesByLocation()).thenReturn(employeesByLocation);
        
        // Act
        ResponseEntity<Map<BigDecimal, List<Employees>>> response = employeeController.listAllEmployeesByLocation();
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(employeesByLocation, response.getBody());
    }
    @Test
    public void testFindMaxSalaryOfJobByEmployeeId() {
        // Arrange
        BigDecimal employeeId = new BigDecimal(1);
        Map<String, Object> response = new HashMap<>();
        // Add some entries to the response
        
        when(employeeService.findMaxSalaryOfJobByEmployeeId(eq(employeeId))).thenReturn(response);
        
        // Act
        ResponseEntity<Map<String, Object>> result = employeeController.findMaxSalaryOfJobByEmployeeId(employeeId);
        
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(response, result.getBody());
    }
    @Test
    public void testUpdateEmployeeEmail() {
        // Arrange
        String email = "old@example.com";
        String newEmail = "new@example.com";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", newEmail);
        
        when(employeeService.updateEmployeeEmail(eq(email), eq(newEmail))).thenReturn(true);
        
        // Act
        ResponseEntity<String> response = employeeController.updateEmployeeEmail(email, requestBody);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record Modified Successfully", response.getBody());
    }
    @Test
    public void testUpdateEmployeePhoneNumber() {
        // Arrange
        String phoneNumber = "1234567890";
        String newPhoneNumber = "9876543210";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("phoneNumber", newPhoneNumber);
        
        when(employeeService.updateEmployeePhoneNumber(eq(phoneNumber), eq(newPhoneNumber))).thenReturn(true);
        
        // Act
        ResponseEntity<String> response = employeeController.updateEmployeePhoneNumber(phoneNumber, requestBody);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record Modified Successfully", response.getBody());
    }
    @Test
    public void testListEmployeesByHireDateRange() {
        // Arrange
        Date fromHireDate = new Date();
        Date toHireDate = new Date();
        List<Employees> employees = new ArrayList<>();
        // Add some employees to the list
        
        when(employeeService.listEmployeesByHireDateRange(eq(fromHireDate), eq(toHireDate))).thenReturn(employees);
        
        // Act
        List<Employees> result = employeeController.listEmployeesByHireDateRange(fromHireDate, toHireDate);
        
        // Assert
        assertNotNull(result);
        assertEquals(employees, result);
        // Add additional assertions if needed
    }
    @Test
    public void testDeleteEmployeeById() {
        // Arrange
        BigDecimal empId = new BigDecimal(1);
        
        // Act
        ResponseEntity<String> response = employeeController.deleteEmployeeById(empId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee with ID " + empId + " has been deleted.", response.getBody());
        verify(employeeService, times(1)).deleteEmployeeById(eq(empId));
    }
    @Test
    public void testGetAllEmployeesByDepartmentId() {
        // Arrange
        BigDecimal departmentId = new BigDecimal(1);
        List<Employees> employees = new ArrayList<>();
        // Add some employees to the list
        
        when(employeeService.getEmployeesByDepartmentId(eq(departmentId))).thenReturn(employees);
        
        // Act
        ResponseEntity<List<Employees>> response = employeeController.getAllEmployeesByDepartmentId(departmentId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(employees, response.getBody());
        // Add additional assertions if needed
    }
    @Test
    public void testFindAllEmployeesWithCommission() {
        // Arrange
        List<Employees> employees = new ArrayList<>();
        // Add some employees to the list
        
        when(employeeService.findAllEmployeesWithCommission()).thenReturn(employees);
        
        // Act
        ResponseEntity<List<Employees>> response = employeeController.findAllEmployeesWithCommission();
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(employees, response.getBody());
        // Add additional assertions if needed
    }
    @Test
    public void testGetEmployeePhone() {
        // Arrange
        BigDecimal employeeId = new BigDecimal(1);
        String phoneNumber = "1234567890";
        
        Employees employee = new Employees();
        employee.setPhoneNumber(phoneNumber);
        
        when(employeeService.getEmployeeById(eq(employeeId))).thenReturn(employee);
        
        // Act
        ResponseEntity<String> response = employeeController.getEmployeePhone(employeeId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(phoneNumber, response.getBody());
        // Add additional assertions if needed
    }
    @Test
    public void testGetEmployeeEmailById() {
        // Arrange
        BigDecimal employeeId = new BigDecimal(1);
        String email = "test@example.com";
        
        when(employeeService.getEmployeeEmailById(eq(employeeId))).thenReturn(email);
        
        // Act
        ResponseEntity<String> response = employeeController.getEmployeeEmailById(employeeId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(email, response.getBody());
        // Add additional assertions if needed
    }
    @Test
    public void testUpdateEmployeeJob() {
        // Arrange
        BigDecimal employeeId = new BigDecimal(1);
        String jobId = "JOB123";
        
        Employees updatedEmployee = new Employees();
        
        when(employeeService.updateEmployeeJob(eq(employeeId), eq(jobId))).thenReturn(updatedEmployee);
        
        // Act
        ResponseEntity<String> response = employeeController.updateEmployeeJob(employeeId, jobId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record Modified Successfully", response.getBody());
        // Add additional assertions if needed
    }
    @Test
    public void testUpdateEmployeeName() {
        // Arrange
        BigDecimal employeeId = new BigDecimal(1);
        String firstName = "John";
        String lastName = "Doe";
        
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("firstName", firstName);
        requestBody.put("lastName", lastName);
        
        Employees employee = new Employees();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        
        Optional<Employees> optionalEmployee = Optional.of(employee);
        when(employeesRepository.findById(eq(employeeId))).thenReturn(optionalEmployee);
        when(employeesRepository.save(any(Employees.class))).thenReturn(employee);
        
        // Act
        ResponseEntity<String> response = employeeController.updateEmployeeName(employeeId, requestBody);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record Modified Successfully", response.getBody());
        // Add additional assertions if needed
    }
}