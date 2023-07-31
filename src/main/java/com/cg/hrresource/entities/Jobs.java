package com.cg.hrresource.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "jobs")
public class Jobs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Column(name = "job_id", nullable = false, columnDefinition = "varchar(10)")
	private String jobId;

	@Column(name = "job_title", nullable = false, columnDefinition = "varchar(35)")
	private String jobTitle;

	@Column(name = "min_salary", columnDefinition = "decimal(6,0)")
	private BigDecimal minSalary;

	@Column(name = "max_salary", columnDefinition = "decimal(6,0)")
	private BigDecimal maxSalary;

	@OneToMany(mappedBy = "job")
	@JsonIgnore
	private List<Employees> employees;

	public Jobs() {
		super();
	}

	public List<Employees> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employees> employees) {
		this.employees = employees;
	}

	public Jobs(String jobId, String jobTitle, BigDecimal minSalary, BigDecimal maxSalary, List<Employees> employees) {
		super();
		this.jobId = jobId;
		this.jobTitle = jobTitle;
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
		this.employees = employees;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public BigDecimal getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(BigDecimal minSalary) {
		this.minSalary = minSalary;
	}

	public BigDecimal getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(BigDecimal maxSalary) {
		this.maxSalary = maxSalary;
	}

	@Override
	public String toString() {
		return "Jobs [jobId=" + jobId + ", jobTitle=" + jobTitle + ", minSalary=" + minSalary + ", maxSalary="
				+ maxSalary + ", employees=" + employees + "]";
	}

}
