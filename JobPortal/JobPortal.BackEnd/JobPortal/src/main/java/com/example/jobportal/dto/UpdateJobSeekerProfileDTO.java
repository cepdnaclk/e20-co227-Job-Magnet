package com.example.jobportal.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UpdateJobSeekerProfileDTO {


    private Long id;
    private String email;
    private String fname;
    private String lname;
    private LocalDate dob;
    private String password;
    private String skills;
    private String experience;
    private String phone;
    private String bio;
    private String country;
    private byte[] profilePicture;
    public String university;
    public String degree;
    public String dclass;
    public String projects;
    public String about;
}
