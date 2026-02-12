package com.example.jobportal.Response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    String message;
    Boolean status;
    String email;
    String usertype;



    public LoginResponse(String message, Boolean status,String email,String usertype) {
        this.message = message;
        this.status = status;
        this.email=email;
        this.usertype=usertype;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", usertype='" + usertype + '\'' +
                '}';
    }



}
