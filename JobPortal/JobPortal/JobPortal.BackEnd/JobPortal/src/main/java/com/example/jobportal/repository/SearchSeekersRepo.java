package com.example.jobportal.repository;

import com.example.jobportal.entity.RegJobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchSeekersRepo extends JpaRepository<RegJobSeeker,Long> {
    //@Query("SELECT j FROM RegJobSeeker j where lower(j.experience)like lower(CONCAT('%',:keyword,'%')) OR lower(j.degree) LIKE LOWER(CONCAT('%',:keyword,'%'))OR lower(j.degreeClass) LIKE LOWER(CONCAT('%',:keyword,'%'))OR lower(j.university) LIKE LOWER(CONCAT('%',:keyword,'%')) ")
    //List<RegJobSeeker>searchSeekerByKeyword(String keyword);
    @Query("SELECT j FROM RegJobSeeker j WHERE " +
            "LOWER(j.experience) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.degree) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.dclass) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.university) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.skills) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.projects) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.bio) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<RegJobSeeker> searchSeekerByKeyword(String keyword);

}
