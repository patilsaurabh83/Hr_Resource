package com.cg.hrresource.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.hrresource.entities.Countries;
import com.cg.hrresource.exception.CustomException;
import com.cg.hrresource.repository.CountriesRepository;
import com.cg.hrresource.service.CountriesServiceImpl;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

	@Mock
	private CountriesRepository countryRepository;

	@InjectMocks
	private CountriesServiceImpl countryService;

	@Test
	void testGetAllCountries() {
		// Arrange
		List<Countries> countries = List.of(new Countries(), new Countries());
		when(countryRepository.findAll()).thenReturn(countries);

		// Act
		List<Countries> result = countryService.getAllCountries();

		// Assert
		assertEquals(countries, result);
		verify(countryRepository, times(1)).findAll();
	}

	@Test
	void testDeleteCountryById() {
		// Arrange
		String id = "1";

		// Act
		countryService.deleteCountryById(id);

		// Assert
		verify(countryRepository, times(1)).deleteById(id);
	}

	@Test
	void testGetCountryById() {
		// Arrange
		String id = "1";
		Countries country = new Countries();
		when(countryRepository.findById(id)).thenReturn(Optional.of(country));

		// Act
		Optional<Countries> result = countryService.getCountryById(id);

		// Assert
		assertTrue(result.isPresent());
		assertEquals(country, result.get());
		verify(countryRepository, times(1)).findById(id);
	}

	@Test
	void testGetCountryById_NotFound() {
		// Arrange
		String id = "1";
		when(countryRepository.findById(id)).thenReturn(Optional.empty());

		// Act
		assertThrows(CustomException.class, () -> countryService.getCountryById(id));

		// Assert
		verify(countryRepository, times(1)).findById(id);
	}

	@Test
	void testCreatedCountry() {
		// Arrange
		Countries country = new Countries();
		when(countryRepository.save(country)).thenReturn(country);

		// Act
		Countries result = countryService.createdCountry(country);

		// Assert
		assertEquals(country, result);
		verify(countryRepository, times(1)).save(country);
	}

	@Test
	void testUpdateCountry() {
		// Arrange
		String id = "1";
		Countries country = new Countries();
		country.setCountryId(id);
		when(countryRepository.existsById(id)).thenReturn(true);
		when(countryRepository.save(country)).thenReturn(country);

		// Act
		String result = countryService.updateCountry(country);

		// Assert
		assertEquals("Country updated successfully", result);
		verify(countryRepository, times(1)).existsById(id);
		verify(countryRepository, times(1)).save(country);
	}

	@Test
	void testUpdateCountry_NotFound() {
		// Arrange
		String id = "1";
		Countries country = new Countries();
		country.setCountryId(id);
		when(countryRepository.existsById(id)).thenReturn(false);

		// Act
		assertThrows(CustomException.class, () -> countryService.updateCountry(country));

		// Assert
		verify(countryRepository, times(1)).existsById(id);
		verify(countryRepository, never()).save(country);
	}
}

