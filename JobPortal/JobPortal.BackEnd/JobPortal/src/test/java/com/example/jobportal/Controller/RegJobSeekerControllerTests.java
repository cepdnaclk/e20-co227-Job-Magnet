package com.example.jobportal.Controller;

import com.example.jobportal.controller.RegJobSeekerController;
import com.example.jobportal.dto.RegJobSeekerDTO;
import com.example.jobportal.entity.RegJobSeeker;
import com.example.jobportal.service.RegJobSeekerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegJobSeekerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RegJobSeekerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegJobSeekerServiceImpl regJobSeekerService;

    @MockBean
    private PasswordEncoder passwordEncoder;  // Mock the PasswordEncoder

    @Autowired
    private ObjectMapper objectMapper;

    // Test case for job seeker registration with password encoding
    @Test
    public void testRegisterJobSeeker() throws Exception {
        // Mock the password encoding
        given(passwordEncoder.encode("password")).willReturn("encodedPassword");

        // Create the job seeker with the encoded password
        RegJobSeeker regJobSeeker = RegJobSeeker.builder()
                .id(1L)
                .fname("Jane")
                .lname("Smith")
                .email("jane.smith@example.com")
                .dob(LocalDate.of(1991, 7, 21))
                .password("encodedPassword")  // Use the encoded password
                .build();

        RegJobSeekerDTO regJobSeekerDTO = RegJobSeekerDTO.builder()
                .fname("Jane")
                .lname("Smith")
                .email("jane.smith@example.com")
                .dob(LocalDate.of(1991, 7, 21))
                .password("password")  // Raw password sent in the request
                .build();

        // Mock the service to return the registered job seeker
        given(regJobSeekerService.Reg(any(RegJobSeekerDTO.class))).willReturn(regJobSeeker);

        // Perform the registration request
        ResultActions response = mockMvc.perform(post("/api/jobseekers/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(regJobSeekerDTO)));

        // Assert the response
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Registration successful with ID: 1"));
    }

    // Test case for deleting a job seeker by ID
    @Test
    public void testDeleteJobSeeker() throws Exception {
        Long regId = 1L;
        doNothing().when(regJobSeekerService).DeleteJobSeeker(regId);

        mockMvc.perform(delete("/api/jobseekers/deletebyid/{id}", regId))
                .andExpect(status().isNoContent());
    }

    // Test case for fetching all job seekers with encoded passwords
    @Test
    public void testGetAllJobSeekers() throws Exception {
        // Mock the password encoding for both job seekers
        given(passwordEncoder.encode("password1")).willReturn("encodedPassword1");
        given(passwordEncoder.encode("password2")).willReturn("encodedPassword2");

        RegJobSeeker regJobSeeker1 = RegJobSeeker.builder()
                .id(1L)
                .fname("Jane1")
                .lname("Smith1")
                .email("jane1.smith1@example.com")
                .dob(LocalDate.of(1991, 7, 21))
                .password("encodedPassword1")  // Use the encoded password
                .build();

        RegJobSeeker regJobSeeker2 = RegJobSeeker.builder()
                .id(2L)
                .fname("Jane2")
                .lname("Smith2")
                .email("jane2.smith2@example.com")
                .dob(LocalDate.of(1991, 7, 21))
                .password("encodedPassword2")  // Use the encoded password
                .build();

        List<RegJobSeeker> regJobSeekerList = Arrays.asList(regJobSeeker1, regJobSeeker2);

        // Mock the service to return all job seekers
        given(regJobSeekerService.getAllJobSeekers()).willReturn(regJobSeekerList);

        // Perform the request to fetch all job seekers
        mockMvc.perform(get("/api/jobseekers/getall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].fname").value("Jane1"))
                .andExpect(jsonPath("$[1].fname").value("Jane2"));
    }

    // Test case for searching a job seeker by first name
    @Test
    public void testGetJobSeekerByFname() throws Exception {
        RegJobSeeker regJobSeeker = RegJobSeeker.builder()
                .id(1L)
                .fname("Jane")
                .lname("Smith")
                .email("jane.smith@example.com")
                .dob(LocalDate.of(1991, 7, 21))
                .password("encodedPassword")  // Use the encoded password
                .build();

        // Mock the service to return the job seeker
        given(regJobSeekerService.getJobSeekerByFname("Jane")).willReturn(regJobSeeker);

        // Perform the request to search job seeker by first name
        mockMvc.perform(get("/api/jobseekers/searchbyfname/Jane"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fname").value("Jane"))
                .andExpect(jsonPath("$.lname").value("Smith"));
    }

    // Test case for fetching a job seeker by ID
    @Test
    public void testGetJobSeekerById() throws Exception {
        RegJobSeeker regJobSeeker = RegJobSeeker.builder()
                .id(1L)
                .fname("Jane")
                .lname("Smith")
                .email("jane.smith@example.com")
                .dob(LocalDate.of(1991, 7, 21))
                .password("encodedPassword")  // Use the encoded password
                .build();

        // Mock the service to return the job seeker
        given(regJobSeekerService.getJobSeekerById(1L)).willReturn(regJobSeeker);

        // Perform the request to fetch job seeker by ID
        mockMvc.perform(get("/api/jobseekers/searchbyid/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fname").value("Jane"))
                .andExpect(jsonPath("$.lname").value("Smith"));
    }
}
