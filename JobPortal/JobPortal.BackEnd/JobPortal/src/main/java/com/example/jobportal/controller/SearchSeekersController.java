package com.example.jobportal.controller;


import com.example.jobportal.entity.RegJobSeeker;
import com.example.jobportal.service.SearchSeekersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/search-seekers")
public class SearchSeekersController {
    @Autowired
    private SearchSeekersService searchSeekersService;

    @GetMapping("/search/{keyword}")
    public ResponseEntity<?>searchSeekers(@PathVariable("keyword")String keyword){
        List<RegJobSeeker> seekers = new ArrayList<>();
        seekers=searchSeekersService.searchJobSeekerByKeyword(keyword);
        if (seekers.isEmpty()){
            return ResponseEntity.ok("No seekers found ");
        }
        return ResponseEntity.ok(seekers);

    }
}
