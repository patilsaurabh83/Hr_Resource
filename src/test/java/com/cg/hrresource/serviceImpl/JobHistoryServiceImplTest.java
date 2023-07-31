package com.cg.hrresource.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.hrresource.entities.JobHistory;
import com.cg.hrresource.repository.JobHistoryRepository;
import com.cg.hrresource.service.JobHistoryServiceImpl;

class JobHistoryServiceImplTest {

    @Mock
    private JobHistoryRepository jobHistoryRepository;

    @InjectMocks
    private JobHistoryServiceImpl jobHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testAddJobHistoryEntry() {
//        // Mock dependencies
//        BigDecimal employeeId = BigDecimal.valueOf(123);
//        String startDate = "2023-01-01";
//        String jobId = "JOB001";
//        BigDecimal departmentId = BigDecimal.valueOf(456);
//        JobHistory jobHistory = new JobHistory();
//        when(jobHistoryRepository.save(any(JobHistory.class))).thenReturn(jobHistory);
//
//        // Call the method
//        jobHistoryService.addJobHistoryEntry(employeeId, startDate, jobId, departmentId);
//
//        // Verify the interactions
//        verify(jobHistoryRepository).save(any(JobHistory.class));
//    }

    @Test
    void testUpdateEndDateByEmployeeId() {
        // Mock dependencies
        BigDecimal employeeId = BigDecimal.valueOf(123);
        java.util.Date endDate = Date.valueOf("2023-06-30");
        doNothing().when(jobHistoryRepository).updateEndDateByEmployeeId(employeeId, endDate);

        // Call the method
        jobHistoryService.updateEndDateByEmployeeId(employeeId, endDate);

        // Verify the interactions
        verify(jobHistoryRepository).updateEndDateByEmployeeId(employeeId, endDate);
    }

    @Test
    void testCalculateTotalExperience() {
        // Mock dependencies
        BigDecimal employeeId = BigDecimal.valueOf(123);
        JobHistory jobHistory = new JobHistory();
        jobHistory.setStartDate(Date.valueOf("2023-01-01"));
        jobHistory.setEndDate(Date.valueOf("2023-07-08"));
        when(jobHistoryRepository.findTotalExperienceByEmployeeId(employeeId)).thenReturn(jobHistory);

        // Call the method
        LocalDate now = LocalDate.of(2023, 7, 8);
        Period expectedPeriod = Period.between(((Date) jobHistory.getStartDate()).toLocalDate(), now);
        Period actualPeriod = jobHistoryService.calculateTotalExperience(employeeId);

        // Verify the result
        assertEquals(expectedPeriod, actualPeriod);
    }

//    @Test
//    void testListAllEmployeesWithLessThanOneYearExperience() {
//        // Mock dependencies
//        JobHistory jobHistory1 = new JobHistory();
//        jobHistory1.setStartDate(Date.valueOf("2022-01-01"));
//        jobHistory1.setEndDate(Date.valueOf("2022-12-31"));
//        JobHistory jobHistory2 = new JobHistory();
//        jobHistory2.setStartDate(Date.valueOf("2023-01-01"));
//        jobHistory2.setEndDate(Date.valueOf("2023-06-30"));
//        List<JobHistory> jobHistories = new ArrayList<>();
//        jobHistories.add(jobHistory1);
//        jobHistories.add(jobHistory2);
//        when(jobHistoryRepository.findAll()).thenReturn(jobHistories);
//
//        // Call the method
//        List<ExperienceResponse> expectedResponse = new ArrayList<>();
//        LocalDate now = LocalDate.of(2023, 7, 8);
//        Period period1 = Period.between(((Date) jobHistory1.getStartDate()).toLocalDate(), now);
//        Period period2 = Period.between(((Date) jobHistory2.getStartDate()).toLocalDate(), now);
//        if (period1.getYears() < 1) {
//            ExperienceResponse response1 = new ExperienceResponse(period1.getYears(), period1.getMonths(), period1.getDays());
//            expectedResponse.add(response1);
//        }
//        if (period2.getYears() < 1) {
//            ExperienceResponse response2 = new ExperienceResponse(period2.getYears(), period2.getMonths(), period2.getDays());
//            expectedResponse.add(response2);
//        }
//        List<ExperienceResponse> actualResponse = jobHistoryService.listAllEmployeesWithLessThanOneYearExperience();
//
//        // Verify the result
//        assertEquals(expectedResponse, actualResponse);
//    }

    @Test
    void testGetAllJobHistory() {
        // Mock dependencies
        JobHistory jobHistory1 = new JobHistory();
        JobHistory jobHistory2 = new JobHistory();
        List<JobHistory> expectedJobHistories = new ArrayList<>();
        expectedJobHistories.add(jobHistory1);
        expectedJobHistories.add(jobHistory2);
        when(jobHistoryRepository.findAllWithDetails()).thenReturn(expectedJobHistories);

        // Call the method
        List<JobHistory> actualJobHistories = jobHistoryService.getAllJobHistory();

        // Verify the result
        assertEquals(expectedJobHistories, actualJobHistories);
    }

    // Add more test cases for other methods

}

