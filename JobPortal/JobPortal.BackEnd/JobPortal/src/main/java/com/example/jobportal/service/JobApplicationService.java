package com.example.jobportal.service;

import com.example.jobportal.entity.JobApplication;
import com.example.jobportal.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public byte[] saveResume(MultipartFile resume) throws IOException {
        if (resume.isEmpty()) {
            throw new IOException("Failed to store empty file");
        }
        return resume.getBytes();  // Return the byte array of the file
    }

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

    public List<JobApplication> findByCompanyId(Long companyId) {
        return jobApplicationRepository.findByCompanyId(companyId);
    }

    public List<JobApplication> findByCompanyEmail(String companyEmail) {
        return jobApplicationRepository.findByCompanyEmail(companyEmail);
    }

    public byte[] getResumeById(Long id) {
        JobApplication application = jobApplicationRepository.findById(id).orElse(null);
        if (application != null && application.getResume() != null) {
            return application.getResume();
        }
        return null; // Or throw an exception if preferred
    }
}
