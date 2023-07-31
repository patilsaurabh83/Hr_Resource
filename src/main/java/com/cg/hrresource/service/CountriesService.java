package com.cg.hrresource.service;

import java.util.List;
import java.util.Optional;

import com.cg.hrresource.entities.Countries;



public interface CountriesService {

	List<Countries> getAllCountries();

	void deleteCountryById(String id);

	Optional<Countries> getCountryById(String id);

	Countries createdCountry(Countries country);

	String updateCountry(Countries country);
}
