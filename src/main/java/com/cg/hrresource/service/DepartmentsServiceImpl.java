package com.cg.hrresource.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrresource.entities.Departments;
import com.cg.hrresource.exception.CustomException;
import com.cg.hrresource.repository.DepartmentsRepository;
import com.cg.hrresource.repository.EmployeesRepository;


@Service
public class DepartmentsServiceImpl implements DepartmentsService {

	@Autowired
	private DepartmentsRepository departmentsRepository;

	@Autowired
	private EmployeesRepository employeesRepository;

	@Override
	public Departments createDepartment(Departments department) {
		return departmentsRepository.save(department);
	}

	@Override
    public Departments updateDepartment(BigDecimal departmentId, Departments updatedDepartment) {
        Optional<Departments> optionalDepartment = departmentsRepository.findById(departmentId);
        if (optionalDepartment.isPresent()) {
            Departments existingDepartment = optionalDepartment.get();
            existingDepartment.setDepartmentName(updatedDepartment.getDepartmentName());
            existingDepartment.setLocation(updatedDepartment.getLocation());
            return departmentsRepository.save(existingDepartment);
        }
        throw new CustomException("details not found");
    }

	@Override
	public List<Departments> getDepartmentsByEmployeeId(BigDecimal employeeId) {
		List<Departments> findByManagerEmployeeId = departmentsRepository.findByManagerEmployeeId(employeeId);
		if (findByManagerEmployeeId.size() > 0) {
			return findByManagerEmployeeId;
		}
		throw new CustomException("Department not found");

	}

	@Override
	public void deleteDepartment(BigDecimal departmentId) {
		departmentsRepository.deleteById(departmentId);
	}

	// for max salary

	@Override
    public int getMaxSalaryByDepartmentId(BigDecimal departmentId) {
        Departments max = departmentsRepository.findByDepartmentId(departmentId);
        if (max == null) {
            throw new CustomException("Department not found ");
        }
        return employeesRepository.findMaxSalaryByDepartmentId(departmentId);
    }

	// for min salary

	@Override
    public int getMinSalaryByDepartmentId(BigDecimal departmentId) {
        Departments dept = departmentsRepository.findByDepartmentId(departmentId);
        if(dept == null) {
            throw new CustomException("Department not found");
        }
        return employeesRepository.findMinSalaryByDepartmentId(departmentId);
    }

	// front end extra methods

	/// Get Department name by department ID

	@Override
	public String getDepartmentNameById(BigDecimal departmentId) {
		Departments department = departmentsRepository.findById(departmentId).orElse(null);
		return department != null ? department.getDepartmentName() : null;
	}

	// max and min salary by dept Id

	@Override
	public Map<String, Object> getSalaryStatsByDepartmentId(BigDecimal departmentId) {
		java.util.Optional<Departments> optionalDepartment = departmentsRepository.findById(departmentId);
		String departmentName = optionalDepartment.map(Departments::getDepartmentName).orElse(null);

		int maxSalary = departmentsRepository.getMaxSalaryByDepartmentId(departmentId);
		int minSalary = departmentsRepository.getMinSalaryByDepartmentId(departmentId);

		Map<String, Object> response = new HashMap<>();
		response.put("department_name", departmentName);
		response.put("max_salary", maxSalary);
		response.put("min_salary", minSalary);

		return response;
	}

	// get all the department name and id
	// get all departments

	@Override
	public List<Departments> getAllDepartments() {
		return departmentsRepository.findAll();
	}
}
