package com.cg.hrresource.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.hrresource.entities.Regions;
import com.cg.hrresource.exception.CustomException;
import com.cg.hrresource.repository.RegionsRepository;
import com.cg.hrresource.service.RegionsServiceImpl;

@ExtendWith(MockitoExtension.class)
class RegionServiceImplTest {

    @Mock
    private RegionsRepository regionRepository;

    @InjectMocks
    private RegionsServiceImpl regionService;

    @Test
    void testGetRegionById() {
        // Arrange
        BigDecimal regionId = BigDecimal.valueOf(1);
        Regions region = new Regions();
        when(regionRepository.findByRegionId(regionId)).thenReturn(region);

        // Act
        Regions result = regionService.getRegionById(regionId);

        // Assert
        assertEquals(region, result);
        verify(regionRepository, times(1)).findByRegionId(regionId);
    }

    @Test
    void testGetRegionById_NotFound() {
        // Arrange
        BigDecimal regionId = BigDecimal.valueOf(1);
        when(regionRepository.findByRegionId(regionId)).thenReturn(null);

        // Act
        assertThrows(CustomException.class, () -> regionService.getRegionById(regionId));

        // Assert
        verify(regionRepository, times(1)).findByRegionId(regionId);
    }

    @Test
    void testDeleteRegionById() {
        // Arrange
        BigDecimal regionId = BigDecimal.valueOf(1);

        // Act
        regionService.deleteRegionById(regionId);

        // Assert
        verify(regionRepository, times(1)).deleteByRegionId(regionId);
    }

    @Test
    void testGetAllRegions() {
        // Arrange
        List<Regions> regions = List.of(new Regions(), new Regions());
        when(regionRepository.findAll()).thenReturn(regions);

        // Act
        List<Regions> result = regionService.getAllRegions();

        // Assert
        assertEquals(regions, result);
        verify(regionRepository, times(1)).findAll();
    }

    @Test
    void testCreatedRegion() {
        // Arrange
        Regions region = new Regions();
        when(regionRepository.save(region)).thenReturn(region);

        // Act
        Regions result = regionService.createdRegion(region);

        // Assert
        assertEquals(region, result);
        verify(regionRepository, times(1)).save(region);
    }

    @Test
    void testUpdateRegion() {
        // Arrange
        BigDecimal regionId = BigDecimal.valueOf(1);
        Regions region = new Regions();
        region.setRegionId(regionId);
        when(regionRepository.existsById(regionId)).thenReturn(true);
        when(regionRepository.save(region)).thenReturn(region);

        // Act
        Regions result = regionService.updateRegion(region);

        // Assert
        assertEquals(region, result);
        verify(regionRepository, times(1)).existsById(regionId);
        verify(regionRepository, times(1)).save(region);
    }

    @Test
    void testUpdateRegion_NotFound() {
        // Arrange
        BigDecimal regionId = BigDecimal.valueOf(1);
        Regions region = new Regions();
        region.setRegionId(regionId);
        when(regionRepository.existsById(regionId)).thenReturn(false);

        // Act
        assertThrows(CustomException.class, () -> regionService.updateRegion(region));

        // Assert
        verify(regionRepository, times(1)).existsById(regionId);
        verify(regionRepository, never()).save(region);
    }
}
