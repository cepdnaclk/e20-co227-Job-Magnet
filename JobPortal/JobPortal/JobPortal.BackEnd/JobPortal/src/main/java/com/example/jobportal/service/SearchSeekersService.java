package com.example.jobportal.service;

import com.example.jobportal.entity.RegJobSeeker;
import com.example.jobportal.repository.SearchSeekersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchSeekersService {
    @Autowired
    private SearchSeekersRepo searchSeekersRepo;

    public List<RegJobSeeker> searchJobSeekerByKeyword(String keyword){
        return searchSeekersRepo.searchSeekerByKeyword(keyword);
    }
}
