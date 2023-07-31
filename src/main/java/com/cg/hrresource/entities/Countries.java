package com.cg.hrresource.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "countries")
public class Countries implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "country_id", columnDefinition = "char(4)")
	private String countryId;

	@Column(name = "country_name", length = 60)
	private String countryName;

	@ManyToOne
//	@JsonIgnore
	@JoinColumn(name = "region_id", columnDefinition = "decimal(10,0)")
	private Regions region;

	public Countries() {
	}

	public Countries(String countryId, String countryName, Regions region) {
		this.countryId = countryId;
		this.countryName = countryName;
		this.region = region;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Regions getRegion() {
		return region;
	}

	public void setRegions(Regions region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "Countries [countryId=" + countryId + ", countryName=" + countryName + ", region=" + region + "]";
	}

}
