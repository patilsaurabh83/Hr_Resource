package com.cg.hrresource.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.hrresource.entities.Locations;

public interface LocationsRepository extends JpaRepository<Locations, BigDecimal> {
	
 // get location id by location id
    
    @Query("SELECT location.locationId FROM Locations location WHERE location.city = :cityName")
    BigDecimal findLocationIdByCityName(@Param("cityName") String cityName);

}
