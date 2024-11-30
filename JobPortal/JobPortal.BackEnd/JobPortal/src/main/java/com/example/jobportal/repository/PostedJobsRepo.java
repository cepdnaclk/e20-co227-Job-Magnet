package com.example.jobportal.repository;

import com.example.jobportal.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostedJobsRepo extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findByCompanyIdAndJobTitleContainingOrDescriptionContaining(Long companyId, String titleKeyword, String descriptionKeyword);
    List<JobPosting> findByCompanyId(Long companyId);
}
