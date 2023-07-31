package com.cg.hrresource.controller;

import java.math.BigDecimal;
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

import com.cg.hrresource.entities.Employees;
import com.cg.hrresource.entities.Locations;
import com.cg.hrresource.service.LocationsService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1")
public class LocationsController {

	@Autowired
	private LocationsService locationsService;

	@Autowired
	public LocationsController(LocationsService locationsService) {
		this.locationsService = locationsService;
	}

	// ADD NEW LOCATION

	@PostMapping("/admin/location")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> createLocation(@Valid @RequestBody Locations location) {
		Locations createdLocation = locationsService.createLocation(location);
		return new ResponseEntity<>("Record Created Successfully", HttpStatus.CREATED);
	}

	// MODIFY THE LOCATION

	@PutMapping("/admin/location")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> updateLocation(@Valid @RequestBody Locations location) {
		Locations updatedLocation = locationsService.updateLocation(location);
		return new ResponseEntity<>("Record Modified Successfully", HttpStatus.OK);
	}

	// GET ALL THE LOCATIONS

	@GetMapping("/public/locations")
	public ResponseEntity<List<Locations>> getAllLocations() {
		List<Locations> locations = locationsService.getAllLocations();
		return new ResponseEntity<>(locations, HttpStatus.OK);
	}

	// SEARCH LOCATION BY ID

	@GetMapping("/public/locations/{id}")
	public ResponseEntity<Locations> getLocationById(@Valid @PathVariable("id") BigDecimal id) {
		Optional<Locations> location = locationsService.getLocationById(id);
		return location.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// DELETE LOCATION BY ID

	@DeleteMapping("/admin/locations/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> deleteLocation(@Valid @PathVariable("id") BigDecimal id) {
		locationsService.deleteLocation(id);
		return new ResponseEntity<>("Record deleted Successfully", HttpStatus.OK);
	}
	
	// front end extra methods
	
	 // locations via id


    @GetMapping("/public/locations/{cityName}/locationId")
    public ResponseEntity<BigDecimal> getLocationIdByCityName(@PathVariable("cityName") String cityName) {
        BigDecimal locationId = locationsService.getLocationIdByCityName(cityName);
        if (locationId != null) {
            return ResponseEntity.ok(locationId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    //  get emp detail via lctid


    @GetMapping("/public/locations/locationwiseemployee/{locationId}")
    public ResponseEntity<List<Employees>> getEmployeesByLocationId(@PathVariable("locationId") BigDecimal locationId) {
        List<Employees> employees = locationsService.getEmployeesByLocationId(locationId);
        return ResponseEntity.ok(employees);
    }
}