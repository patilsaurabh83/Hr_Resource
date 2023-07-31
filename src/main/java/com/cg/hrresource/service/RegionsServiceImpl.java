package com.cg.hrresource.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrresource.entities.Regions;
import com.cg.hrresource.exception.CustomException;
import com.cg.hrresource.repository.RegionsRepository;

import jakarta.transaction.Transactional;

@Service
public class RegionsServiceImpl implements RegionsService {

	@Autowired
	private RegionsRepository regionRepository;

	@Override

	public Regions getRegionById(BigDecimal regionId) {

		Regions region = regionRepository.findByRegionId(regionId);

		if (region == null) {

			throw new CustomException("Region not found");

		}

		return region;

	}

	@Override
	@Transactional
	public void deleteRegionById(BigDecimal regionId) {
		regionRepository.deleteByRegionId(regionId);
	}

	@Override
	public List<Regions> getAllRegions() {
		return regionRepository.findAll();
	}

	@Override
	public Regions createdRegion(Regions region) {
		return regionRepository.save(region);
	}

	@Override
    public Regions updateRegion(Regions region) {
        BigDecimal id = region.getRegionId();
        if (regionRepository.existsById(id)) {
            return regionRepository.save(region);
        } else {
            throw new CustomException("Region not found with id: " + id);
        }
    }
}