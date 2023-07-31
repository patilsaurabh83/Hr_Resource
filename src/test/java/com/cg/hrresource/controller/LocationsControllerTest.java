package com.cg.hrresource.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.hrresource.entities.Locations;
import com.cg.hrresource.service.LocationsService;

@ExtendWith(MockitoExtension.class)
class LocationsControllerTest {

    @Mock
    private LocationsService locationsService;

    @InjectMocks
    private LocationsController locationsController;

    @Test
    void createLocation_ValidLocation_ReturnsCreatedStatus() {
        // Arrange
        Locations location = new Locations();
        when(locationsService.createLocation(location)).thenReturn(location);

        // Act
        ResponseEntity<String> response = locationsController.createLocation(location);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Record Created Successfully", response.getBody());
        verify(locationsService, times(1)).createLocation(location);
    }

    @Test
    void updateLocation_ValidLocation_ReturnsOkStatus() {
        // Arrange
        Locations location = new Locations();
        when(locationsService.updateLocation(location)).thenReturn(location);

        // Act
        ResponseEntity<String> response = locationsController.updateLocation(location);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record Modified Successfully", response.getBody());
        verify(locationsService, times(1)).updateLocation(location);
    }

    @Test
    void getAllLocations_ReturnsListOfLocations() {
        // Arrange
        List<Locations> locations = new ArrayList<>();
        when(locationsService.getAllLocations()).thenReturn(locations);

        // Act
        ResponseEntity<List<Locations>> response = locationsController.getAllLocations();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(locations, response.getBody());
        verify(locationsService, times(1)).getAllLocations();
    }

    @Test
    void getLocationById_ExistingId_ReturnsLocation() {
        // Arrange
        BigDecimal id = BigDecimal.ONE;
        Locations location = new Locations();
        Optional<Locations> optionalLocation = Optional.of(location);
        when(locationsService.getLocationById(id)).thenReturn(optionalLocation);

        // Act
        ResponseEntity<Locations> response = locationsController.getLocationById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(location, response.getBody());
        verify(locationsService, times(1)).getLocationById(id);
    }

    @Test
    void getLocationById_NonExistingId_ReturnsNotFoundStatus() {
        // Arrange
        BigDecimal id = BigDecimal.ONE;
        when(locationsService.getLocationById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Locations> response = locationsController.getLocationById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() == null);
        verify(locationsService, times(1)).getLocationById(id);
    }

    @Test
    void deleteLocation_ExistingId_ReturnsOkStatus() {
        // Arrange
        BigDecimal id = BigDecimal.ONE;

        // Act
        ResponseEntity<String> response = locationsController.deleteLocation(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record deleted Successfully", response.getBody());
        verify(locationsService, times(1)).deleteLocation(id);
    }
}
