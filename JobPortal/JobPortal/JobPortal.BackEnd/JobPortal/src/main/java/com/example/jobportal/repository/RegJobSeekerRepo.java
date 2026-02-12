package com.example.jobportal.repository;

import com.example.jobportal.entity.RegJobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RegJobSeekerRepo extends JpaRepository<RegJobSeeker,Long> {
    Optional<RegJobSeeker> findByEmail(String email);
    //RegJobSeeker findByEmail(String email);

    RegJobSeeker findByFname(String fname);
    Optional<RegJobSeeker> findById(Long id);
    RegJobSeeker findByLname(String lname);

}
