package com.example.jobportal.service;

import com.example.jobportal.dto.AdminDTO;
import com.example.jobportal.entity.Admin;
import com.example.jobportal.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for handling administrator-related operations.
 */
@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Autowire the BCryptPasswordEncoder

    /**
     * Creates a new administrator.
     *
     * @param adminDTO The data transfer object containing the admin's details.
     */
    public void createAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setEmail(adminDTO.getEmail());
        // Encode the password before saving
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        adminRepository.save(admin);
    }

    /**
     * Saves a new administrator.
     *
     * @param adminDTO The data transfer object containing the admin's details.
     * @return The saved administrator entity.
     */
    public Admin saveAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setEmail(adminDTO.getEmail());
        // Encrypt the password before saving
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        return adminRepository.save(admin);
    }
}