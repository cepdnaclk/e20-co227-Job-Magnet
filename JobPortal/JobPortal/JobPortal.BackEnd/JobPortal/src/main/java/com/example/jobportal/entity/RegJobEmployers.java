package com.example.jobportal.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Entity
@Table(name = "regjobemployers")
@Data
@Getter
@Setter

public class RegJobEmployers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regid",unique = true)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "address")
    private String address;

    @Email(message = "Email should be valid")
    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name = "govreg_No")
    private String govRegNo;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "about",columnDefinition = "LONGTEXT")
    private String about;

    @Column(name="corevalues",columnDefinition = "LONGTEXT")
    private String coreValues;

    @Column(name = "years")
    private Integer years;

    @Column(name = "no_of_employees")
    private  Long no_of_Employees;

    @Column(name = "projectscompleted")
    private Integer projectsCompleted;
    @Lob
    @Column(name = "companyLogo",columnDefinition="LONGBLOB")
    private byte[] companyLogo;

    @Lob
    @Column(name = "bannerImage",columnDefinition="LONGBLOB")
    private byte[] bannerImage;

    @Override
    public String toString() {
        return "RegJobEmployers{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", govRegNo='" + govRegNo + '\'' +
                ", password='" + password + '\'' +
                // ", phone='" + phone + '\'' +
                ", corevalues='" + coreValues + '\'' +
                ", years='" + years + '\'' +
                ", no_of_employees='" + no_of_Employees + '\'' +
                ", projectscompleted='" + projectsCompleted + '\'' +
                ", companyLogo=" + Arrays.toString(companyLogo) +
                ", bannerImage=" + Arrays.toString(bannerImage) +
                '}';

    }


}

