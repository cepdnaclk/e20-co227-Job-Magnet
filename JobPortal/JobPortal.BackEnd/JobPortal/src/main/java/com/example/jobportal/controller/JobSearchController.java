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

@Controller
@RequestMapping("/api/seekers/search-jobs")
public class JobSearchController {

    @Autowired
    private JobPostingService jobPostingService;

    @GetMapping("/test1")
    public String test() {
        return "test success!";
    }

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
