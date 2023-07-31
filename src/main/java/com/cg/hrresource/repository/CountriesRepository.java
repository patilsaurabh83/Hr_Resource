package com.cg.hrresource.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.hrresource.entities.Countries;

 

 

public interface CountriesRepository extends JpaRepository<Countries, String> {
    Optional<Countries> findById(String id);

 

    Countries save(Countries country);
}
