package com.example.jobportal.repository;

import com.example.jobportal.entity.RegJobSeeker;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class RegJobSeekerRepoTests {



    @Autowired
    private RegJobSeekerRepo regJobSeekerRepo;


    @BeforeEach
    public void cleanDatabase() {
        regJobSeekerRepo.deleteAll();
        regJobSeekerRepo.flush();
    }


    @Test
    @Rollback(true)             //ensure the database state is reset after each test
    public void testSave(){

        //Arrange
        RegJobSeeker regJobSeeker= RegJobSeeker.builder()
                .fname("John1")
                .lname("Doe1")
                .email("john1.doe@example.com")
                .dob(LocalDate.of(1986, 6, 16))
                .password("password1234")
                .build();

        //Act
        RegJobSeeker registeredJobSeeker = regJobSeekerRepo.save(regJobSeeker);
        Optional<RegJobSeeker> retrievedJobSeeker =regJobSeekerRepo.findById(regJobSeeker.getId());

        //Assert
        Assertions.assertTrue(retrievedJobSeeker.isPresent(), "RegJobSeeker should be present in the repository");
        Assertions.assertEquals("John1", retrievedJobSeeker.get().getFname(), "First name should match");
        Assertions.assertEquals("Doe1", retrievedJobSeeker.get().getLname(), "Last name should match");
        Assertions.assertEquals("john1.doe@example.com", retrievedJobSeeker.get().getEmail(), "Email should match");
        Assertions.assertEquals(LocalDate.of(1986, 6, 16), retrievedJobSeeker.get().getDob(), "DOB should match");
    }

    @Test
    @Rollback(true)
    public void testFindAll() {
        // Arrange
        RegJobSeeker regJobSeeker1 = RegJobSeeker.builder()
                .fname("Jane1")
                .lname("Smith1")
                .email("jane1.smith1@example.com")
                .dob(LocalDate.of(1991, 7, 21))
                .password("password457")
                .build();

        RegJobSeeker regJobSeeker2 = RegJobSeeker.builder()
                .fname("Robert1")
                .lname("Brown1")
                .email("robert1.brown@example.com")
                .dob(LocalDate.of(1989, 9, 30))
                .password("password790")
                .build();

        regJobSeekerRepo.save(regJobSeeker1);
        regJobSeekerRepo.save(regJobSeeker2);

        // Act
        List<RegJobSeeker> jobSeekers = regJobSeekerRepo.findAll();

        // Assert
        Assertions.assertFalse(jobSeekers.isEmpty());
        Assertions.assertEquals(2, jobSeekers.size());
    }


    @Test
    @Rollback(true)
    public void testUpdate() {
        // Arrange
        RegJobSeeker regJobSeeker = RegJobSeeker.builder()
                .fname("John1")
                .lname("Doe1")
                .email("john1.doe@example.com")
                .dob(LocalDate.of(1991, 6, 16))
                .password("initialPassword")
                .build();

        regJobSeeker = regJobSeekerRepo.save(regJobSeeker);

        // Act: Perform the update
        regJobSeeker.setFname("UpdatedFirstName");
        regJobSeeker.setLname("UpdatedLastName");
        regJobSeeker.setEmail("updated.email@example.com");
        regJobSeeker.setDob(LocalDate.of(1988, 8, 20));
        regJobSeeker.setPassword("updatedPassword");

        regJobSeekerRepo.save(regJobSeeker); // Save the updated entity

        // Assert: Retrieve the updated entity and verify
        Optional<RegJobSeeker> updatedJobSeekerOptional = regJobSeekerRepo.findById(regJobSeeker.getId());
        Assertions.assertTrue(updatedJobSeekerOptional.isPresent(), "Updated RegJobSeeker should be present in the repository");

        RegJobSeeker updatedJobSeeker = updatedJobSeekerOptional.get();
        Assertions.assertEquals("UpdatedFirstName", updatedJobSeeker.getFname(), "First name should be updated");
        Assertions.assertEquals("UpdatedLastName", updatedJobSeeker.getLname(), "Last name should be updated");
        Assertions.assertEquals("updated.email@example.com", updatedJobSeeker.getEmail(), "Email should be updated");
        Assertions.assertEquals(LocalDate.of(1988, 8, 20), updatedJobSeeker.getDob(), "DOB should be updated");
        Assertions.assertEquals("updatedPassword", updatedJobSeeker.getPassword(), "Password should be updated");
    }

    @Test
    @Rollback(true)
    public void testDelete() {
        // Arrange
        RegJobSeeker regJobSeeker = RegJobSeeker.builder()
                .fname("Emily")
                .lname("Clark")
                .email("emily.clark1@example.com")
                .dob(LocalDate.of(1992, 12, 10))
                .password("password101")
                .build();

        RegJobSeeker savedJobSeeker = regJobSeekerRepo.save(regJobSeeker);

        // Act
        regJobSeekerRepo.delete(savedJobSeeker);
        Optional<RegJobSeeker> retrievedJobSeeker = regJobSeekerRepo.findById(savedJobSeeker.getId());

        // Assert
        Assertions.assertFalse(retrievedJobSeeker.isPresent());
    }

    //check whether is returns more than one
    @Test
    @Rollback(true)
    public void RegJobSeekerRepo_GetAll_ReturnMoreThanOneRegJobSeeker(){

        RegJobSeeker regJobSeeker1 = RegJobSeeker.builder()
                .fname("John1")
                .lname("Doe1")
                .email("john1.doe@example.com")
                .dob(LocalDate.of(1991, 6, 16))
                .password("initialPassword1")
                .build();

        RegJobSeeker regJobSeeker2 = RegJobSeeker.builder()
                .fname("John2")
                .lname("Doe2")
                .email("john2.doe@example.com")
                .dob(LocalDate.of(1992, 7, 17))
                .password("initialPassword2")
                .build();

        regJobSeekerRepo.save(regJobSeeker1);
        regJobSeekerRepo.save(regJobSeeker2);

        List<RegJobSeeker> regJobSeekerList= regJobSeekerRepo.findAll();


        Assertions.assertNotNull(regJobSeekerList);
        Assertions.assertEquals(2,regJobSeekerList.size());

    }

    @Test
    @Rollback(true)
    public void RegJobSeekerRepo_findById_ReturnsRegJobSeeker() {
        // Arrange
        RegJobSeeker regJobSeeker = RegJobSeeker.builder()
                .fname("Emily1")
                .lname("Clark1")
                .email("emily1.clark1@example.com")
                .dob(LocalDate.of(1993, 01, 11))
                .password("password102")
                .build();

        RegJobSeeker savedJobSeeker = regJobSeekerRepo.save(regJobSeeker);

        // Act
        Optional<RegJobSeeker> findRegisteredJobSeeker = regJobSeekerRepo.findById(savedJobSeeker.getId());

        // Assert
        Assertions.assertNotNull(findRegisteredJobSeeker);
    }

    @Test
    @Rollback(true)
    public void RegJobSeekerRepo_findByFname_ReturnsRegJobSeeker() {
        // Arrange
        RegJobSeeker regJobSeeker = RegJobSeeker.builder()
                .fname("Emily1")
                .lname("Clark1")
                .email("emily1.clark1@example.com")
                .dob(LocalDate.of(1993, 01, 11))
                .password("password102")
                .build();

        RegJobSeeker savedJobSeeker = regJobSeekerRepo.save(regJobSeeker);

        // Act
        RegJobSeeker findRegisteredJobSeeker = regJobSeekerRepo.findByFname(savedJobSeeker.getFname());

        // Assert
        Assertions.assertNotNull(findRegisteredJobSeeker);
        Assertions.assertEquals("Emily1",findRegisteredJobSeeker.getFname());
    }

    @Test
    @Rollback(true)
    public void RegJobSeekerRepo_findByLname_ReturnsRegJobSeeker() {
        // Arrange
        RegJobSeeker regJobSeeker = RegJobSeeker.builder()
                .fname("Emily1")
                .lname("Clark1")
                .email("emily1.clark1@example.com")
                .dob(LocalDate.of(1993, 01, 11))
                .password("password102")
                .build();

        RegJobSeeker savedJobSeeker = regJobSeekerRepo.save(regJobSeeker);

        // Act
        RegJobSeeker findRegisteredJobSeeker = regJobSeekerRepo.findByLname(savedJobSeeker.getLname());

        // Assert
        Assertions.assertNotNull(findRegisteredJobSeeker);
        Assertions.assertEquals("Clark1",findRegisteredJobSeeker.getLname());
    }



}
