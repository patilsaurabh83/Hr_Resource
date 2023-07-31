package com.cg.hrresource.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.cg.hrresource.dto.ExperienceResponse;
import com.cg.hrresource.entities.JobHistory;
import com.cg.hrresource.service.JobHistoryService;

class JobHistoryControllerTest {

    @Mock
    private JobHistoryService jobHistoryService;

    @InjectMocks
    private JobHistoryController jobHistoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddJobHistoryEntry() {
        BigDecimal employeeId = new BigDecimal(123);
        String startDate = "2023-01-01";
        String jobId = "456";
        BigDecimal departmentId = new BigDecimal(789);

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Record Created Successfully");

        ResponseEntity<String> response = jobHistoryController.addJobHistoryEntry(employeeId, startDate, jobId, departmentId);

        assertEquals(expectedResponse, response);
        verify(jobHistoryService, times(1)).addJobHistoryEntry(employeeId, startDate, jobId, departmentId);
    }

    @Test
    void testUpdateJobHistoryEndDate() throws ParseException {
        BigDecimal empid = new BigDecimal(123);
        String enddate = "2023-02-28";

        java.util.Date parsedEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Record Modified Successfully");

        ResponseEntity<String> response = jobHistoryController.updateJobHistoryEndDate(empid, enddate);

        assertEquals(expectedResponse, response);
        verify(jobHistoryService, times(1)).updateEndDateByEmployeeId(empid, parsedEndDate);
    }

//    @Test
//    void testFindExperienceOfEmployees() {
//        BigDecimal employeeId = new BigDecimal(123);
//        Period totalExperience = Period.ofYears(2).plusMonths(6).plusDays(10);
//        ExperienceResponse expectedResponse = new ExperienceResponse(2, 6, 10);
//
//        when(jobHistoryService.calculateTotalExperience(employeeId)).thenReturn(totalExperience);
//
//        ResponseEntity<ExperienceResponse> response = jobHistoryController.findExperienceOfEmployees(employeeId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(expectedResponse, response.getBody());
//        verify(jobHistoryService, times(1)).calculateTotalExperience(employeeId);
//    }

    @Test
    void testListAllEmployeesWithLessThanOneYearExperience() {
        List<ExperienceResponse> experienceList = new ArrayList<>();
        experienceList.add(new ExperienceResponse(0, 6, 15));
        experienceList.add(new ExperienceResponse(0, 11, 28));

        when(jobHistoryService.listAllEmployeesWithLessThanOneYearExperience()).thenReturn(experienceList);

        ResponseEntity<List<ExperienceResponse>> expectedResponse = ResponseEntity.ok(experienceList);

        ResponseEntity<List<ExperienceResponse>> response = jobHistoryController.listAllEmployeesWithLessThanOneYearExperience();

        assertEquals(expectedResponse, response);
        verify(jobHistoryService, times(1)).listAllEmployeesWithLessThanOneYearExperience();
    }

    @Test
    void testGetAllJobHistory() {
        List<JobHistory> jobHistoryList = new ArrayList<>();
        jobHistoryList.add(new JobHistory());
        jobHistoryList.add(new JobHistory());

        when(jobHistoryService.getAllJobHistory()).thenReturn(jobHistoryList);

        List<JobHistory> expectedResponse = jobHistoryList;

        List<JobHistory> response = jobHistoryController.getAllJobHistory();

        assertEquals(expectedResponse, response);
        verify(jobHistoryService, times(1)).getAllJobHistory();
    }
}
