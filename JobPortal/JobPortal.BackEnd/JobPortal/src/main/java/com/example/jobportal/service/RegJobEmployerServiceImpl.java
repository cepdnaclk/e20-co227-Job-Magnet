package com.example.jobportal.service;

import com.example.jobportal.dto.RegJobEmployerDTO;
import com.example.jobportal.entity.RegJobEmployers;
import com.example.jobportal.repository.RegJobEmployerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegJobEmployerServiceImpl implements RegJobEmployerService {

    private final RegJobEmployerRepo regJobEmployerRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegJobEmployerServiceImpl(RegJobEmployerRepo regJobEmployerRepo, BCryptPasswordEncoder passwordEncoder) {
        this.regJobEmployerRepo = regJobEmployerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegJobEmployers Reg(RegJobEmployerDTO regJobEmployerDTO) {
        RegJobEmployers regJobEmployers = new RegJobEmployers();
        regJobEmployers.setCompanyName(regJobEmployerDTO.getCompanyName());
        regJobEmployers.setEmail(regJobEmployerDTO.getEmail());
        regJobEmployers.setAddress(regJobEmployerDTO.getAddress());
        regJobEmployers.setGovRegNo(regJobEmployerDTO.getGovRegNo());
        regJobEmployers.setPassword(passwordEncoder.encode(regJobEmployerDTO.getPassword()));

        return regJobEmployerRepo.save(regJobEmployers);
    }

    @Override
    public RegJobEmployers getJobEmployerById(Long id) {
        return regJobEmployerRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Employer not found with id: " + id));
    }

    @Override
    public void DeleteJobEmployer(Long id) {
        regJobEmployerRepo.deleteById(id);
    }

    @Override
    public RegJobEmployers getJobEmployerByEmail(String email) {
        return regJobEmployerRepo.findByEmail(email).orElseThrow(() ->
                new RuntimeException("Employer not found with email: " + email));
    }

    @Override
    public List<RegJobEmployers> getAllEmployers() {
        return regJobEmployerRepo.findAll();
    }

    @Override
    public Optional<RegJobEmployers> getEmployerProfile(String email) {
        return regJobEmployerRepo.findByEmail(email);
    }


    @Override
    public void updateEmployerProfile(String email, RegJobEmployers updatedProfile) {
        Optional<RegJobEmployers> existingProfile = regJobEmployerRepo.findByEmail(email); // Corrected here
        if (existingProfile.isPresent()) {
            RegJobEmployers employer = existingProfile.get();
            // Update other fields
            employer.setCompanyLogo(updatedProfile.getCompanyLogo());
            employer.setBannerImage(updatedProfile.getBannerImage());
            regJobEmployerRepo.save(employer); // Corrected here
        } else {
            throw new RuntimeException("Employer not found with email: " + email); // Optional check for not found
        }
    }
}
