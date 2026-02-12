package com.example.jobportal.service;


import com.example.jobportal.entity.JobPosting;
import com.example.jobportal.repository.JobPostingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing job postings.
 */
@Service
public class JobPostingService {


    @Autowired
    private JobPostingRepo jobPostingRepo;

    /**
     * Creates a new job posting.
     *
     * @param jobPosting The job posting to create.
     * @return The created job posting.
     */
    public JobPosting postJob(JobPosting jobPosting) {
        return jobPostingRepo.save(jobPosting);
    }
    /**
     * Retrieves a job posting by its ID.
     *
     * @param id The ID of the job posting to retrieve.
     * @return An Optional containing the job posting if found, or an empty Optional otherwise.
     */
    public Optional<JobPosting> getJobPostingById(Long id) {
        return jobPostingRepo.findById(id);
    }
    /**
     * Retrieves all job postings.
     *
     * @return A list of all job postings.
     */
    public List<JobPosting> getAllJobPostings() {
        return jobPostingRepo.findAll();
    }
    /**
     * Searches for job postings by job title.
     *
     * @param job_title The job title to search for.
     * @return A list of job postings with the specified job title.
     */
    public List<JobPosting> searchJobPostingByTitle(String job_title) {
        return jobPostingRepo.findByJobTitle(job_title);
    }
    /**
     * Searches for job postings by job type.
     *
     * @param job_type The job type to search for.
     * @return A list of job postings with the specified job type.
     */
    public List<JobPosting> searchJobPostingByJobType(String job_type) {
        return jobPostingRepo.findByJobType(job_type);
    }


    /**
     * Deletes a job posting by its ID.
     *
     * @param id The ID of the job posting to delete.
     * @return true if the job posting was deleted, false otherwise.
     */
    public boolean deleteJobPostingById(Long id) {
        Optional<JobPosting> jobPosting = jobPostingRepo.findById(id);
        if (jobPosting.isPresent()) {
            jobPostingRepo.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Searches for job postings by position.
     *
     * @param jobPosition The job position to search for.
     * @return A list of job postings with the specified position.
     */
    public List<JobPosting> searchJobPostingByPosition(String jobPosition) {
        return jobPostingRepo.findByJobPosition(jobPosition);
    }

    /**
     * Searches for job postings by location.
     *
     * @param location The location to search for.
     * @return A list of job postings in the specified location.
     */
    public List<JobPosting> searchJobPostingByLocation(String location) {
        return jobPostingRepo.findByJobLocation(location);
    }
}
