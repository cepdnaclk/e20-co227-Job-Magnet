package com.example.jobportal.controller;

import com.example.jobportal.dto.RegJobSeekerDTO;
import com.example.jobportal.entity.RegJobSeeker;
import com.example.jobportal.repository.RegJobSeekerRepo;
import com.example.jobportal.service.RegJobSeekerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/api/jobseekers")
public class JobSeekerController {

    @Autowired
    private RegJobSeekerServiceImpl jobSeekerService;

    @Autowired
    private RegJobSeekerRepo jobSeekerRepo;

    // Get profile by email
    @GetMapping("/profile")
    public ResponseEntity<RegJobSeeker> getProfile(@RequestParam String email) {
        Optional<RegJobSeeker> jobSeeker = jobSeekerRepo.findByEmail(email);
        if (jobSeeker.isPresent()) {
            return ResponseEntity.ok(jobSeeker.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Save jobseeker's profile with image
    @PostMapping("/getimage/save")
    public ResponseEntity<String> saveImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fname") String fname,
            @RequestParam("lname") String lname,
            @RequestParam("dob") String dob,
            @RequestParam("password") String password,
            @RequestParam("skills") String skills,
            @RequestParam("experience") String experience,
            @RequestParam("phone") String phone,
            @RequestParam("bio") String bio,
            @RequestParam("country") String country,
            @RequestParam("degree") String degree,
            @RequestParam("dclass") String dClass,
            @RequestParam("projects") String projectsDone,
            @RequestParam("about") String about) {

        try {
            RegJobSeeker jobSeeker = new RegJobSeeker();
            jobSeeker.setFname(fname);
            jobSeeker.setLname(lname);
            jobSeeker.setDob(LocalDate.parse(dob));
            jobSeeker.setPassword(password);
            jobSeeker.setSkills(skills);
            jobSeeker.setExperience(experience);
            jobSeeker.setPhone(phone);
            jobSeeker.setBio(bio);
            jobSeeker.setCountry(country);
            jobSeeker.setDegree(degree);
            jobSeeker.setDclass(dClass);
            jobSeeker.setProjects(projectsDone);
            jobSeeker.setAbout(about);

            if (file != null && !file.isEmpty()) {
                jobSeeker.setProfilePicture(file.getBytes());
            }

            jobSeekerService.updateJobSeekerProfile(jobSeeker);

            return ResponseEntity.ok("JobSeeker profile saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving JobSeeker profile");
        }
    }

    @PostMapping("/profile/update")
    public ResponseEntity<String> updateJobSeekerProfile(
            @RequestParam("email") String email,
            @RequestParam("fname") String fname,
            @RequestParam("lname") String lname,
            @RequestParam("dob") String dob,
            @RequestParam(value = "experience", required = false) String experience,
            @RequestParam(value = "skills", required = false) String skills,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "bio", required = false) String bio,
            @RequestParam(value = "degree", required = false) String degree,
            @RequestParam(value = "dclass", required = false) String dclass,
            @RequestParam(value = "projects", required = false) String projects,
            @RequestParam(value = "about", required = false) String about,
            @RequestParam(value = "university", required = false) String university,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        try {
            Optional<RegJobSeeker> jobSeekerOptional = jobSeekerRepo.findByEmail(email);
            if (!jobSeekerOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("JobSeeker not found");
            }

            RegJobSeeker jobSeeker = jobSeekerOptional.get();
            jobSeeker.setFname(fname);
            jobSeeker.setLname(lname);
            jobSeeker.setDob(LocalDate.parse(dob));

            if (experience != null) jobSeeker.setExperience(experience);
            if (skills != null) jobSeeker.setSkills(skills);
            if (phone != null) jobSeeker.setPhone(phone);
            if (bio != null) jobSeeker.setBio(bio);
            if (degree != null) jobSeeker.setDegree(degree);
            if (dclass != null) jobSeeker.setDclass(dclass);
            if (projects != null) jobSeeker.setProjects(projects);
            if (about != null) jobSeeker.setAbout(about);
            if (university != null) jobSeeker.setUniversity(university);

            if (file != null && !file.isEmpty()) {
                jobSeeker.setProfilePicture(file.getBytes());
            }

            jobSeekerRepo.save(jobSeeker);

            return ResponseEntity.ok("Profile updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating profile: " + e.getMessage());
        }
    }


}
