package com.example.jobportal.controller;


import com.example.jobportal.dto.RegJobEmployerDTO;
import com.example.jobportal.dto.RegJobSeekerDTO;
import com.example.jobportal.entity.RegJobEmployers;
import com.example.jobportal.entity.RegJobSeeker;
import com.example.jobportal.service.RegJobEmployerService;
import com.example.jobportal.service.RegJobEmployerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employers")
public class RegEmployerController {

    public final RegJobEmployerService regJobEmployerService;
    private final RegJobEmployerServiceImpl regJobEmployerServiceImpl;

    @Autowired
    public RegEmployerController(RegJobEmployerService regJobEmployerService,RegJobEmployerServiceImpl regJobEmployerServiceImpl) {
        this.regJobEmployerService = regJobEmployerService;
        this.regJobEmployerServiceImpl=regJobEmployerServiceImpl;
    }

    @GetMapping("/test")
    public String test(){
        return "test success";
    }


    @PostMapping("/registration")
    public ResponseEntity<Map<String, Object>> registerJobEmployer(@RequestBody RegJobEmployerDTO jobEmployerDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            RegJobEmployers createdJobEmployers = regJobEmployerService.Reg(jobEmployerDTO);
            response.put("success", true);
            response.put("message", "Registration Successful with ID: " + createdJobEmployers.getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @GetMapping("/")
    public String regemp(){
        return "regemp";
    }


    @GetMapping("/all")
    public List<RegJobEmployers> getAllEmployers(){
        return regJobEmployerServiceImpl.getAllEmployers();
    }


    @GetMapping("/findbyid/{id}")
    public ResponseEntity<RegJobEmployers> getEmployerById(@PathVariable Long id){
        RegJobEmployers jobEmployer=regJobEmployerService.getJobEmployerById(id);
        return ResponseEntity.ok(jobEmployer);
    }


    @GetMapping("/findbyemail/{email}")
    public ResponseEntity<RegJobEmployers> getEmployerByEmail(@PathVariable String email){
        RegJobEmployers jobEmployer=regJobEmployerService.getJobEmployerByEmail(email);
        return ResponseEntity.ok(jobEmployer);
    }


    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteJobSeeker(@PathVariable Long id) {

        try {
            regJobEmployerService.DeleteJobEmployer(id);
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("success", false));
        }
    }
}




