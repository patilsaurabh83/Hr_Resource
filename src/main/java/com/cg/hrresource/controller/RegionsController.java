package com.cg.hrresource.controller;

import java.math.BigDecimal;
import java.util.List;

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

import com.cg.hrresource.entities.Regions;
import com.cg.hrresource.service.RegionsServiceImpl;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1")
public class RegionsController {

	@Autowired
	private RegionsServiceImpl regionService;

	// ADD NEW REAGION -- POST

	@PostMapping("/admin/region")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> createdRegion(@Valid @RequestBody Regions region) {
		Regions createdRegion = regionService.createdRegion(region);
		return new ResponseEntity<>("Record created Successfully", HttpStatus.OK);
	}

	// MODIFY THE REGION

	@PutMapping("/admin/region")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> updateRegion(@Valid @RequestBody Regions region) {
		Regions updateRegion = regionService.updateRegion(region);
		return new ResponseEntity<>("Record Modified Successfully", HttpStatus.OK);
	}

	// LIST OF ALL REGIONS

	@GetMapping("/admin/getAll/region")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Regions> getAllRegions() {
		return regionService.getAllRegions();
	}

	// GET REGION BY ID

	@GetMapping("/employee/region/{region_id}")
	public ResponseEntity<Regions> getRegionById(@Valid @PathVariable("region_id") BigDecimal regionId) {
		Regions region = regionService.getRegionById(regionId);
		if (region != null) {
			return ResponseEntity.status(HttpStatus.OK).body(region);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	// DELETE REGION BY ID

	@DeleteMapping("/admin/region/{region_id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> deleteRegionById(@Valid @PathVariable("region_id") BigDecimal regionId) {
		regionService.deleteRegionById(regionId);
		return ResponseEntity.status(HttpStatus.OK).body("Record deleted Successfully");
	}

}
