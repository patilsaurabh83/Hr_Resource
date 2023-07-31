package com.cg.hrresource.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrresource.entities.Departments;
import com.cg.hrresource.entities.Employees;
import com.cg.hrresource.entities.Jobs;
import com.cg.hrresource.entities.Locations;
import com.cg.hrresource.exception.CustomException;
import com.cg.hrresource.repository.DepartmentsRepository;
import com.cg.hrresource.repository.EmployeesRepository;
import com.cg.hrresource.repository.JobsRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@PersistenceContext
	private EntityManager entityManager;
//
	@Autowired
	private EmployeesRepository employeeRepository;

	@Autowired
	private DepartmentsRepository departmentsRepository;

	@Autowired
	private JobsRepository jobsRepository;



	@Override
	public void updateEmployee(Employees employee) {
		if (employee == null) {
			throw new CustomException("Invalid employee object provided.");
		}

		employeeRepository.save(employee);
	}

	@Override
	public List<Employees> findByFirstName(String firstName) {
		List<Employees> findByFirstName = employeeRepository.findByFirstName(firstName);
		if (findByFirstName.size() == 0) {
			throw new CustomException("name not found");
		}
		return findByFirstName;
	}

	@Override
	public Employees findByEmail(String email) {
		Employees employee = employeeRepository.findByEmail(email);
		if (employee == null) {
			throw new CustomException("No employee found with email: " + email);
		}
		return employee;
	}

	@Override
	public Employees findByPhoneNumber(String phoneNumber) {
		Employees employee = employeeRepository.findByPhoneNumber(phoneNumber);
		if (employee == null) {
			throw new CustomException("No employee found with the given phone number.");
		}
		return employee;
	}

	@Override
	public List<Employees> findAllEmployeeWithNoCommission() {
		return employeeRepository.findByCommissionPctIsNull();
	}

	@Override
	public BigDecimal CalculateCommissionIssuedToEmployeeByDepartment(BigDecimal departmentId) {
		return employeeRepository.calculateTotalCommissionByDepartment(departmentId);
	}

	@Override
	public List<Employees> listAllEmployeesByDepartment(BigDecimal departmentId) {
		List<Employees> findAllByDepartmentId = employeeRepository.findAllByDepartmentId(departmentId);
		if (findAllByDepartmentId.size() > 0) {
			return findAllByDepartmentId;
		} else {
			throw new CustomException("Customer not found");
		}
	}

	@Override
	public List<Map<String, Object>> countAllEmployeesGroupByDepartment() {
		List<Object[]> results = employeeRepository.countAllEmployeesGroupByDepartment();
		List<Map<String, Object>> response = new ArrayList<>();

		for (Object[] result : results) {
			Map<String, Object> entry = new HashMap<>();
			entry.put("departmentName", result[0]);
			entry.put("count", result[1]);
			response.add(entry);
		}

		return response;
	}

	@Override
	public List<Employees> getAllManagers() {
		return employeeRepository.findAllManagers();
	}

	public Map<BigDecimal, List<Employees>> getAllEmployeesByLocation() {
		List<Employees> employees = employeeRepository.findAll();

		Map<BigDecimal, List<Employees>> employeesByLocation = new HashMap<>();
		for (Employees employee : employees) {
			BigDecimal locationId = null;
			Departments department = employee.getDepartment();
			if (department != null) {
				Locations location = department.getLocation();
				if (location != null) {
					locationId = location.getLocationId();
					List<Employees> locationEmployees = employeesByLocation.getOrDefault(locationId, new ArrayList<>());
					locationEmployees.add(employee);
					employeesByLocation.put(locationId, locationEmployees);
				}

			}

		}
		return employeesByLocation;
	}

	@Override
	public void deleteEmployeeById(BigDecimal empId) {
		employeeRepository.deleteById(empId);
	}

	@Override
	public Map<String, Object> findMaxSalaryOfJobByEmployeeId(BigDecimal employeeId) {
		Map<String, Object> result = employeeRepository.findMaxSalaryOfJobByEmployeeId(employeeId);

		if (result == null || result.isEmpty()) {
			throw new CustomException("No maximum salary found for employee with ID: " + employeeId);
		}

		return result;
	}

	// ---------------------------------------------------------------------------------------------

	// working excp...

	@Override

	public boolean updateEmployeeEmail(String email, String newEmail) {

		Employees employee = employeeRepository.findByEmail(email);

		if (employee == null) {

			throw new CustomException("Employee with email " + email + " not found.");

		}

		employee.setEmail(newEmail);

		employeeRepository.save(employee);

		return true; // Record modified successfully

	}

	@Override
	public boolean updateEmployeePhoneNumber(String phoneNumber, String newPhoneNumber) {
		Employees employee = employeeRepository.findByPhoneNumber(phoneNumber);
		if (employee == null) {
			throw new CustomException("Employee with phone number " + phoneNumber + " not found.");
		}

		employee.setPhoneNumber(newPhoneNumber);
		employeeRepository.save(employee);
		return true; // Record modified successfully
	}

	@Override
	public List<Employees> listEmployeesByHireDateRange(Date fromHireDate, Date toHireDate) {
		List<Employees> employees = employeeRepository.findAllByHireDateBetween(fromHireDate, toHireDate);

		if (employees.isEmpty()) {
			throw new CustomException("No employees found within the hire date range");
		}

		return employees;
	}

	// to upadate the manager details
	@Override
	public Employees getEmployeeById(BigDecimal employeeId) {
		return employeeRepository.findById(employeeId)
				.orElseThrow(() -> new CustomException("Employee not found with ID: " + employeeId));
	}

	@Override
	public Employees saveEmployee(Employees employee) {
		return employeeRepository.save(employee);
	}

	// to update the employee department
	@Override
	public Employees saveEmployeeWithDepartment(BigDecimal employeeId, BigDecimal departmentId) {
		Employees employeeToUpdate = employeeRepository.findById(employeeId).orElse(null);
		Departments department = departmentsRepository.findById(departmentId).orElse(null);

		if (employeeToUpdate == null) {
			throw new CustomException("Employee not found.");
		} else if (department == null) {
			throw new CustomException("Department not found.");
		}

		employeeToUpdate.setDepartment(department);
		return employeeRepository.save(employeeToUpdate);
	}

	// to assign job to employee

	@Override
	public Jobs getJobById(String jobId) {
		return getJobById(jobId);
	}

	@Override
	public Employees updateEmployeeJob(String jobId) {
		Employees existingEmployee = employeeRepository.findByJobId(jobId);
		if (existingEmployee == null) {
			return null;
		}

		Jobs newJob = jobsRepository.findById(jobId).orElse(null);
		if (newJob == null) {
			return null;
		}

		existingEmployee.setJobId(newJob);
		return employeeRepository.save(existingEmployee);
	}

	@Override
	@Transactional
	public void assignDepartmentAndUpdateCommission(BigDecimal employeeId, BigDecimal departmentId,
			BigDecimal commissionPct) {
		List<Employees> salesEmployees = employeeRepository.findByDepartmentDepartmentName("Sales");
		for (Employees employee : salesEmployees) {
			employee.setCommissionPct(commissionPct);
			employeeRepository.save(employee);
		}

		// Update department and sales commission for the specific employee
		Employees emp = saveEmployeeWithDepartment(employeeId, departmentId);
		if (emp.getDepartment().getDepartmentName().equalsIgnoreCase("Sales")) {
			emp.setCommissionPct(commissionPct);
		}
		employeeRepository.save(emp);

		if (salesEmployees.isEmpty() || emp == null) {
			throw new CustomException("Error assigning department and updating commission.");
		}
	}

	@Override
	public Employees getEmployeeById(Long employeeId) {
		return employeeRepository.findByEmployeeId(employeeId);
	}

	// ----------------
	// Front end Extra methods

	// get all employees by dept ID
	@Override
	public List<Employees> getEmployeesByDepartmentId(BigDecimal departmentId) {
		return employeeRepository.findAllByDepartmentId(departmentId);
	}

	// find Employee who got commision
	@Override
	public List<Employees> findAllEmployeesWithCommission() {
		return employeeRepository.findByCommissionPctIsNotNull();
	}

	// find name by employee Id

	@Override
	public Employees getEmployeeNameById(BigDecimal employeeId) {
		return employeeRepository.findByEmployeeId(employeeId);
	}

	// get manager name by manager Id
	@Override
	public Employees getManagerNameById(BigDecimal managerId) {
		return employeeRepository.findByEmployeeId(managerId);
	}

	// get emp email by id

	@Override
	public String getEmployeeEmailById(BigDecimal employeeId) {
		Employees employee = employeeRepository.findById(employeeId).orElse(null);
		if (employee != null) {
			return employee.getEmail();
		}
		return null;
	}

	// Assign EmployeeId Using Job Id

	@Override
	public Employees updateEmployeeJob(BigDecimal employeeId, String jobId) {
		java.util.Optional<Employees> optionalEmployee = employeeRepository.findById(employeeId);
		java.util.Optional<Jobs> optionalJob = jobsRepository.findById(jobId);

		if (optionalEmployee.isPresent() && optionalJob.isPresent()) {
			Employees employee = optionalEmployee.get();
			Jobs job = optionalJob.get();
			employee.setJobId(job);

			return employeeRepository.save(employee);
		} else {
			throw new CustomException("Job not found.");
		}
	}

}