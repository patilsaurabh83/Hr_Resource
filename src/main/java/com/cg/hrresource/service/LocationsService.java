package com.cg.hrresource.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.cg.hrresource.entities.Employees;
import com.cg.hrresource.entities.Locations;

public interface LocationsService {
	List<Locations> getAllLocations();

	Optional<Locations> getLocationById(BigDecimal id);

	Locations createLocation(Locations location);

	Locations updateLocation(Locations location);

	void deleteLocation(BigDecimal id);
	
	// give me name via id
    BigDecimal getLocationIdByCityName(String cityName);
    
 // get employess by location id

    List<Employees> getEmployeesByLocationId(BigDecimal locationId);
}