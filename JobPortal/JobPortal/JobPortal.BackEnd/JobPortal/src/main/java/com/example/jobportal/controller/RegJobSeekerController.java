package com.example.jobportal.controller;


import com.example.jobportal.dto.RegJobSeekerDTO;
import com.example.jobportal.entity.RegJobSeeker;
import com.example.jobportal.service.RegJobSeekerService;
import com.example.jobportal.service.RegJobSeekerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for handling job seeker registration and management.
 */
@RestController
@RequestMapping("/api/jobseekers")
@CrossOrigin(origins = "http://localhost:5173")

public class RegJobSeekerController {

    private final RegJobSeekerService regJobSeekerService;
    private final RegJobSeekerServiceImpl regJobSeekerServiceImpl;

    /**
     * Test endpoint to check if the controller is working.
     * @return A test string.
     */
    @GetMapping("/test")
    public String testEndpoint() {
        return "Test successful!";
    }

    @Autowired
    public RegJobSeekerController(RegJobSeekerService regJobSeekerService, RegJobSeekerServiceImpl regJobSeekerServiceImpl) {
        this.regJobSeekerService = regJobSeekerService;
        this.regJobSeekerServiceImpl = regJobSeekerServiceImpl;
    }

    /**
     * Registers a new job seeker.
     * @param jobSeekerDTO The DTO containing the job seeker's registration details.
     * @return A response entity with the registration status.
     */
    @PostMapping("/registration")
    public ResponseEntity<Map<String, Object>> registerJobSeeker(@RequestBody RegJobSeekerDTO jobSeekerDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            RegJobSeeker createdJobSeeker = regJobSeekerService.Reg(jobSeekerDTO);
            response.put("success", true);
            response.put("message", "Registration successful with ID: " + createdJobSeeker.getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }



    @GetMapping("/")
    public String regJobSeeker() {
        return "regJobSeeker";
    }


    @GetMapping("/login")
    public String loginJobSeeker() {
        return "loginJobSeeker";
    }

    /**
     * Retrieves all registered job seekers.
     * @return A list of all job seekers.
     */
    @GetMapping("/getall")
    public List<RegJobSeeker> getAllJobSeekers(){
        return regJobSeekerServiceImpl.getAllJobSeekers();
    }

    /**
     * Retrieves a job seeker by their ID.
     * @param id The ID of the job seeker.
     * @return The job seeker details.
     */
    @GetMapping("/searchbyid/{id}")
    public ResponseEntity<RegJobSeeker> getJobSeekerById(@PathVariable Long id){
        RegJobSeeker jobSeeker=regJobSeekerServiceImpl.getJobSeekerById(id);
        return ResponseEntity.ok(jobSeeker);
    }
    /**
     * Retrieves a job seeker by their first name.
     * @param fname The first name of the job seeker.
     * @return The job seeker details.
     */
    @GetMapping("/searchbyfname/{fname}")
    public ResponseEntity<RegJobSeeker>getJobSeekerByFname(@PathVariable String fname){
        RegJobSeeker jobSeeker=regJobSeekerServiceImpl.getJobSeekerByFname(fname);
        return ResponseEntity.ok(jobSeeker);
    }

    /**
     * Deletes a job seeker by their ID.
     * @param id The ID of the job seeker to delete.
     * @return A response entity indicating the success of the operation.
     */
    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteJobSeeker(@PathVariable Long id) {
        //regJobSeekerServiceImpl.DeleteJobSeeker(id);
        //  return ResponseEntity.noContent().build();
        try {
            regJobSeekerServiceImpl.DeleteJobSeeker(id);
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("success", false));
        }
    }
}

