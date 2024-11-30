package com.example.jobportal.Controller;

import com.example.jobportal.controller.LoginController;
import com.example.jobportal.dto.LoginDTO;
import com.example.jobportal.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void login_Failure() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("user@example.com");
        loginDTO.setPassword("wrongpassword");

        when(loginService.login(loginDTO)).thenThrow(new RuntimeException("Invalid credentials"));

        // Act
        ResponseEntity<Map<String, Object>> response = loginController.login(loginDTO);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("error", response.getBody().get("status"));
        assertTrue(response.getBody().get("message").toString().contains("An error occurred: Invalid credentials"));
    }
/*
    @Test
    void updateProfile_Success() {
        // Arrange
        UpdateJobSeekerProfileDTO updateProfileDTO = new UpdateJobSeekerProfileDTO();
        UpdateProfileResponse updateProfileResponse = new UpdateProfileResponse();
        updateProfileResponse.setStatus("success");
        updateProfileResponse.setMessage("Profile updated successfully");

        when(loginService.updateProfile(updateProfileDTO)).thenReturn(updateProfileResponse);

        // Act
        ResponseEntity<Map<String, Object>> response = loginController.updateProfile(updateProfileDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().get("status"));
        assertEquals("Profile updated successfully", response.getBody().get("message"));
    }

    @Test
    void empUpdateProfile_Failure() {
        // Arrange
        EmpUpdateProdileDTO empUpdateProdileDTO = new EmpUpdateProdileDTO();
        when(loginService.empUpdateProfile(empUpdateProdileDTO)).thenThrow(new RuntimeException("Update failed"));

        // Act
        ResponseEntity<Map<String, Object>> response = loginController.empUpdateProfile(empUpdateProdileDTO);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("error", response.getBody().get("status"));
        assertTrue(response.getBody().get("message").toString().contains("An error occurred: Update failed"));
    }

    @Test
    void sendResetLink_Success() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("email", "user@example.com");

        when(loginService.sendResetLink("user@example.com")).thenReturn(true); // This part is correct

        // Act
        ResponseEntity<Map<String, String>> response = loginController.sendResetLink(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Reset link sent", response.getBody().get("message"));
    }


    @Test
    void passwordReset_Success() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("token", "valid-token");
        request.put("newPassword", "newpassword123");

        when(loginService.validateResetToken("valid-token")).thenReturn(ResponseEntity.ok("Token is valid"));
        when(loginService.resetPassword("valid-token", "newpassword123")).thenReturn(true);

        // Act
        ResponseEntity<Map<String, Object>> response = loginController.passwordReset(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password reset successfully", response.getBody().get("message"));
    }*/
}
