package com.cg.hrresource.controller;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.hrresource.entities.Countries;
import com.cg.hrresource.service.CountriesService;

@ExtendWith(MockitoExtension.class)
class CountryControllerTest {

    @Mock
    private CountriesService countryService;

    @InjectMocks
    private CountriesController countryController;

//    @BeforeEach
//    void setUp() {
//        // Any additional setup required for the test cases
//    }

    @Test
    void testCreatedCountry() {
        // Arrange
        Countries country = new Countries();
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Record created Successfully");
        when(countryService.createdCountry(country)).thenReturn(country);

        // Act
        ResponseEntity<String> actualResponse = countryController.createdCountry(country);

        // Assert
        Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        Assertions.assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(countryService, times(1)).createdCountry(country);
    }

//    @Test
//    void testUpdateCountry() {
//        // Arrange
//        Countries country = new Countries();
//        String updateMessage = "Country updated successfully";
//        ResponseEntity<String> expectedResponse = ResponseEntity.ok(updateMessage);
//        when(countryService.updateCountry(country)).thenReturn(updateMessage);
//
//        // Act
//        ResponseEntity<String> actualResponse = countryController.updateCountry(country);
//
//        // Assert
//        Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
//        Assertions.assertEquals(expectedResponse.getBody(), actualResponse.getBody());
//        verify(countryService, times(1)).updateCountry(country);
//    }

    @Test
    void testGetAllCountries() {
        // Arrange
        List<Countries> expectedCountries = Arrays.asList(new Countries(), new Countries());
        when(countryService.getAllCountries()).thenReturn(expectedCountries);

        // Act
        ResponseEntity<List<Countries>> actualResponse = countryController.getAllCountries();

        // Assert
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedCountries, actualResponse.getBody());
        verify(countryService, times(1)).getAllCountries();
    }

    @Test
    void testDeleteCountryById() {
        // Arrange
        String countryId = "1";

        // Act
        ResponseEntity<String> actualResponse = countryController.deleteCountryById(countryId);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals("Record deleted Successfully", actualResponse.getBody());
        verify(countryService, times(1)).deleteCountryById(countryId);
    }

    @Test
    void testGetCountryById() {
        // Arrange
        String countryId = "1";
        Optional<Countries> expectedCountry = Optional.of(new Countries());
        when(countryService.getCountryById(countryId)).thenReturn(expectedCountry);

        // Act
        ResponseEntity<Optional<Countries>> actualResponse = countryController.getCountryById(countryId);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedCountry, actualResponse.getBody());
        verify(countryService, times(1)).getCountryById(countryId);
    }
}
