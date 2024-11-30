package com.example.jobportal.controller;

import com.example.jobportal.entity.JobPosting;
import com.example.jobportal.repository.JobPostingRepo;
import com.example.jobportal.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/company/posting")
public class JobPostingController {

    @Autowired
    private JobPostingService jobPostingService;
    @Autowired
    private JobPostingRepo jobPostingRepo;

    @GetMapping("/test")
    public String test() {
        return "test success";
    }

    @PostMapping("/post")
    public ResponseEntity<?> postJobPosting(@RequestBody JobPosting jobPosting) {
        try {
            JobPosting postJob = jobPostingService.postJob(jobPosting);
            return new ResponseEntity<>(postJob, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while posting the job: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJobPosting(@PathVariable Long id) {
      /*  try{
            jobPostingRepo.deleteById(id);
            return ResponseEntity.ok("{\\\"message\\\": \\\"JobPosting deleted successfully\\\"}");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\\\"message\\\": \\\"Error deleting job posting\\\"}");
        }*/
        try {
            jobPostingRepo.deleteById(id);
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("success", false));
        }

    }
}