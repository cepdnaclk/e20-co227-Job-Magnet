package com.example.jobportal.service;

import com.example.jobportal.Response.LoginResponse;
import com.example.jobportal.Response.UpdateProfileResponse;

import com.example.jobportal.dto.EmpUpdateProfileDTO;
import com.example.jobportal.dto.LoginDTO;
import com.example.jobportal.dto.UpdateJobSeekerProfileDTO;
import com.example.jobportal.entity.RegJobSeeker;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    LoginResponse login(LoginDTO loginDTO);

    //public boolean isEmailExists(String email);

    ResponseEntity<String> validateResetToken(String token);

    boolean isValidEmail(String email);

    LoginResponse loginAdmin(LoginDTO loginDTO);

    public boolean sendResetLink(String email);
    UpdateProfileResponse updateProfile(UpdateJobSeekerProfileDTO updateJobSeekerProfileDTO);
    UpdateProfileResponse empUpdateProfile(EmpUpdateProfileDTO empUpdateProdileDTO);

    public void save(RegJobSeeker jobSeeker) ;

    boolean resetPassword(String email, String newPassword);
}