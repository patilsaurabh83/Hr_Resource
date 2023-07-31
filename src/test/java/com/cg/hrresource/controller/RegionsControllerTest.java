package com.cg.hrresource.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.hrresource.entities.Regions;
import com.cg.hrresource.service.RegionsServiceImpl;

@ExtendWith(MockitoExtension.class)
class RegionControllerTest {

	@Mock
	private RegionsServiceImpl regionService;

	@InjectMocks
	private RegionsController regionController;

	@BeforeEach
	void setUp() {
		// Any additional setup required for the test cases
	}

	@Test
	void testCreatedRegion() {
		// Arrange
		Regions region = new Regions();
		ResponseEntity<String> expectedResponse = ResponseEntity.ok("Record created Successfully");
		when(regionService.createdRegion(region)).thenReturn(region);

		// Act
		ResponseEntity<String> actualResponse = regionController.createdRegion(region);

		// Assert
		Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		Assertions.assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(regionService, times(1)).createdRegion(region);
	}

	@Test
	void testUpdateRegion() {
		// Arrange
		Regions region = new Regions();
		ResponseEntity<String> expectedResponse = ResponseEntity.ok("Record Modified Successfully");
		when(regionService.updateRegion(region)).thenReturn(region);

		// Act
		ResponseEntity<String> actualResponse = regionController.updateRegion(region);

		// Assert
		Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		Assertions.assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(regionService, times(1)).updateRegion(region);
	}

	@Test
	void testGetAllRegions() {
		// Arrange
		List<Regions> expectedRegions = Arrays.asList(new Regions(), new Regions());
		when(regionService.getAllRegions()).thenReturn(expectedRegions);

		// Act
		List<Regions> actualRegions = regionController.getAllRegions();

		// Assert
		Assertions.assertEquals(expectedRegions, actualRegions);
		verify(regionService, times(1)).getAllRegions();
	}

	@Test
	void testGetRegionById() {
		// Arrange
		BigDecimal regionId = BigDecimal.valueOf(1);
		Regions expectedRegion = new Regions();
		when(regionService.getRegionById(regionId)).thenReturn(expectedRegion);

		// Act
		ResponseEntity<Regions> actualResponse = regionController.getRegionById(regionId);

		// Assert
		Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		Assertions.assertEquals(expectedRegion, actualResponse.getBody());
		verify(regionService, times(1)).getRegionById(regionId);
	}

	@Test
	void testDeleteRegionById() {
		// Arrange
		BigDecimal regionId = BigDecimal.valueOf(1);

		// Act
		ResponseEntity<String> actualResponse = regionController.deleteRegionById(regionId);

		// Assert
		Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		Assertions.assertEquals("Record deleted Successfully", actualResponse.getBody());
		verify(regionService, times(1)).deleteRegionById(regionId);
	}
}
