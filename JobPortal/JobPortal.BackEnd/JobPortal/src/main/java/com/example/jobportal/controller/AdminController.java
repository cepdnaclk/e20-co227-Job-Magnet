package com.example.jobportal.controller;

import com.example.jobportal.dto.AdminDTO;
import com.example.jobportal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling administrator-related operations.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {



    @Autowired
    private AdminService adminService;

    /**
     * Creates a new administrator.
     *
     * @param adminDTO The data transfer object containing the admin's details.
     * @return A response entity with a success message.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createAdmin(@RequestBody AdminDTO adminDTO) {
        adminService.saveAdmin(adminDTO);
        return ResponseEntity.ok("Admin created successfully");
    }
    /**
     * Shows the form for creating a new administrator.
     *
     * @param model The model to which the admin DTO will be added.
     * @return The name of the view to render.
     */
    @GetMapping("/create")
    public String showCreateAdminForm(Model model) {
        model.addAttribute("adminDTO", new AdminDTO());
        return "createAdmin";  // Returns the createAdmin.html page
    }
}
