package com.example.jobportal.controller;

import com.example.jobportal.entity.JobApplication;
import com.example.jobportal.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Controller for handling job application-related operations.
 */
@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    /**
     * Applies for a job.
     *
     * @param jobId The ID of the job to apply for.
     * @param seekerId The ID of the job seeker applying.
     * @param companyId The ID of the company.
     * @param companyEmail The email of the company.
     * @param seekerName The name of the job seeker.
     * @param seekerEmail The email of the job seeker.
     * @param resume The resume file of the job seeker.
     * @return A response entity with the created job application or an error message.
     */
    @PostMapping("/apply")
    public ResponseEntity<?> applyToJob(
            @RequestParam("jobId") Long jobId,
            @RequestParam("seekerId") Long seekerId,
            @RequestParam("companyId") Long companyId,
            @RequestParam("companyEmail") String companyEmail,
            @RequestParam("seekerName") String seekerName,
            @RequestParam("seekerEmail") String seekerEmail,
            @RequestParam(value = "resume", required = false) MultipartFile resume) {
        try {
            byte[] resumeData = null;

            if (resume != null && !resume.isEmpty()) {
                resumeData = jobApplicationService.saveResume(resume);  // Save the binary data
            }

            JobApplication application = jobApplicationService.applyToJob(
                    jobId,
                    seekerId,
                    seekerName,
                    seekerEmail,
                    companyId,
                    companyEmail,
                    resumeData  // Pass the byte array
            );

            return new ResponseEntity<>(application, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>("Error occurred while uploading the resume: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception details
            return new ResponseEntity<>("Error occurred while applying to the job: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves job applications by company email.
     *
     * @param companyEmail The email of the company.
     * @return A list of job applications for the specified company.
     */
    @GetMapping("/Email/{companyEmail}")
    public ResponseEntity<?> getApplicationsByCompanyEmail(
            @PathVariable String companyEmail) {
        List<JobApplication> applications = jobApplicationService.findByCompanyEmail(companyEmail);

        if (applications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No content found
        }

        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    /**
     * Downloads the resume for a specific job application.
     *
     * @param id The ID of the job application.
     * @return The resume file as a byte array.
     */
    @GetMapping("/downloadResume/{id}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable Long id) {
        try {
            byte[] resumeData = jobApplicationService.getResumeById(id);  // Updated method call

            if (resumeData == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "resume.pdf");

            return new ResponseEntity<>(resumeData, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception details
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves job applications by company ID.
     *
     * @param companyId The ID of the company.
     * @return A list of job applications for the specified company.
     */
    @GetMapping("/Id/{companyId}")
    public ResponseEntity<?> getApplicationsByCompanyId(@PathVariable Long companyId) {
        try {
            List<JobApplication> applications = jobApplicationService.findByCompanyId(companyId);
            if (applications.isEmpty()) {
                return new ResponseEntity<>("No applications found for the company ID.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception details
            return new ResponseEntity<>("Error retrieving applications: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
