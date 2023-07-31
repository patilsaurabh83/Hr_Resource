package com.cg.hrresource.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.hrresource.entities.Regions;

public interface RegionsRepository extends JpaRepository<Regions, BigDecimal> {
	Regions findByRegionId(BigDecimal regionId);

	void deleteByRegionId(BigDecimal regionId);

	Regions save(Regions region);

}
