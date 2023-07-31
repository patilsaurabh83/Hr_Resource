package com.cg.hrresource.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrresource.entities.Countries;
import com.cg.hrresource.exception.CustomException;
import com.cg.hrresource.repository.CountriesRepository;

import jakarta.transaction.Transactional;

@Service

public class CountriesServiceImpl implements CountriesService {

	@Autowired

	private CountriesRepository countryRepository;

	@Override

	public List<Countries> getAllCountries() {

		return countryRepository.findAll();

	}

	@Override

	@Transactional

	public void deleteCountryById(String id) {

		countryRepository.deleteById(id);

	}

	@Override
    public Optional<Countries> getCountryById(String id) {
        Optional<Countries> findById = countryRepository.findById(id);
        if (findById.isPresent()) {
            return findById;
        } else {
            throw new CustomException("Country not found");
        }
    }
	@Override

	public Countries createdCountry(Countries country) {
		return countryRepository.save(country);

	}

	@Override
    public String updateCountry(Countries country) {
        String id = country.getCountryId();
        if (countryRepository.existsById(id)) {
            countryRepository.save(country);
            return "Country updated successfully";
        } else {
            throw new CustomException("Country not updated");
        }
    }
}
