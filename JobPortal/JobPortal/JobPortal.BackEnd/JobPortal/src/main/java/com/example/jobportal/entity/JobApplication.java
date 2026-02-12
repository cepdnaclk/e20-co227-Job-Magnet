package com.example.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "JobApplication")
@Data
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "seeker_id", nullable = false)
    private Long seekerId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "company_email")
    private String companyEmail;

    @Column(name = "seeker_name", length = 100)
    private String seekerName;

    @Column(name = "seeker_email", length = 100)
    private String seekerEmail;

    @Lob  // This annotation maps the field to a BLOB or LONGBLOB in the database
    @Column(name = "resume", columnDefinition = "LONGBLOB")
    private byte[] resume;  // Store the binary data of the resume

    @Column(name = "applied_date", nullable = false)
    private LocalDateTime appliedDate;

    @Column(name = "status", nullable = false)
    private String status;
}
