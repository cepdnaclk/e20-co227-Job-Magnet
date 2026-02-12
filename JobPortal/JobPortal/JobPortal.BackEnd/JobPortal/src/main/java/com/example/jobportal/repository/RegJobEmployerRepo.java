package com.example.jobportal.repository;


import com.example.jobportal.entity.RegJobEmployers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegJobEmployerRepo extends JpaRepository<RegJobEmployers,Long> {
    Optional<RegJobEmployers> findByEmail(String email) ;


}
