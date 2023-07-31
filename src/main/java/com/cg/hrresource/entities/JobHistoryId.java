
package com.cg.hrresource.entities;

import java.io.Serializable;

import java.util.Date;

import java.util.Objects;

public class JobHistoryId implements Serializable {

	private Employees employee;

	private Date startDate;

	public JobHistoryId() {

		super();

	}

	public JobHistoryId(Employees employee, Date startDate) {

		super();

		this.employee = employee;

		this.startDate = startDate;

	}

	public Employees getEmployee() {

		return employee;

	}

	public Date getStartDate() {

		return startDate;

	}

	public void setEmployee(Employees employee) {

		this.employee = employee;

	}

	public void setStartDate(Date startDate) {

		this.startDate = startDate;

	}

	@Override

	public int hashCode() {

		return Objects.hash(employee, startDate);

	}

	@Override

	public boolean equals(Object obj) {

		if (this == obj)

			return true;

		if (obj == null)

			return false;

		if (getClass() != obj.getClass())

			return false;

		JobHistoryId other = (JobHistoryId) obj;

		return Objects.equals(employee, other.employee) && Objects.equals(startDate, other.startDate);

	}

}