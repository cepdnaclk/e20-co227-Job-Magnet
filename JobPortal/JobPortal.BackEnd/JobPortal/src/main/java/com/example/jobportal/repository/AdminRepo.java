package com.example.jobportal.repository;

import com.example.jobportal.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository <Admin,Long> {


    Admin findByEmail(String providedEmail);
}
