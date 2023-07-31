package com.cg.hrresource.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Locations implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "location_id", columnDefinition = "decimal(4,0)")
	private BigDecimal locationId;

	@Column(name = "street_address", length = 40)
	private String streetAddress;

	@Column(name = "postal_code", length = 12)
	private String postalCode;

	@Column(name = "city", length = 30, nullable = false)
	private String city;

	@Column(name = "state_province", length = 25)
	private String stateProvince;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "country_id")
	private Countries country;

	public Locations() {
		super();
	}

	public Locations(BigDecimal locationId, String streetAddress, String postalCode, String city, String stateProvince,
			Countries country) {
		this.locationId = locationId;
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.city = city;
		this.stateProvince = stateProvince;
		this.country = country;
	}

	public BigDecimal getLocationId() {
		return locationId;
	}

	public void setLocationId(BigDecimal locationId) {
		this.locationId = locationId;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public Countries getCountry() {
		return country;
	}

	public void setCountry(Countries country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Locations [locationId=" + locationId + ", streetAddress=" + streetAddress + ", postalCode=" + postalCode
				+ ", city=" + city + ", stateProvince=" + stateProvince + ", country=" + country + "]";
	}

}