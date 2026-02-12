package com.example.jobportal.dto;

import lombok.Data;

@Data
public class EmpUpdateProfileDTO {

    private Long id;
    private String email;
    private String companyName;
    private String address;
    private String password;
    private String coreValues;
    private String about;
    private int years;
    private Long no_of_Employees;
    private int projectsCompleted;

}
