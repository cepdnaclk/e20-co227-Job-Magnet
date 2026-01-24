package com.example.jobportal.controller;

import com.example.jobportal.entity.JobPosting;
import com.example.jobportal.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Controller for handling job search operations.
 */
@Controller
@RequestMapping("/api/seekers/search-jobs")
public class JobSearchController {

    @Autowired
    private JobPostingService jobPostingService;

    /**
     * Test endpoint to check if the controller is working.
     * @return A test string.
     */
    @GetMapping("/test1")
    public String test() {
        return "test success!";
    }

    /**
     * Retrieves all job postings.
     *
     * @return A response entity containing a list of all job postings or a message if no jobs are posted.
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllJobPostings() {
        List<JobPosting> jobPostings = jobPostingService.getAllJobPostings();
        if (jobPostings.isEmpty()) {
            return ResponseEntity.ok("No jobs posted");
        } else {
            return ResponseEntity.ok(jobPostings);
        }
    }
    /*
    @GetMapping("/api/seekers/search-jobs/all")
    public ResponseEntity<List<BatchProperties.Job>> getAllJobs() {
        List<JobPosting> jobs = jobPostingService.getAllJobPostings();
        if (jobs.isEmpty()) {
            return ResponseEntity.ok(Collections.singletonMap("message", "No jobs posted")); // Send a JSON object
        }
        return ResponseEntity.ok(jobs); // Send the list of jobs as JSON
    }
    */
    /**
     * Searches for job postings based on a given search option and query.
     *
     * @param searchOption The search option (e.g., "type", "title", "position", "location").
     * @param query The search query.
     * @param model The model to which the search results will be added.
     * @return A response entity containing a list of job postings that match the search criteria.
     */
    @GetMapping("/search")
    public ResponseEntity<List<?>> searchJobPostings(
            @RequestParam("search_option") String searchOption,
            @RequestParam("query") String query, Model model) {

        List<JobPosting> jobs = new ArrayList<>();
        try {
            switch (searchOption.toLowerCase()) {
                case "type":
                    jobs = jobPostingService.searchJobPostingByJobType(query);
                    break;
                case "title":
                    jobs = jobPostingService.searchJobPostingByTitle(query);
                    break;
                case "position":
                    jobs = jobPostingService.searchJobPostingByPosition(query);
                    break;
                case "location":
                    jobs = jobPostingService.searchJobPostingByLocation(query);
                    break;

                default:
                    model.addAttribute("error", "Invalid search option");
                    //return "jobsOutput";

            }
            if (jobs.isEmpty()) {
                model.addAttribute("message", "No jobs found for the given criteria.");
            }
            model.addAttribute("jobs", jobs);
            return ResponseEntity.ok(jobs);
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid ID format");
            //return "jobsOutput";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while searching for jobs: " + e.getMessage());
            //return "jobsOutput";
        }

        //return "jobsOutput";
        return ResponseEntity.ok(jobs);
    }
    /**
     * Finds a job posting by its ID.
     *
     * @param id The ID of the job posting to find.
     * @return A response entity containing the job posting if found, or a message if not found.
     */
    @GetMapping("/findByJobId/{id}")
    public ResponseEntity<?> findJobPostingById(@PathVariable Long id) {
        Optional<JobPosting> jobPosting = jobPostingService.getJobPostingById(id);
        if (jobPosting.isEmpty()) {
            return ResponseEntity.ok("No jobs posted");
        } else {
            return ResponseEntity.ok(jobPosting.get());
        }
    }
   /* // Method to delete a job posting
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJobPosting(@PathVariable Long id) {
        try {
            boolean isDeleted = jobPostingService.deleteJobPostingById(id);
            if (isDeleted) {
                return ResponseEntity.ok("Job posting deleted successfully.");
            } else {
                return ResponseEntity.status(404).body("Job posting not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the job posting: " + e.getMessage());
        }
    }*/

}
