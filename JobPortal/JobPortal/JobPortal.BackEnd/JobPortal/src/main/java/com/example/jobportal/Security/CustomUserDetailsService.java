package com.example.jobportal.Security;

import com.example.jobportal.entity.Admin;
import com.example.jobportal.entity.RegJobEmployers;
import com.example.jobportal.entity.RegJobSeeker;
import com.example.jobportal.repository.AdminRepo;
import com.example.jobportal.repository.RegJobEmployerRepo;
import com.example.jobportal.repository.RegJobSeekerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RegJobSeekerRepo regJobSeekerRepo;
    private final RegJobEmployerRepo regJobEmployerRepo;
    private final AdminRepo adminRepo;

    @Autowired
    public CustomUserDetailsService(RegJobSeekerRepo regJobSeekerRepo,
                                    RegJobEmployerRepo regJobEmployerRepo,
                                    AdminRepo adminRepo) {
        this.regJobSeekerRepo = regJobSeekerRepo;
        this.regJobEmployerRepo = regJobEmployerRepo;
        this.adminRepo = adminRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<RegJobSeeker> seeker = regJobSeekerRepo.findByEmail(email);
        if (seeker.isPresent()) {
            return buildUser(email, seeker.get().getPassword(), "ROLE_SEEKER");
        }

        Optional<RegJobEmployers> employer = regJobEmployerRepo.findByEmail(email);
        if (employer.isPresent()) {
            return buildUser(email, employer.get().getPassword(), "ROLE_EMPLOYER");
        }

        Admin admin = adminRepo.findByEmail(email);
        if (admin != null) {
            return buildUser(email, admin.getPassword(), "ROLE_ADMIN");
        }

        throw new UsernameNotFoundException("User not found: " + email);
    }

    private UserDetails buildUser(String email, String password, String role) {
        return new User(email, password, List.of(new SimpleGrantedAuthority(role)));
    }
}
