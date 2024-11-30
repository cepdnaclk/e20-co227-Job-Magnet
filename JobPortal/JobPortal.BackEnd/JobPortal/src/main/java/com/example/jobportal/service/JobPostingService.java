package com.example.jobportal.service;


import com.example.jobportal.entity.JobPosting;
import com.example.jobportal.repository.JobPostingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobPostingService {


    @Autowired
    private JobPostingRepo jobPostingRepo;

    public JobPosting postJob(JobPosting jobPosting) {
        return jobPostingRepo.save(jobPosting);
    }
    public Optional<JobPosting> getJobPostingById(Long id) {
        return jobPostingRepo.findById(id);
    }
    public List<JobPosting> getAllJobPostings() {
        return jobPostingRepo.findAll();
    }
    public List<JobPosting> searchJobPostingByTitle(String job_title) {
        return jobPostingRepo.findByJobTitle(job_title);
    }
    public List<JobPosting> searchJobPostingByJobType(String job_type) {
        return jobPostingRepo.findByJobType(job_type);
    }


    public boolean deleteJobPostingById(Long id) {
        Optional<JobPosting> jobPosting = jobPostingRepo.findById(id);
        if (jobPosting.isPresent()) {
            jobPostingRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<JobPosting> searchJobPostingByPosition(String jobPosition) {
        return jobPostingRepo.findByJobPosition(jobPosition);
    }

    public List<JobPosting> searchJobPostingByLocation(String location) {
        return jobPostingRepo.findByJobLocation(location);
    }
}
