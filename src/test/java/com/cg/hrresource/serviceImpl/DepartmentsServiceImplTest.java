package com.cg.hrresource.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.hrresource.entities.Departments;
import com.cg.hrresource.repository.DepartmentsRepository;
import com.cg.hrresource.repository.EmployeesRepository;
import com.cg.hrresource.service.DepartmentsServiceImpl;

@ExtendWith(MockitoExtension.class)
class DepartmentsServiceImplTest {

    @Mock
    private DepartmentsRepository departmentsRepository;

    @Mock
    private EmployeesRepository employeesRepository;

    @InjectMocks
    private DepartmentsServiceImpl departmentsService;

    @Test
    void testCreateDepartment() {
        // Mock data
        Departments department = new Departments();

        // Mock the repository method
        when(departmentsRepository.save(department)).thenReturn(department);

        // Invoke the service method
        Departments createdDepartment = departmentsService.createDepartment(department);

        // Verify the repository method is called
        verify(departmentsRepository).save(department);

        // Verify the result
        assertEquals(department, createdDepartment);
    }

    @Test
    void testUpdateDepartment() {
        // Mock data
        BigDecimal departmentId = new BigDecimal(123);
        Departments updatedDepartment = new Departments();
        updatedDepartment.setDepartmentName("New Name");
        updatedDepartment.getLocation();

        Departments existingDepartment = new Departments();
        existingDepartment.setDepartmentId(departmentId);

        // Mock the repository method
        when(departmentsRepository.findById(departmentId)).thenReturn(Optional.of(existingDepartment));
        when(departmentsRepository.save(existingDepartment)).thenReturn(existingDepartment);

        // Invoke the service method
        Departments result = departmentsService.updateDepartment(departmentId, updatedDepartment);

        // Verify the repository methods are called
        verify(departmentsRepository).findById(departmentId);
        verify(departmentsRepository).save(existingDepartment);

        // Verify the result
        assertEquals(existingDepartment, result);
        assertEquals(updatedDepartment.getDepartmentName(), existingDepartment.getDepartmentName());
        assertEquals(updatedDepartment.getLocation(), existingDepartment.getLocation());
    }

    

    @Test
    void testDeleteDepartment() {
        // Mock data
        BigDecimal departmentId = new BigDecimal(123);

        // Invoke the service method
        departmentsService.deleteDepartment(departmentId);

        // Verify the repository method is called
        verify(departmentsRepository).deleteById(departmentId);
    }

    @Test
    void testGetMaxSalaryByDepartmentId() {
        // Mock data
        BigDecimal departmentId = new BigDecimal(123);
        Departments department = new Departments();

        // Mock the repository methods
        when(departmentsRepository.findByDepartmentId(departmentId)).thenReturn(department);
        when(employeesRepository.findMaxSalaryByDepartmentId(departmentId)).thenReturn(10000);

        // Invoke the service method
        int result = departmentsService.getMaxSalaryByDepartmentId(departmentId);

        // Verify the repository methods are called
        verify(departmentsRepository).findByDepartmentId(departmentId);
        verify(employeesRepository).findMaxSalaryByDepartmentId(departmentId);

        // Verify the result
        assertEquals(10000, result);
    }

    @Test
    void testGetMinSalaryByDepartmentId() {
        // Mock data
        BigDecimal departmentId = new BigDecimal(123);
        Departments department = new Departments();

        // Mock the repository methods
        when(departmentsRepository.findByDepartmentId(departmentId)).thenReturn(department);
        when(employeesRepository.findMinSalaryByDepartmentId(departmentId)).thenReturn(5000);

        // Invoke the service method
        int result = departmentsService.getMinSalaryByDepartmentId(departmentId);

        // Verify the repository methods are called
        verify(departmentsRepository).findByDepartmentId(departmentId);
        verify(employeesRepository).findMinSalaryByDepartmentId(departmentId);

        // Verify the result
        assertEquals(5000, result);
    }

    @Test
    void testGetDepartmentNameById() {
        // Mock data
        BigDecimal departmentId = new BigDecimal(123);
        Departments department = new Departments();
        department.setDepartmentName("Test Department");

        // Mock the repository method
        when(departmentsRepository.findById(departmentId)).thenReturn(Optional.of(department));

        // Invoke the service method
        String result = departmentsService.getDepartmentNameById(departmentId);

        // Verify the repository method is called
        verify(departmentsRepository).findById(departmentId);

        // Verify the result
        assertEquals(department.getDepartmentName(), result);
    }

    @Test
    void testGetSalaryStatsByDepartmentId() {
        // Mock data
        BigDecimal departmentId = new BigDecimal(123);
        Departments department = new Departments();
        department.setDepartmentName("Test Department");

        // Mock the repository methods
        when(departmentsRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(departmentsRepository.getMaxSalaryByDepartmentId(departmentId)).thenReturn(10000);
        when(departmentsRepository.getMinSalaryByDepartmentId(departmentId)).thenReturn(5000);

        // Invoke the service method
        Map<String, Object> result = departmentsService.getSalaryStatsByDepartmentId(departmentId);

        // Verify the repository methods are called
        verify(departmentsRepository).findById(departmentId);
        verify(departmentsRepository).getMaxSalaryByDepartmentId(departmentId);
        verify(departmentsRepository).getMinSalaryByDepartmentId(departmentId);

        // Verify the result
        assertEquals(department.getDepartmentName(), result.get("department_name"));
        assertEquals(10000, result.get("max_salary"));
        assertEquals(5000, result.get("min_salary"));
    }

    @Test
    void testGetAllDepartments() {
        // Mock data
        List<Departments> departments = new ArrayList<>();

        // Mock the repository method
        when(departmentsRepository.findAll()).thenReturn(departments);

        // Invoke the service method
        List<Departments> result = departmentsService.getAllDepartments();

        // Verify the repository method is called
        verify(departmentsRepository).findAll();

        // Verify the result
        assertEquals(departments, result);
    }
}
