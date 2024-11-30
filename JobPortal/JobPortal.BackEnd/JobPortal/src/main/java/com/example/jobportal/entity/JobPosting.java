package com.example.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "jobposting")
@Data

public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private long jobId;

    @Column(name = "company_id")
    private long companyId;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "job_position")
    private String jobPosition;

    @Column(name = "location")
    private String location;

    @Column(name = "primary_skill",columnDefinition = "LONGTEXT")
    private String primaryskill;

    @Column(name = "secondary_skill",columnDefinition = "LONGTEXT")
    private String secondaryskill;

    @Column(name = "other_skills",columnDefinition = "LONGTEXT")
    private String otherskills;

    @Column(name = "experience_required")
    private String experiencerequired;

    @Column(name = "salary")
    private String salary;

    @Column(name = "responsibilities",columnDefinition = "LONGTEXT")
    private String responsibilities;

    @Column(name = "description",columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "other",columnDefinition = "LONGTEXT")
    private String other;

    @Column(name = "availability")
    private Boolean availability;

    @Column(name = "posted_date")
    @Temporal(TemporalType.TIMESTAMP) // Ensure to store the date and time
    private Date postedDate;

    public JobPosting() {
        this.postedDate = new Date(); // Sets the current date and time
    }

    public Boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void setId(long l) {
    }

    public void setTitle(String softwareEngineer) {

    }
}

