package com.example.jobportal.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JobPostingDTO {
    private Long jobId;
    private Long companyId;
    private String jobPosition;
    private String Location;
    private String primarySkills;
    private String secondarySkills;
    private String otherSkills;
    private String other;
    private String title;
    private String type;
    private String responsibilities;
    public String experience;
    public String salary;

    public JobPostingDTO(Long companyId, Long jobId, String jobPosition, String location, String primarySkills, String secondarySkills, String title, String type, String responsibilities, String experience, String salary) {
        this.companyId = companyId;
        this.jobId = jobId;
        this.jobPosition = jobPosition;
        this.Location = location;
        this.primarySkills = primarySkills;
        this.secondarySkills = secondarySkills;
        this.title = title;
        this.type = type;
        this.responsibilities = responsibilities;
        this.experience = experience;
        this.salary = salary;
    }


}
