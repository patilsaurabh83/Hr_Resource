package com.cg.hrresource.service;

import java.math.BigDecimal;
import java.util.List;

import com.cg.hrresource.entities.Regions;

public interface RegionsService {

	Regions getRegionById(BigDecimal regionId);

	void deleteRegionById(BigDecimal regionId);

	List<Regions> getAllRegions();

	Regions createdRegion(Regions region);

	Regions updateRegion(Regions region);

}
