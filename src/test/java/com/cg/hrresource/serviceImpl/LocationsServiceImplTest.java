package com.cg.hrresource.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.hrresource.entities.Locations;
import com.cg.hrresource.repository.LocationsRepository;
import com.cg.hrresource.service.LocationsServiceImpl;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

	@Mock
	private LocationsRepository locationsRepository;

	@InjectMocks
	private LocationsServiceImpl locationService;

	@Test
	void testGetAllLocations() {
		// Arrange
		List<Locations> locations = List.of(new Locations(), new Locations());
		when(locationsRepository.findAll()).thenReturn(locations);

		// Act
		List<Locations> result = locationService.getAllLocations();

		// Assert
		assertEquals(locations, result);
		verify(locationsRepository, times(1)).findAll();
	}

	@Test
	void testGetLocationById() {
		// Arrange
		BigDecimal id = BigDecimal.valueOf(1);
		Optional<Locations> location = Optional.of(new Locations());
		when(locationsRepository.findById(id)).thenReturn(location);

		// Act
		Optional<Locations> result = locationService.getLocationById(id);

		// Assert
		assertTrue(result.isPresent());
		assertEquals(location.get(), result.get());
		verify(locationsRepository, times(1)).findById(id);
	}

	@Test
	void testCreateLocation() {
		// Arrange
		Locations location = new Locations();
		when(locationsRepository.save(location)).thenReturn(location);

		// Act
		Locations result = locationService.createLocation(location);

		// Assert
		assertEquals(location, result);
		verify(locationsRepository, times(1)).save(location);
	}

	@Test
	void testUpdateLocation() {
		// Arrange
		Locations location = new Locations();
		BigDecimal id = BigDecimal.valueOf(1);
		location.setLocationId(id);
		when(locationsRepository.existsById(id)).thenReturn(true);
		when(locationsRepository.save(location)).thenReturn(location);

		// Act
		Locations result = locationService.updateLocation(location);

		// Assert
		assertEquals(location, result);
		verify(locationsRepository, times(1)).existsById(id);
		verify(locationsRepository, times(1)).save(location);
	}

	@Test
	void testDeleteLocation() {
		// Arrange
		BigDecimal id = BigDecimal.valueOf(1);

		// Act
		locationService.deleteLocation(id);

		// Assert
		verify(locationsRepository, times(1)).deleteById(id);
	}
}
