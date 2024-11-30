package com.example.jobportal.service;

import com.example.jobportal.dto.RegJobSeekerDTO;
import com.example.jobportal.entity.RegJobSeeker;
import com.example.jobportal.repository.RegJobSeekerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Service
public class RegJobSeekerServiceImpl implements RegJobSeekerService {
    private final RegJobSeekerRepo jobSeekerRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegJobSeekerServiceImpl(RegJobSeekerRepo jobSeekerRepo) {
        this.jobSeekerRepo = jobSeekerRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public RegJobSeeker Reg(RegJobSeekerDTO jobSeekerDTO) {

        RegJobSeeker regJobSeeker = new RegJobSeeker();
        regJobSeeker.setFname(jobSeekerDTO.getFname());
        regJobSeeker.setLname(jobSeekerDTO.getLname());
        regJobSeeker.setEmail(jobSeekerDTO.getEmail());
        regJobSeeker.setDob(jobSeekerDTO.getDob());


        regJobSeeker.setPassword(passwordEncoder.encode(jobSeekerDTO.getPassword()));

        return jobSeekerRepo.save(regJobSeeker);
    }

    public Optional<RegJobSeeker> getJobSeekerProfile(String email){
        return jobSeekerRepo.findByEmail(email);
    }



    public RegJobSeeker findByEmail(String email) {
        return jobSeekerRepo.findByEmail(email).orElse(null);
    }

    @Override
    public List<RegJobSeeker> getAllJobSeekers() {
        return jobSeekerRepo.findAll();
    }

    public RegJobSeeker getJobSeekerById(Long id) {
        return jobSeekerRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Job Seeker not found with id: " + id));
    }

    public RegJobSeeker getJobSeekerByFname(String fname) {

        return jobSeekerRepo.findByFname(fname);
    }

    public RegJobSeeker getJobSeekerProfile(Long userId) {
        return jobSeekerRepo.findById(userId).orElseThrow(() -> new RuntimeException("Job seeker profile not found for userId: " + userId));
    }


    public void updateJobSeekerProfile(RegJobSeeker updatedProfile) {
        Optional<RegJobSeeker> existingJobSeekerOptional = jobSeekerRepo.findById(updatedProfile.getId());

        if (existingJobSeekerOptional.isPresent()) {
            RegJobSeeker existingJobSeeker = existingJobSeekerOptional.get();

            // Update the fields that can be updated
            existingJobSeeker.setFname(updatedProfile.getFname());
            existingJobSeeker.setLname(updatedProfile.getLname());
            existingJobSeeker.setDob(updatedProfile.getDob());



            // Save the updated profile
            jobSeekerRepo.save(existingJobSeeker);
        } else {
            // Handle if the job seeker profile is not found (optional)
            throw new RuntimeException("Job Seeker profile not found with id: " + updatedProfile.getId());
        }
    }

    public RegJobSeeker getJobSeekerByLname(String lname){

        return jobSeekerRepo.findByLname(lname);
    }

    public RegJobSeeker getJobSeekerByEmail(String email){
        Optional<RegJobSeeker> optionalJobSeeker = jobSeekerRepo.findByEmail(email);
        return optionalJobSeeker.orElse(null);
    }

    public RegJobSeeker SaveJobSeeker(RegJobSeeker jobSeeker){
        return jobSeekerRepo.save(jobSeeker);
    }
    public void DeleteJobSeeker(Long id){
        jobSeekerRepo.deleteById(id);
    }

    public void save(RegJobSeeker jobSeeker) {
        try {
            if (jobSeeker == null) {
                throw new IllegalArgumentException("JobSeeker object cannot be null");
            }
            jobSeekerRepo.save(jobSeeker);
            System.out.println("JobSeeker saved successfully: " + jobSeeker);
        } catch (Exception e) {
            System.err.println("Error saving JobSeeker: " + e.getMessage());
            e.printStackTrace();
            // Handle the error accordingly
        }


    }


    public void updateJobSeekerProfileWithImage(String email, RegJobSeekerDTO updatedProfileDTO, byte[] profilePicture) {
        Optional<RegJobSeeker> existingJobSeekerOptional = jobSeekerRepo.findByEmail(email);
        if (existingJobSeekerOptional.isPresent()) {
            RegJobSeeker existingJobSeeker = existingJobSeekerOptional.get();
            // Update fields
            existingJobSeeker.setFname(updatedProfileDTO.getFname());
            existingJobSeeker.setLname(updatedProfileDTO.getLname());
            existingJobSeeker.setDob(updatedProfileDTO.getDob());
            existingJobSeeker.setExperience(updatedProfileDTO.getExperience());
            existingJobSeeker.setSkills(updatedProfileDTO.getSkills());
            existingJobSeeker.setBio(updatedProfileDTO.getBio());
            existingJobSeeker.setPhone(updatedProfileDTO.getPhone());
            if (profilePicture != null) {
                existingJobSeeker.setProfilePicture(profilePicture);
            }
            jobSeekerRepo.save(existingJobSeeker);
        } else {
            throw new RuntimeException("Job Seeker profile not found with email: " + email);
        }
    }

}
