package com.example.jobportal.service;

import com.example.jobportal.dto.RegJobEmployerDTO;
import com.example.jobportal.entity.RegJobEmployers;

import java.util.List;
import java.util.Optional;


public interface RegJobEmployerService {
    RegJobEmployers Reg(RegJobEmployerDTO regJobEmployerDTO);


    List<RegJobEmployers> getAllEmployers();

    Optional<RegJobEmployers> getEmployerProfile(String email);
    void updateEmployerProfile(String email,RegJobEmployers updatedProfile);

    RegJobEmployers getJobEmployerById(Long id);

    void DeleteJobEmployer(Long id);

    RegJobEmployers getJobEmployerByEmail(String email);
}
