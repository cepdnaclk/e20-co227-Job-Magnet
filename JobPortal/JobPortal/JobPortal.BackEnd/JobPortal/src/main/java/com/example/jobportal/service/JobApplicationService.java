package com.example.jobportal.service;

import com.example.jobportal.entity.JobApplication;
import com.example.jobportal.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for handling job application-related operations.
 */
@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    /**
     * Saves the resume file.
     *
     * @param resume The resume file to save.
     * @return The resume data as a byte array.
     * @throws IOException If the file is empty or an error occurs while reading the file.
     */
    public byte[] saveResume(MultipartFile resume) throws IOException {
        if (resume.isEmpty()) {
            throw new IOException("Failed to store empty file");
        }
        return resume.getBytes();  // Return the byte array of the file
    }

    /**
     * Applies for a job.
     *
     * @param jobId The ID of the job to apply for.
     * @param seekerId The ID of the job seeker.
     * @param seekerName The name of the job seeker.
     * @param seekerEmail The email of the job seeker.
     * @param companyId The ID of the company.
     * @param companyEmail The email of the company.
     * @param resume The resume data as a byte array.
     * @return The created job application.
     */
    public JobApplication applyToJob(Long jobId, Long seekerId, String seekerName, String seekerEmail, Long companyId, String companyEmail, byte[] resume) {
        JobApplication application = new JobApplication();
        application.setJobId(jobId);
        application.setSeekerId(seekerId);
        application.setCompanyId(companyId);
        application.setCompanyEmail(companyEmail);
        application.setSeekerName(seekerName);
        application.setSeekerEmail(seekerEmail);
        application.setStatus("Applied");
        application.setAppliedDate(LocalDateTime.now());
        application.setResume(resume);  // Set the binary resume data

        return jobApplicationRepository.save(application);
    }

    /**
     * Finds job applications by company ID.
     *
     * @param companyId The ID of the company.
     * @return A list of job applications for the specified company.
     */
    public List<JobApplication> findByCompanyId(Long companyId) {
        return jobApplicationRepository.findByCompanyId(companyId);
    }

    /**
     * Finds job applications by company email.
     *
     * @param companyEmail The email of the company.
     * @return A list of job applications for the specified company.
     */
    public List<JobApplication> findByCompanyEmail(String companyEmail) {
        return jobApplicationRepository.findByCompanyEmail(companyEmail);
    }

    /**
     * Retrieves the resume for a specific job application by its ID.
     *
     * @param id The ID of the job application.
     * @return The resume data as a byte array, or null if not found.
     */
    public byte[] getResumeById(Long id) {
        JobApplication application = jobApplicationRepository.findById(id).orElse(null);
        if (application != null && application.getResume() != null) {
            return application.getResume();
        }
        return null; // Or throw an exception if preferred
    }
}
