package com.example.jobportal.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {

    private String email;
    private String password;
    private String usertype;

    public LoginDTO(){

    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
