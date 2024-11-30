package com.example.jobportal.Controller;

import com.example.jobportal.controller.JobPostingController;
import com.example.jobportal.entity.JobPosting;
import com.example.jobportal.repository.JobPostingRepo;
import com.example.jobportal.service.JobPostingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobPostingControllerTest {

    @InjectMocks
    private JobPostingController jobPostingController;

    @Mock
    private JobPostingService jobPostingService;

    @Mock
    private JobPostingRepo jobPostingRepo;

    private JobPosting mockJobPosting;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create a sample JobPosting object for testing
        mockJobPosting = new JobPosting();
        mockJobPosting.setId(1L);
        mockJobPosting.setTitle("Software Engineer");
        mockJobPosting.setDescription("Job Description");
    }

    @Test
    public void testPostJobPostingSuccess() {
        // Arrange: Mock the service to return the created JobPosting
        when(jobPostingService.postJob(any(JobPosting.class))).thenReturn(mockJobPosting);

        // Act: Call the controller method to post a job
        ResponseEntity<?> response = jobPostingController.postJobPosting(mockJobPosting);

        // Assert: Check if the response is OK and the body contains the JobPosting object
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockJobPosting, response.getBody());
    }

    @Test
    public void testPostJobPostingFailure() {
        // Arrange: Simulate an exception when calling the service
        when(jobPostingService.postJob(any(JobPosting.class))).thenThrow(new RuntimeException("Test exception"));

        // Act: Call the controller method to post a job and handle failure
        ResponseEntity<?> response = jobPostingController.postJobPosting(mockJobPosting);

        // Assert: Ensure that the response has an INTERNAL_SERVER_ERROR status and contains the error message
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Error occurred while posting the job"));
    }

    @Test
    public void testDeleteJobPostingSuccess() {
        // Act: Call the controller method to delete a job posting
        ResponseEntity<?> response = jobPostingController.deleteJobPosting(1L);

        // Assert: Ensure the response is OK and contains a success message
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((java.util.Map<?, ?>) response.getBody()).containsKey("success"));
        assertTrue((Boolean) ((java.util.Map<?, ?>) response.getBody()).get("success"));

        // Verify that the repository's deleteById method was called once
        verify(jobPostingRepo, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteJobPostingFailure() {
        // Arrange: Simulate an exception when deleting a job posting
        doThrow(new RuntimeException("Test exception")).when(jobPostingRepo).deleteById(anyLong());

        // Act: Call the controller method to delete a job posting and handle failure
        ResponseEntity<?> response = jobPostingController.deleteJobPosting(1L);

        // Assert: Ensure that the response has an INTERNAL_SERVER_ERROR status and indicates failure
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(((java.util.Map<?, ?>) response.getBody()).containsKey("success"));
        assertFalse((Boolean) ((java.util.Map<?, ?>) response.getBody()).get("success"));
    }

    @Test
    public void testTestEndpoint() {
        // Act: Call the test endpoint
        String result = jobPostingController.test();

        // Assert: Ensure the returned string is "test success"
        assertEquals("test success", result);
    }
}
