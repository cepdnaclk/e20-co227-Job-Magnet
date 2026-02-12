package com.example.jobportal.repository;

import com.example.jobportal.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobPostingRepo extends JpaRepository<JobPosting, Long> {

    @Query("SELECT  j from JobPosting j where lower(j.jobTitle) like lower(CONCAT('%',:jobTitle,'%'))")
    List<JobPosting> findByJobTitle(String jobTitle);
    @Query("SELECT j FROM JobPosting j WHERE LOWER(j.jobType) LIKE LOWER(CONCAT('%', :jobType, '%'))")
    List<JobPosting> findByJobType(String jobType);

    @Query("SELECT j FROM JobPosting j WHERE LOWER(j.jobPosition) LIKE LOWER(CONCAT('%', :jobPosition, '%'))")
    List<JobPosting> findByJobPosition(String jobPosition);

    @Query("SELECT j FROM JobPosting j WHERE LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<JobPosting> findByJobLocation(String location);
}
