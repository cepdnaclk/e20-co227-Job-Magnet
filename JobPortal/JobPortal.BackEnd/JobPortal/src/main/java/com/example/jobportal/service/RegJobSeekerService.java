package com.example.jobportal.service;


import com.example.jobportal.dto.RegJobSeekerDTO;
import com.example.jobportal.entity.RegJobSeeker;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RegJobSeekerService {
    RegJobSeeker Reg (RegJobSeekerDTO jobSeekerDTO);
    List<RegJobSeeker> getAllJobSeekers();
    // public ResponseEntity<RegJobSeeker> getJobSeekerProfile();


}
