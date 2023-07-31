package com.cg.hrresource.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class Regions implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "region_id", columnDefinition = "decimal(10,0)")
	private BigDecimal regionId;

	@Column(name = "region_name", columnDefinition = "varchar(25)")
	private String regionName;

	public Regions(BigDecimal regionId, String regionName) {
		this.regionId = regionId;
		this.regionName = regionName;
	}

	public Regions() {
		super();
	}

	public BigDecimal getRegionId() {

		return regionId;
	}

	public void setRegionId(BigDecimal regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Override
	public String toString() {
		return "Regions [regionId=" + regionId + ", regionName=" + regionName + "]";
	}

}