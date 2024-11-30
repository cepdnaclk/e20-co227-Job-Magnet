package com.example.jobportal.controller;

import com.example.jobportal.dto.AdminDTO;
import com.example.jobportal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {



    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<String> createAdmin(@RequestBody AdminDTO adminDTO) {
        adminService.saveAdmin(adminDTO);
        return ResponseEntity.ok("Admin created successfully");
    }
    @GetMapping("/create")
    public String showCreateAdminForm(Model model) {
        model.addAttribute("adminDTO", new AdminDTO());
        return "createAdmin";  // Returns the createAdmin.html page
    }
}
