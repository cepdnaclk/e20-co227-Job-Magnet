package com.example.jobportal.repository;

import com.example.jobportal.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByJobId(Long jobId);
    List<JobApplication> findBySeekerId(Long seekerId);
    List<JobApplication> findByCompanyId(Long companyId);
    List<JobApplication> findByCompanyEmail(String companyEmail);

}
