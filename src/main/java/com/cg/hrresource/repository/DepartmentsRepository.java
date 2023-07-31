package com.cg.hrresource.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.hrresource.entities.Departments;

public interface DepartmentsRepository extends JpaRepository<Departments, BigDecimal> {

    @Query("SELECT d FROM Departments d WHERE d.manager.employeeId = :employeeId")
    List<Departments> findByManagerEmployeeId(@Param("employeeId") BigDecimal employeeId);
    
    
 // get min and mix salary by department id

    @Query("SELECT MAX(e.salary) FROM Employees e WHERE e.department.departmentId = :departmentId")
    int getMaxSalaryByDepartmentId(BigDecimal departmentId);

 

    @Query("SELECT MIN(e.salary) FROM Employees e WHERE e.department.departmentId = :departmentId")
    int getMinSalaryByDepartmentId(BigDecimal departmentId);

 

    Departments findByDepartmentId(BigDecimal name);

  
}
