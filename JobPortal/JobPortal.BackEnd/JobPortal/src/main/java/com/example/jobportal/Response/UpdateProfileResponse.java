package com.example.jobportal.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileResponse {
    String message;
    Boolean status;
    public UpdateProfileResponse(String message,Boolean status){
        this.message = message;
        this.status = status;
    }

    @Override
    public String toString() {
        return "UpdateProfileResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }

}
