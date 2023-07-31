package com.cg.hrresource.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.hrresource.entities.Jobs;
import com.cg.hrresource.repository.JobsRepository;
import com.cg.hrresource.service.JobsServiceImpl;

class JobsServiceImplTest {

    @Mock
    private JobsRepository jobsRepository;

    @InjectMocks
    private JobsServiceImpl jobsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddJob() {
        Jobs job = new Jobs();
        when(jobsRepository.save(job)).thenReturn(job);

        Jobs savedJob = jobsService.addJob(job);

        assertNotNull(savedJob);
        assertEquals(job, savedJob);
        verify(jobsRepository, times(1)).save(job);
    }

    @Test
    void testGetAllJobs() {
        List<Jobs> jobsList = new ArrayList<>();
        jobsList.add(new Jobs());
        jobsList.add(new Jobs());
        when(jobsRepository.findAll()).thenReturn(jobsList);

        List<Jobs> result = jobsService.getAllJobs();

        assertNotNull(result);
        assertEquals(jobsList.size(), result.size());
        verify(jobsRepository, times(1)).findAll();
    }

    @Test
    void testModifyJob() {
        Jobs job = new Jobs();
        when(jobsRepository.save(job)).thenReturn(job);

        Jobs modifiedJob = jobsService.modifyJob(job);

        assertNotNull(modifiedJob);
        assertEquals(job, modifiedJob);
        verify(jobsRepository, times(1)).save(job);
    }

    @Test
    void testUpdateSalary_WhenJobExists() {
        String jobId = "123";
        BigDecimal minSalary = new BigDecimal("1000");
        BigDecimal maxSalary = new BigDecimal("2000");

        Jobs job = new Jobs();
        job.setJobId(jobId);

        when(jobsRepository.findById(jobId)).thenReturn(Optional.of(job));
        when(jobsRepository.save(job)).thenReturn(job);

        boolean isUpdated = jobsService.updateSalary(jobId, minSalary, maxSalary);

        assertTrue(isUpdated);
        assertEquals(minSalary, job.getMinSalary());
        assertEquals(maxSalary, job.getMaxSalary());
        verify(jobsRepository, times(1)).findById(jobId);
        verify(jobsRepository, times(1)).save(job);
    }

    @Test
    void testUpdateSalary_WhenJobDoesNotExist() {
        String jobId = "123";
        BigDecimal minSalary = new BigDecimal("1000");
        BigDecimal maxSalary = new BigDecimal("2000");

        when(jobsRepository.findById(jobId)).thenReturn(Optional.empty());

        boolean isUpdated = jobsService.updateSalary(jobId, minSalary, maxSalary);

        assertFalse(isUpdated);
        verify(jobsRepository, times(1)).findById(jobId);
        verify(jobsRepository, never()).save(any());
    }

    @Test
    void testDeleteJobById_WhenJobExists() {
        String jobId = "123";

        when(jobsRepository.findById(jobId)).thenReturn(Optional.of(new Jobs()));

        boolean isDeleted = jobsService.deleteJobById(jobId);

        assertTrue(isDeleted);
        verify(jobsRepository, times(1)).findById(jobId);
        verify(jobsRepository, times(1)).delete(any());
    }

    @Test
    void testDeleteJobById_WhenJobDoesNotExist() {
        String jobId = "123";

        when(jobsRepository.findById(jobId)).thenReturn(Optional.empty());

        boolean isDeleted = jobsService.deleteJobById(jobId);

        assertFalse(isDeleted);
        verify(jobsRepository, times(1)).findById(jobId);
        verify(jobsRepository, never()).delete(any());
    }

    @Test
    void testGetMinSalaryByJobTitle() {
        String jobTitle = "Engineer";
        BigDecimal minSalary = new BigDecimal("1000");

        when(jobsRepository.findMinSalaryByJobTitle(jobTitle)).thenReturn(minSalary);

        BigDecimal result = jobsService.getMinSalaryByJobTitle(jobTitle);

        assertNotNull(result);
        assertEquals(minSalary, result);
        verify(jobsRepository, times(1)).findMinSalaryByJobTitle(jobTitle);
    }

    @Test
    void testGetMaxSalaryByJobTitle() {
        String jobTitle = "Engineer";
        BigDecimal maxSalary = new BigDecimal("2000");

        when(jobsRepository.findMaxSalaryByJobTitle(jobTitle)).thenReturn(maxSalary);

        BigDecimal result = jobsService.getMaxSalaryByJobTitle(jobTitle);

        assertNotNull(result);
        assertEquals(maxSalary, result);
        verify(jobsRepository, times(1)).findMaxSalaryByJobTitle(jobTitle);
    }
}
