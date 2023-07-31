package com.cg.hrresource.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hrresource.entities.Countries;
import com.cg.hrresource.exception.InvalidException;
import com.cg.hrresource.service.CountriesService;

import jakarta.validation.Valid;

@Validated
@RestController

@RequestMapping("/api/v1")
public class CountriesController {

	@Autowired
	private CountriesService countryService;

	// ADD NEW COUNTRY -- POST

	@PostMapping("/admin/country")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> createdCountry(@Valid @RequestBody Countries country) {
		Countries createdCountry = countryService.createdCountry(country);
		if (createdCountry != null) {
			return new ResponseEntity<>("Record created Successfully", HttpStatus.OK);
		} else {
			throw new InvalidException("Country Not Found");
		}
	}

// MODIFY THE COUNTRY --

	@PutMapping("/admin/country")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> updateCountry(@Valid @RequestBody Countries country) {
		String updateCountry = countryService.updateCountry(country);
			return new ResponseEntity<>("Record Modified Successfully", HttpStatus.OK);
	}

	// LIST OF ALL COUNTRIES -- GET

	@GetMapping("/admin/country")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<Countries>> getAllCountries() {
		List<Countries> countries = countryService.getAllCountries();
		if (countries != null) {
			return ResponseEntity.ok(countries);
		} else {
			throw new InvalidException("No Country data Found");
		}
	}

// DELETE THE COUNTRY

	@DeleteMapping("/admin/country/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> deleteCountryById(@Valid @PathVariable String id) {
		countryService.deleteCountryById(id);
		return ResponseEntity.status(HttpStatus.OK).body("Record deleted Successfully");
	}

	// SEARCH COUNTRY BY ID -- GET

	@GetMapping("/employee/country/{id}")
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	public ResponseEntity<Optional<Countries>> getCountryById(@Valid @PathVariable String id) {
		Optional<Countries> country = countryService.getCountryById(id);
			return ResponseEntity.status(HttpStatus.OK).body(country);
		} 

}