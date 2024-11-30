package com.example.jobportal.entity;

import jakarta.persistence.*;

import lombok.*;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
@Table(name="regjobseeker")
//@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class RegJobSeeker {
    //table is auto creating
    /*@Id
    private int RegID;
    private String fname;
    private String lname;
    private Date dob;
    private String username;
    private String password;*/
    //access created table
    @Id
    @Column(name="regid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fname",nullable = false)
    private String fname;

    @Column(name = "lname",nullable = false)
    private String lname;


    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name ="password")
    private String password;

    @Column(name ="country")
    private String country;

    @Column(name="experience")
    private String experience;

    @Column(name="skills")
    private String skills;

    @Column(name="phone")
    private String phone;

    @Column(name="bio")
    private String bio;

    @Column(name = "university")
    private String university;

    @Column(name="degree")
    private String degree;

    @Column(name="degree_class")
    private String dclass;

    @Column(name = "projects_done")
    private String projects;

    @Column(name="about")
    private String about;

    @Lob
    @Column(name="profilePicture", columnDefinition="LONGBLOB")
    private byte[] profilePicture;

    public RegJobSeeker() {
    }

    @Override
    public String toString() {
        return "RegJobSeeker{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", password='" + password + '\'' +
                ", country='" + country + '\'' +
                ", experience='" + experience + '\'' +
                ", skills='" + skills + '\'' +
                ", phone='" + phone + '\'' +
                ", bio='" + bio + '\'' +
                ", university='" + university + '\'' +
                ", degree='" + degree + '\'' +
                ", dclass='" + dclass + '\'' +
                ", projects='" + projects + '\'' +
                ", about='" + about + '\'' +
                ", profilePicture=" + Arrays.toString(profilePicture) +
                '}';
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDclass() {
        return dclass;
    }

    public void setDclass(String dclass) {
        this.dclass = dclass;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    /* public String getCountry() {
         return country;
     }

     public void setCountry(String country) {
         this.country = country;
     }
 */
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }


}
