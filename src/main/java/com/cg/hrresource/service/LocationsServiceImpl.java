package com.cg.hrresource.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrresource.entities.Employees;
import com.cg.hrresource.entities.Locations;
import com.cg.hrresource.repository.EmployeesRepository;
import com.cg.hrresource.repository.LocationsRepository;

@Service
public class LocationsServiceImpl implements LocationsService {
    
	@Autowired
	private LocationsRepository locationsRepository;
	
	@Autowired
	private EmployeesRepository employeesRepository;

	@Autowired
	public LocationsServiceImpl(LocationsRepository locationsRepository) {
		this.locationsRepository = locationsRepository;
	}

	@Override
	public List<Locations> getAllLocations() {
		return locationsRepository.findAll();
	}

	@Override
	public Optional<Locations> getLocationById(BigDecimal id) {
		return locationsRepository.findById(id);
	}

	@Override
	public Locations createLocation(Locations location) {
		return locationsRepository.save(location);
	}

	@Override
	public Locations updateLocation(Locations location) {
		BigDecimal id = location.getLocationId(); // Assuming id is present in the Locations entity
		if (locationsRepository.existsById(id)) {
			return locationsRepository.save(location);
		} else {
			throw new RuntimeException("Location not found with id: " + id);
		}
	}

	@Override
	public void deleteLocation(BigDecimal id) {
		locationsRepository.deleteById(id);
	}
	
	// extra front end methods
	
	// give me name via id
    @Override
    public BigDecimal getLocationIdByCityName(String cityName) {
        return locationsRepository.findLocationIdByCityName(cityName);
    }
    
 // get employees details via lct id
    @Override
    public List<Employees> getEmployeesByLocationId(BigDecimal locationId) {
        return employeesRepository.findEmployeesByLocationId(locationId);
    }

}
