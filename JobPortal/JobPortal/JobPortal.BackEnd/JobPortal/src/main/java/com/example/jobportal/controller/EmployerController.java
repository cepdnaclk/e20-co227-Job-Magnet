package com.example.jobportal.controller;

import com.example.jobportal.entity.RegJobEmployers;
import com.example.jobportal.service.RegJobEmployerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Controller for handling employer-related operations.
 */
@RestController
@RequestMapping("/api/employers")
public class EmployerController {

    @Autowired
    private RegJobEmployerServiceImpl jobEmployerService;

    /**
     * Retrieves the profile of an employer.
     *
     * @param email The email of the employer.
     * @return The employer's profile.
     */
    @GetMapping("/profile")
    public ResponseEntity<RegJobEmployers> getEmpProfile(@RequestParam String email) {
        Optional<RegJobEmployers> jobEmployers = jobEmployerService.getEmployerProfile(email);
        return jobEmployers.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    /**
     * Updates the profile of an employer.
     *
     * @param email The email of the employer.
     * @param companyName The new company name.
     * @param companyLogo The new company logo.
     * @param bannerImage The new banner image.
     * @param govRegNo The new government registration number.
     * @param about A description of the company.
     * @param coreValues The core values of the company.
     * @param years The number of years the company has been in operation.
     * @param noOfEmployees The number of employees in the company.
     * @param projectsCompleted The number of projects completed by the company.
     * @return A response entity with a success or error message.
     */
    @PutMapping("/profile/update")
    public ResponseEntity<String> updateEmpProfile(@RequestParam String email,
                                                   @RequestParam(required = false) String companyName,
                                                   @RequestParam(required = false) MultipartFile companyLogo,
                                                   @RequestParam(required = false) MultipartFile bannerImage,
                                                   @RequestParam(required = false) String govRegNo,
                                                   @RequestParam(required = false) String about,
                                                   @RequestParam(required = false) String coreValues,
                                                   @RequestParam(required = false) Integer years,
                                                   @RequestParam(required = false) Long noOfEmployees,
                                                   @RequestParam(required = false) Integer projectsCompleted) {
        try {
            Optional<RegJobEmployers> existingProfileOpt = jobEmployerService.getEmployerProfile(email);

            if (existingProfileOpt.isPresent()) {
                RegJobEmployers existingProfile = existingProfileOpt.get();

                // Update fields with new values if they are provided
                if (companyName != null) {
                    existingProfile.setCompanyName(companyName);
                }
                if (companyLogo != null) {
                    existingProfile.setCompanyLogo(companyLogo.getBytes());
                }
                if (bannerImage != null) {
                    existingProfile.setBannerImage(bannerImage.getBytes());
                }
                if (govRegNo != null) {
                    existingProfile.setGovRegNo(govRegNo);
                }

                if (about != null) {
                    existingProfile.setAbout(about);
                }
                if (coreValues != null) {
                    existingProfile.setCoreValues(coreValues);
                }
                if (years != null) {
                    existingProfile.setYears(years);
                }
                if (noOfEmployees != null) {
                    existingProfile.setNo_of_Employees(noOfEmployees);
                }
                if (projectsCompleted != null) {
                    existingProfile.setProjectsCompleted(projectsCompleted);
                }

                jobEmployerService.updateEmployerProfile(email, existingProfile);
                return ResponseEntity.ok("Profile updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employer profile not found");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Profile update failed: " + e.getMessage());
        }
    }

}
