package com.example.jobportal.controller;

import com.example.jobportal.entity.JobPosting;
import com.example.jobportal.service.PostedJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postedJobs")
public class PostedJobsController {

    @Autowired
    private PostedJobsService postedJobsService;

    @GetMapping("/all")
    public ResponseEntity<List<JobPosting>> getAllJobs() {
        List<JobPosting> jobs = postedJobsService.getAllJobs();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/{jobId}") // Endpoint to retrieve a single job by ID
    public ResponseEntity<JobPosting> getJobById(@PathVariable long jobId) {
        JobPosting job = postedJobsService.getJobById(jobId);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @PutMapping("/{jobId}") // Endpoint to update job posting
    public ResponseEntity<JobPosting> updateJobPosting(@PathVariable long jobId, @RequestBody JobPosting updatedJob) {
        JobPosting job = postedJobsService.updateJobPosting(jobId, updatedJob);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobPosting>> getJobsByCompanyId(@PathVariable Long companyId) {
        List<JobPosting> jobs = postedJobsService.getJobsByCompanyId(companyId); // Correct method name
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }



    @GetMapping("/search")
    public ResponseEntity<List<JobPosting>> searchJobs(@RequestParam("keyword") String keyword) {
        Long companyId = getLoggedInCompanyId();  // Get the logged-in user's company ID
        List<JobPosting> jobs = postedJobsService.searchJobsByKeywordAndCompany(keyword, companyId);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }


    private Long getLoggedInCompanyId() {
        // Fetch the company ID from the authenticated user (e.g., from session, token, or security context)
        return 1L; // Example company ID, replace with actual logic
    }
}
