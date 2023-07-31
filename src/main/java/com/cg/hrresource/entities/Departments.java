package com.cg.hrresource.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "departments")
public class Departments implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Column(name = "department_id", columnDefinition = "decimal(4,0)")
	private BigDecimal departmentId;

	@Column(name = "department_name", columnDefinition = "varchar(30)", nullable = false)
	private String departmentName;

	@ManyToOne
	@JsonIgnore()
	@JoinColumn(name = "manager_id", referencedColumnName = "employee_id")
	private Employees manager;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "location_id")
	private Locations location;

	public Departments() {
		super();

	}

	public Departments(BigDecimal departmentId, String departmentName, Locations location, Employees manager) {
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.manager = manager;
		this.location = location;
	}

	public BigDecimal getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(BigDecimal departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Employees getManager() {
		return manager;
	}

	public void setManager(Employees manager) {
		this.manager = manager;
	}

	public Locations getLocation() {
		return location;
	}

	public void setLocation(Locations location) {
		this.location = location;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Departments [departmentId=" + departmentId + ", departmentName=" + departmentName + ", manager="
				+ manager + ", location=" + location + "]";
	}

}





















//
//package com.example.demo.entities;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "departments")
//public class Departments implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@Column(name = "department_id", columnDefinition = "decimal(4,0)")
//	private BigDecimal departmentId;
//
//	@Column(name = "department_name", columnDefinition = "varchar(30)",nullable = false)
//	private String departmentName;
//
//	@ManyToOne
//	@JsonIgnore
//	@JoinColumn(name = "manager_id", referencedColumnName = "employee_id")
//	//, insertable = false, updatable = false
//	private Employees manager;
//
//	@ManyToOne
//	@JsonIgnore
//	@JoinColumn(name = "location_id")
//	private Locations location;
//
//	public Departments() {
//		super();
//
//	}
//
//	public Departments(BigDecimal departmentId, String departmentName, Locations location, Employees manager) {
//		this.departmentId = departmentId;
//		this.departmentName = departmentName;
//		this.manager = manager;
//		this.location = location;
//	}
//
//	public Employees getManager() {
//		return manager;
//	}
//
//	public void setManager(Employees manager) {
//		this.manager = manager;
//	}
//
//	public BigDecimal getDepartmentId() {
//		return departmentId;
//	}
//
//	public void setDepartmentId(BigDecimal departmentId) {
//		this.departmentId = departmentId;
//	}
//
//	public String getDepartmentName() {
//		return departmentName;
//	}
//
//	public void setDepartmentName(String departmentName) {
//		this.departmentName = departmentName;
//	}
//
//	public Locations getLocation() {
//		return location;
//	}
//
//	public void setLocation(Locations location) {
//		this.location = location;
//	}
//
//	@Override
//	public String toString() {
//		return "Departments [departmentId=" + departmentId + ", departmentName=" + departmentName + ", manager="
//				+ manager + ", location=" + location + "]";
//	}
//
//}