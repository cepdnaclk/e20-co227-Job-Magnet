package com.example.jobportal.dto;


import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegJobEmployerDTO {

    private String companyName;

    private String address;

    private String email;

    private String govRegNo;

    private String password;

}
