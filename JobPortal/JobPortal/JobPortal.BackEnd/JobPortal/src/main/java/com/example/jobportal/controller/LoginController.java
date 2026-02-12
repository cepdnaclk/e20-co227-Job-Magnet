package com.example.jobportal.controller;


import com.example.jobportal.Response.LoginResponse;
import com.example.jobportal.Response.UpdateProfileResponse;
import com.example.jobportal.Security.JwtService;
import com.example.jobportal.dto.LoginDTO;
import com.example.jobportal.dto.UpdateJobSeekerProfileDTO;
import com.example.jobportal.repository.PasswordResetTokenRepository;
import com.example.jobportal.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Controller for handling user login and authentication related requests.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final LoginService loginService;
    private final JwtService jwtService;
    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    public LoginController(LoginService loginService, JwtService jwtService) {
        this.loginService = loginService;
        this.jwtService = jwtService;
    }




    /**
     * Handles admin login requests.
     *
     * @param loginDTO The login credentials.
     * @return A response entity with the login status.
     */
    @PostMapping("/adminlogin")
    public ResponseEntity<Map<String, Object>> adminLogin(@RequestBody LoginDTO loginDTO) {
        try {
            LoginResponse loginResponse = loginService.loginAdmin(loginDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("status", loginResponse.getStatus());
            response.put("message", loginResponse.getMessage());
            response.put("usertype", loginResponse.getUsertype());
            response.put("email", loginResponse.getEmail()); // Add the email to the response
            if (Boolean.TRUE.equals(loginResponse.getStatus())) {
                String token = jwtService.generateToken(loginResponse.getEmail(), loginResponse.getUsertype());
                response.put("token", token);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the error and return an error response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    /**
     * Handles user login requests.
     *
     * @param loginDTO The login credentials.
     * @return A response entity with the login status.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {
        try {
            LoginResponse loginResponse = loginService.login(loginDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("status", loginResponse.getStatus());
            response.put("message", loginResponse.getMessage());
            response.put("usertype", loginResponse.getUsertype());
            response.put("email", loginResponse.getEmail()); // Add the email to the response
            if (Boolean.TRUE.equals(loginResponse.getStatus())) {
                String token = jwtService.generateToken(loginResponse.getEmail(), loginResponse.getUsertype());
                response.put("token", token);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the error and return an error response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Updates the profile of a job seeker.
     *
     * @param updateProfileDTO The data transfer object containing the updated profile information.
     * @return A response entity with the status of the profile update.
     */
    @PutMapping("/updateJobSeekersProfile")
    public ResponseEntity<Map<String, Object>> updateProfile(
            @RequestBody UpdateJobSeekerProfileDTO updateProfileDTO) {

        try {
            // Update profile details
            UpdateProfileResponse updateProfileResponse = loginService.updateProfile(updateProfileDTO);

            // Prepare response
            Map<String, Object> response = new HashMap<>();
            response.put("status", updateProfileResponse.getStatus());
            response.put("message", updateProfileResponse.getMessage());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the error and return an error response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    /*
        @PutMapping("/updateEmployersProfile")
        public ResponseEntity<Map<String, Object>> empUpdateProfile(@RequestBody EmpUpdateProdileDTO empUpdateProdileDTO) {
            try {
                UpdateProfileResponse updateProfileResponse = loginService.empUpdateProfile(empUpdateProdileDTO);
                Map<String, Object> response = new HashMap<>();
                response.put("status", updateProfileResponse.getStatus());
                response.put("message", updateProfileResponse.getMessage());
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                // Log the error and return an error response
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", "error");
                errorResponse.put("message", "An error occurred: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        }
    */
    /**
     * Sends a password reset link to the specified email address.
     *
     * @param request A map containing the user's email address.
     * @return A response entity with a message indicating the result of the operation.
     */
    @PostMapping("/sendresetlink")
    public ResponseEntity<Map<String, String>> sendResetLink(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email is required"));
        }
        try {
            // Implement the logic to send a reset link email
            boolean value =loginService.sendResetLink(email);
            System.out.println("value is  "+value);
            if (value){
                return ResponseEntity.ok().body(Map.of("message", "Reset link sent"));
            }else{
                System.out.println("hi");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));

            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error sending reset link: " + e.getMessage()));
        }
    }

    /**
     * Resets the user's password using the provided token.
     *
     * @param request A map containing the reset token and the new password.
     * @return A response entity with a message indicating the result of the password reset operation.
     */
    @PostMapping("/resetpasswordfinalpart")
    public ResponseEntity<Map<String, Object>> passwordReset(@RequestBody Map<String, String> request) {
        System.out.println("Received request: " + request);
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        System.out.println("token came to resetting: " + token);
        System.out.println("New Password: " + newPassword);

        // Validate inputs
        if (token == null || token.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Token and new password are required"));
        }

        try {
            System.out.println("validating token\n");
            // Validate token
            ResponseEntity<String> validationResponse = loginService.validateResetToken(token);
            System.out.println("validation response is : "+validationResponse);
            if (validationResponse.getStatusCode() != HttpStatus.OK) {
                System.out.println("validation failed");
                // Token validation failed
                return ResponseEntity.badRequest().body(Map.of("error", validationResponse.getBody()));
            }

           /* Optional<PasswordResetToken> resetTokenOptional = passwordResetTokenRepository.findByToken(token);
            PasswordResetToken resetToken = resetTokenOptional.get();

            // Retrieve the email associated with the token
            String email = resetToken.getEmail();
            System.out.println("Email associated with token: " + email);*/

            // Proceed with resetting the password
            boolean isUpdated = loginService.resetPassword(token, newPassword);
            System.out.println("is updated bool is   "+isUpdated);
            if (isUpdated) {
                return ResponseEntity.ok(Map.of("message", "Password reset successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
            }
        } catch (Exception e) {
            // Log the exception for further analysis
            logger.error("Error resetting password", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error resetting password: " + e.getMessage()));
        }
    }


    /**
     * Redirects the user to the password reset page.
     *
     * @param token The password reset token.
     * @param response The HTTP servlet response.
     * @return A response entity with a redirect status.
     * @throws IOException If an input or output error occurs.
     */
    @GetMapping("/resetpassword")
    public ResponseEntity<Void> redirectToPasswordResetPage(@RequestParam("token") String token,HttpServletResponse response) throws IOException {
        // Redirect to a new password reset HTML page (e.g., password-reset.html)
        response.sendRedirect("/ResetPassword/ChangePass.html?token=" + token);
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }


}
