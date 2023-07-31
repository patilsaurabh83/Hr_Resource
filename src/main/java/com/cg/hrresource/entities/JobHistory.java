package com.cg.hrresource.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@IdClass(JobHistoryId.class)
@Table(name = "job_history")
public class JobHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(cascade = CascadeType.ALL)
//	@JsonIgnore
	@JoinColumn(name = "employee_id", insertable = false, updatable = false)
	private Employees employee;

	@ManyToOne
//	@JsonIgnore
	@JoinColumn(name = "job_id", nullable = false, columnDefinition = "varchar(10)")
	// insertable = false, updatable = false, -- removed part
	private Jobs job;

	@ManyToOne
//	@JsonIgnore
	@JoinColumn(name = "department_id", updatable = false)
//    insertable = false,   -- removed part
	private Departments department;

	@Id
	@Column(name = "start_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "end_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date endDate;

	public JobHistory() {
		super();
	}

	public JobHistory(Employees employee, Jobs job, Departments department, Date startDate, Date endDate) {
		this.employee = employee;
		this.job = job;
		this.department = department;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Employees getEmployee() {
		return employee;
	}

	public void setEmployee(Employees employee) {
		this.employee = employee;
	}

	public Jobs getJob() {
		return job;
	}

	public void setJob(Jobs job) {
		this.job = job;
	}

	public Departments getDepartment() {
		return department;
	}

	public void setDepartment(Departments department) {
		this.department = department;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "JobHistory [employee=" + employee + ", job=" + job + ", department=" + department + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}

}