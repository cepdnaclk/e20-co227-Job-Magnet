package com.example.jobportal.service;

import com.example.jobportal.entity.JobPosting;
import com.example.jobportal.exception.ResourceNotFoundException;
import com.example.jobportal.repository.PostedJobsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostedJobsService {

    @Autowired
    private PostedJobsRepo postedJobsRepo;


    // Return a list of job postings by company ID
    public List<JobPosting> getJobsByCompanyId(Long companyId) {
        return postedJobsRepo.findByCompanyId(companyId); // Should return a List<JobPosting>
    }

    public List<JobPosting> getAllJobs() {
        return postedJobsRepo.findAll();
    }

    public JobPosting getJobById(long jobId) {
        return postedJobsRepo.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
    }

    // Search jobs by company and keyword
    public List<JobPosting> searchJobsByKeywordAndCompany(String keyword, Long companyId) {
        return postedJobsRepo.findByCompanyIdAndJobTitleContainingOrDescriptionContaining(companyId, keyword, keyword);
    }

    public JobPosting updateJobPosting(long jobId, JobPosting updatedJob) {
        JobPosting existingJob = postedJobsRepo.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        // Update fields if they are not null or empty
        updateField(existingJob::setJobTitle, updatedJob.getJobTitle());
        updateField(existingJob::setJobType, updatedJob.getJobType());
        updateField(existingJob::setJobPosition, updatedJob.getJobPosition());
        updateField(existingJob::setLocation, updatedJob.getLocation());
        updateField(existingJob::setPrimaryskill, updatedJob.getPrimaryskill());
        updateField(existingJob::setSecondaryskill, updatedJob.getSecondaryskill());
        updateField(existingJob::setOtherskills, updatedJob.getOtherskills());
        updateField(existingJob::setExperiencerequired, updatedJob.getExperiencerequired());
        updateField(existingJob::setSalary, updatedJob.getSalary());
        updateField(existingJob::setResponsibilities, updatedJob.getResponsibilities());
        updateField(existingJob::setDescription, updatedJob.getDescription());

        // Update availability
        existingJob.setAvailability(updatedJob.isAvailability());

        return postedJobsRepo.save(existingJob);
    }
    public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
        List<JobPosting> findByCompanyId(Long companyId);
    }

    // Helper method to update field if value is not null or empty
    private void updateField(java.util.function.Consumer<String> setter, String value) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        }
    }
}
