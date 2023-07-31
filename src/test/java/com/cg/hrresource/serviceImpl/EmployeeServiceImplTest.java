package com.cg.hrresource.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.hrresource.entities.Employees;
import com.cg.hrresource.exception.CustomException;
import com.cg.hrresource.repository.EmployeesRepository;
import com.cg.hrresource.service.EmployeeServiceImpl;

public class EmployeeServiceImplTest {

	@Mock
	private EmployeesRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
    void testFindAllEmployeeWithNoCommission() {
        // Mock the behavior of the repository
        when(employeeRepository.findByCommissionPctIsNull()).thenReturn(Collections.emptyList());

        // Call the method under test
        List<Employees> result = employeeService.findAllEmployeeWithNoCommission();

        // Perform assertions
        assertTrue(result.isEmpty());

        // Verify that the repository method was called
        verify(employeeRepository).findByCommissionPctIsNull();
    }
	@Test
    void testCalculateCommissionIssuedToEmployeeByDepartment() {
        // Arrange
        BigDecimal departmentId = BigDecimal.valueOf(123);
        BigDecimal expectedCommission = BigDecimal.valueOf(1000);
        when(employeeRepository.calculateTotalCommissionByDepartment(departmentId)).thenReturn(expectedCommission);

        // Act
        BigDecimal result = employeeService.CalculateCommissionIssuedToEmployeeByDepartment(departmentId);

        // Assert
        Assertions.assertEquals(expectedCommission, result);
    }
	@Test
    void testCountAllEmployeesGroupByDepartment() {
        // Arrange
        List<Object[]> results = new ArrayList<>();
        Object[] result1 = {"Department 1", 10L};
        Object[] result2 = {"Department 2", 20L};
        results.add(result1);
        results.add(result2);

        when(employeeRepository.countAllEmployeesGroupByDepartment()).thenReturn(results);

        // Act
        List<Map<String, Object>> response = employeeService.countAllEmployeesGroupByDepartment();

        // Assert
        Assertions.assertEquals(2, response.size());

        Map<String, Object> entry1 = response.get(0);
        Assertions.assertEquals("Department 1", entry1.get("departmentName"));
        Assertions.assertEquals(10L, entry1.get("count"));

        Map<String, Object> entry2 = response.get(1);
        Assertions.assertEquals("Department 2", entry2.get("departmentName"));
        Assertions.assertEquals(20L, entry2.get("count"));
    }
	 @Test
	    void testUpdateEmployee_ValidEmployee_SaveCalled() {
	        // Arrange
	        Employees employee = new Employees();
	        when(employeeRepository.save(any(Employees.class))).thenReturn(employee);

	        // Act
	        employeeService.updateEmployee(employee);

	        // Assert
	        verify(employeeRepository, times(1)).save(employee);
	    }
	 @Test
	    void testFindByFirstName_ValidFirstName_EmployeesFound() {
	        // Arrange
	        String firstName = "John";
	        List<Employees> employeesList = new ArrayList<>();
	        employeesList.add(new Employees());
	        when(employeeRepository.findByFirstName(firstName)).thenReturn(employeesList);

	        // Act
	        List<Employees> result = employeeService.findByFirstName(firstName);

	        // Assert
	        Assertions.assertEquals(employeesList, result);
	    }
	 @Test
	    void testFindByEmail_ValidEmail_EmployeeFound() {
	        // Arrange
	        String email = "john@example.com";
	        Employees employee = new Employees();
	        when(employeeRepository.findByEmail(email)).thenReturn(employee);

	        // Act
	        Employees result = employeeService.findByEmail(email);

	        // Assert
	        Assertions.assertEquals(employee, result);
	    }
	 @Test
	    void testGetEmployeeEmailById_WithExistingEmployee_ReturnsEmail() {
	        BigDecimal employeeId = BigDecimal.valueOf(1);
	        String expectedEmail = "john.doe@example.com";
	        Employees employee = new Employees();
	        employee.setEmail(expectedEmail);
	        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

	        String actualEmail = employeeService.getEmployeeEmailById(employeeId);

	        assertEquals(expectedEmail, actualEmail);
	        verify(employeeRepository, times(1)).findById(employeeId);
	    }
	 @Test
	    void testGetEmployeeEmailById_WithNonExistingEmployee_ReturnsNull() {
	        BigDecimal employeeId = BigDecimal.valueOf(1);
	        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

	        String email = employeeService.getEmployeeEmailById(employeeId);

	        assertNull(email);
	        verify(employeeRepository, times(1)).findById(employeeId);
	    }
	 @Test
	    void testFindAllEmployeesWithCommission_WithExistingEmployees_ReturnsEmployeesList() {
	        List<Employees> expectedEmployees = new ArrayList<>();
	        expectedEmployees.add(new Employees());
	        expectedEmployees.add(new Employees());
	        when(employeeRepository.findByCommissionPctIsNotNull()).thenReturn(expectedEmployees);

	        List<Employees> actualEmployees = employeeService.findAllEmployeesWithCommission();

	        assertEquals(expectedEmployees, actualEmployees);
	        verify(employeeRepository, times(1)).findByCommissionPctIsNotNull();
	    }
	 @Test
	    void testDeleteEmployeeById() {
	        BigDecimal empId = BigDecimal.valueOf(1);

	        assertDoesNotThrow(() -> employeeService.deleteEmployeeById(empId));
	        verify(employeeRepository, times(1)).deleteById(empId);
	    }
	 @Test
	    void testFindMaxSalaryOfJobByEmployeeId_WithExistingResult_ReturnsResult() {
	        BigDecimal employeeId = BigDecimal.valueOf(1);
	        Map<String, Object> expectedResult = new HashMap<>();
	        expectedResult.put("maxSalary", BigDecimal.valueOf(5000));

	        when(employeeRepository.findMaxSalaryOfJobByEmployeeId(employeeId)).thenReturn(expectedResult);

	        Map<String, Object> result = employeeService.findMaxSalaryOfJobByEmployeeId(employeeId);

	        assertEquals(expectedResult, result);
	        verify(employeeRepository, times(1)).findMaxSalaryOfJobByEmployeeId(employeeId);
	    }
	 @Test
	    void testFindMaxSalaryOfJobByEmployeeId_WithNoResult_ThrowsException() {
	        BigDecimal employeeId = BigDecimal.valueOf(1);

	        when(employeeRepository.findMaxSalaryOfJobByEmployeeId(employeeId)).thenReturn(null);

	        assertThrows(CustomException.class,
	                () -> employeeService.findMaxSalaryOfJobByEmployeeId(employeeId));

	        verify(employeeRepository, times(1)).findMaxSalaryOfJobByEmployeeId(employeeId);
	    }
	 @Test
	    void testListEmployeesByHireDateRange_WithEmployeesWithinRange_ReturnsEmployeesList() {
	        Date fromHireDate = new Date();
	        Date toHireDate = new Date();
	        List<Employees> expectedEmployees = new ArrayList<>();
	        expectedEmployees.add(new Employees());
	        expectedEmployees.add(new Employees());

	        when(employeeRepository.findAllByHireDateBetween(fromHireDate, toHireDate)).thenReturn(expectedEmployees);

	        List<Employees> actualEmployees = employeeService.listEmployeesByHireDateRange(fromHireDate, toHireDate);

	        assertEquals(expectedEmployees, actualEmployees);
	        verify(employeeRepository, times(1)).findAllByHireDateBetween(fromHireDate, toHireDate);
	    }
	 @Test
	    void testListEmployeesByHireDateRange_WithNoEmployeesWithinRange_ThrowsException() {
	        Date fromHireDate = new Date();
	        Date toHireDate = new Date();

	        when(employeeRepository.findAllByHireDateBetween(fromHireDate, toHireDate)).thenReturn(Collections.emptyList());

	        assertThrows(CustomException.class,
	                () -> employeeService.listEmployeesByHireDateRange(fromHireDate, toHireDate));

	        verify(employeeRepository, times(1)).findAllByHireDateBetween(fromHireDate, toHireDate);
	    }
	 
}
