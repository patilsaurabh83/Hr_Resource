package com.cg.hrresource.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "employees")
@SequenceGenerator(name = "employeeId", sequenceName = "employeeId-Id", allocationSize = 1, initialValue = 206)
public class Employees implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeeId")
	@Column(name = "employee_id", columnDefinition = "decimal(6,0)")
	private BigDecimal employeeId;

	@Column(name = "first_name", columnDefinition = "varchar(20)")
	private String firstName;

	@Column(name = "last_name", columnDefinition = "varchar(25)")
	private String lastName;

	@Column(name = "email", unique = true, columnDefinition = "varchar(25)")
	private String email;

	@Column(name = "phone_number", columnDefinition = "varchar(25)")
	private String phoneNumber;

	@Column(name = "hire_date")
	@Temporal(TemporalType.DATE)
	private Date hireDate;

	@ManyToOne
//	@JsonIgnore
	@JoinColumn(name = "job_id", columnDefinition = "varchar(10)")
	private Jobs job;

	@Column(name = "salary", columnDefinition = "decimal(8,2)")
	private BigDecimal salary;

	@Column(name = "commission_pct", columnDefinition = "decimal(2,2)")
	private BigDecimal commissionPct;

	@ManyToOne()
	@JsonIgnore
	@JoinColumn(name = "manager_id")
	private Employees manager;
	


	@OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Employees> employees;

	@ManyToOne
//    @JsonIgnore
	@JoinColumn(name = "department_id", columnDefinition = "decimal(4,0)")
	private Departments department;

	public Employees() {
		super();
	}

	public Employees(BigDecimal employeeId, String firstName, String lastName, String email, String phoneNumber,
			Date hireDate, Jobs job, BigDecimal salary, BigDecimal commissionPct, Employees manager,
			Departments department) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.hireDate = hireDate;
		this.job = job;
		this.salary = salary;
		this.commissionPct = commissionPct;
		this.manager = manager;
		this.department = department;
	}

	public BigDecimal getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Jobs getJobId() {
		return job;
	}

	public void setJobId(Jobs job) {
		this.job = job;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public BigDecimal getCommissionPct() {
		return commissionPct;
	}

	public void setCommissionPct(BigDecimal commissionPct) {
		this.commissionPct = commissionPct;
	}

	public Employees getManager() {
		return manager;
	}

	public void setManager(Employees manager) {
		this.manager = manager;
	}

	public Departments getDepartment() {
		return department;
	}

	public void setDepartment(Departments department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employees [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", phoneNumber=" + phoneNumber + ", hireDate=" + hireDate + ", job=" + job
				+ ", salary=" + salary + ", commissionPct=" + commissionPct + "]";
	}

}