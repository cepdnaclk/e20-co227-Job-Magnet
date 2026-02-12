package com.example.jobportal.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
//@AllArgsConstructor
@Data
@Builder
public class RegJobSeekerDTO {
    private String fname;
    private String lname;
    private String email;
    private LocalDate dob;
    private String password;
    private String experience;
    private String skills;
    private String bio;
    private String phone;
    private byte[] profilePicture;
    private String about;

    public RegJobSeekerDTO(String fname, String lname, String email, LocalDate dob, String password, String experience, String skills, String bio, String phone,byte[] profilePicture,String about) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.dob = dob;
        this.password = password;
        this.experience = experience;
        this.skills = skills;
        this.bio = bio;
        this.phone = phone;
        this.profilePicture= profilePicture;
        this.about=about;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
}


