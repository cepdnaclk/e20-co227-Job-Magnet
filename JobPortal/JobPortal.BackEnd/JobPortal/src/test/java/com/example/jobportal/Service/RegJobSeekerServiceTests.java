package com.example.jobportal.Service;

import com.example.jobportal.entity.RegJobSeeker;
import com.example.jobportal.repository.RegJobSeekerRepo;
import com.example.jobportal.service.RegJobSeekerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegJobSeekerServiceTests {
    @Mock
    private RegJobSeekerRepo regJobSeekerRepo;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private RegJobSeekerServiceImpl regJobSeekerService;
/*
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        regJobSeekerService = new RegJobSeekerServiceImpl(regJobSeekerRepo, regJobSeekerRepo);
    }
*/

    @Test
    public void testGetAllJobSeekers() {
        // Create sample data
        RegJobSeeker regJobSeeker1 = RegJobSeeker.builder()
                .fname("Jane1")
                .lname("Smith1")
                .email("jane1.smith1@example.com")
                .dob(LocalDate.of(1991, 7, 21))
                .password("encodedPassword1")
                .build();

        RegJobSeeker regJobSeeker2 = RegJobSeeker.builder()
                .fname("Jane2")
                .lname("Smith2")
                .email("jane1.smith2@example.com")
                .dob(LocalDate.of(1992, 8, 22))
                .password("encodedPassword2")
                .build();

        // Mock repository
        when(regJobSeekerRepo.findAll()).thenReturn(Arrays.asList(regJobSeeker1, regJobSeeker2));

        // Call service method
        List<RegJobSeeker> jobSeekers = regJobSeekerService.getAllJobSeekers();

        // Assertions
        assertNotNull(jobSeekers);
        assertFalse(jobSeekers.isEmpty());
        assertEquals(2, jobSeekers.size());
    }

    @Test
    public void testGetJobSeekerById() {
        // Create sample data
        RegJobSeeker regJobSeeker = RegJobSeeker.builder()
                .id(1L)
                .fname("Jane")
                .lname("Smith")
                .email("jane.smith@example.com")
                .dob(LocalDate.of(1991, 7, 21))
                .password("encodedPassword")
                .build();

        // Mock repository
        when(regJobSeekerRepo.findById(1L)).thenReturn(Optional.of(regJobSeeker));

        // Call service method
        RegJobSeeker found = regJobSeekerService.getJobSeekerById(1L);

        // Assertions
        assertNotNull(found);
        assertEquals(1L, found.getId());
        assertEquals("Jane", found.getFname());
    }

    @Test
    public void testGetJobSeekerByFname() {
        // Create sample data
        RegJobSeeker regJobSeeker = RegJobSeeker.builder()
                .fname("Jane")
                .lname("Smith")
                .email("jane.smith@example.com")
                .dob(LocalDate.of(1991, 7, 21))
                .password("encodedPassword")
                .build();

        // Mock repository
        when(regJobSeekerRepo.findByFname("Jane")).thenReturn(regJobSeeker);

        // Call service method
        RegJobSeeker found = regJobSeekerService.getJobSeekerByFname("Jane");

        // Assertions
        assertNotNull(found);
        assertEquals("Jane", found.getFname());
    }

    @Test
    public void testGetJobSeekerByEmail() {
        // Create sample data
        RegJobSeeker regJobSeeker = RegJobSeeker.builder()
                .fname("Jane")
                .lname("Smith")
                .email("jane.smith@example.com")
                .dob(LocalDate.of(1991, 7, 21))
                .password("encodedPassword")
                .build();

        // Mock repository
        when(regJobSeekerRepo.findByEmail("jane.smith@example.com")).thenReturn(Optional.of(regJobSeeker));

        // Call service method
        RegJobSeeker found = regJobSeekerService.getJobSeekerByEmail("jane.smith@example.com");

        // Assertions
        assertNotNull(found);
        assertEquals("jane.smith@example.com", found.getEmail());
    }

    @Test
    public void testDeleteJobSeeker() {
        // Mock repository behavior
        Long jobSeekerId = 1L;
        doNothing().when(regJobSeekerRepo).deleteById(jobSeekerId);

        // Call service method
        regJobSeekerService.DeleteJobSeeker(jobSeekerId);

        // Verify repository interaction
        verify(regJobSeekerRepo, times(1)).deleteById(jobSeekerId);
    }
}
