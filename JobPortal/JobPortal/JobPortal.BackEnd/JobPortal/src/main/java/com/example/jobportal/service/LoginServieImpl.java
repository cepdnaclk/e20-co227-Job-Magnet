package com.example.jobportal.service;

import com.example.jobportal.Response.LoginResponse;
import com.example.jobportal.Response.UpdateProfileResponse;
import com.example.jobportal.dto.EmpUpdateProfileDTO;
import com.example.jobportal.dto.LoginDTO;
import com.example.jobportal.dto.UpdateJobSeekerProfileDTO;
import com.example.jobportal.entity.Admin;
import com.example.jobportal.entity.PasswordResetToken;
import com.example.jobportal.entity.RegJobEmployers;
import com.example.jobportal.entity.RegJobSeeker;
import com.example.jobportal.repository.AdminRepo;
import com.example.jobportal.repository.PasswordResetTokenRepository;
import com.example.jobportal.repository.RegJobEmployerRepo;
import com.example.jobportal.repository.RegJobSeekerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoginServieImpl implements LoginService {

    @Autowired
    private JavaMailSender emailSender;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RegJobSeekerRepo regJobSeekerRepo;
    private final RegJobEmployerRepo regJobEmployerRepo;
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;


    public LoginServieImpl(RegJobSeekerRepo regJobSeekerRepo, RegJobEmployerRepo regJobEmployerRepo, BCryptPasswordEncoder passwordEncoder) {
        this.regJobSeekerRepo = regJobSeekerRepo;
        this.regJobEmployerRepo = regJobEmployerRepo;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepository= passwordResetTokenRepository;
    }

    @Override
    public LoginResponse login(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();

        Optional<RegJobSeeker> jobSeeker = regJobSeekerRepo.findByEmail(email);

        if (jobSeeker.isPresent()) {
            return loginSeeker(loginDTO);
        }
        Optional<RegJobEmployers> jobEmployers = regJobEmployerRepo.findByEmail(email);
        if (jobEmployers.isPresent()) {
            return loginEmployer(loginDTO);
        }else{
            return loginAdmin(loginDTO);
        }

    }
    /*
        @Override
        public boolean isEmailExists(String email) {
            boolean value1=regJobEmployerRepo.existsByEmail(email);
            boolean value2=regJobSeekerRepo.existsByEmail(email);
            boolean value =value1 | value2;
            return value;
        }
    */
    @Override
    public boolean sendResetLink(String email) {
        Optional<RegJobSeeker> userOptional1 = regJobSeekerRepo.findByEmail(email);
        Optional<RegJobEmployers> userOptional2 = regJobEmployerRepo.findByEmail(email);
        Admin userOptional3=adminRepo.findByEmail(email);
        System.out.println("both checking");

        String token = UUID.randomUUID().toString();  // Generate a unique token
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(15);  // Token valid for 1 hour

        if (userOptional1.isPresent()) {
            RegJobSeeker user1 = userOptional1.get();
            System.out.println("token is "+token);
            System.out.println("found a seeker");
            PasswordResetToken resetToken = new PasswordResetToken(email,token,expiryTime);  // Create an instance of your PasswordResetToken entity
            System.out.println("Saving Token: " + resetToken); // Log the token state
            try {
                passwordResetTokenRepository.save(resetToken);
                System.out.println("Token saved successfully");
                // return true;
            } catch (Exception e) {
                System.out.println("Error saving token: " + e.getMessage());
                e.printStackTrace();
                // return false;
            }

            // Create the reset link using the user's email
            String resetLink = "http://localhost:8080/api/resetpassword?token=" + token;

            // Create the email message
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("To reset your password, click the link below:\n" + resetLink);
            System.out.println("mail is ready to send");
            // Send the email
            emailSender.send(message);
            System.out.println("mail sent");
        } else if (userOptional2.isPresent()) {
            RegJobEmployers user2 = userOptional2.get();
            System.out.println("token is "+token);
            System.out.println("found a employer");
            PasswordResetToken resetToken = new PasswordResetToken( email,token,expiryTime);  // Create an instance of your PasswordResetToken entity

            try {
                passwordResetTokenRepository.save(resetToken);
                System.out.println("Token saved successfully");
                //return true;
            } catch (Exception e) {
                System.out.println("Error saving token: " + e.getMessage());
                e.printStackTrace();
                //return false;
            }

            // Create the reset link using the user's email
            String resetLink = "http://localhost:8080/api/resetpassword?token=" + token;

            // Create the email message
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("To reset your password, click the link below:\n" + resetLink);

            // Send the email
            emailSender.send(message);

        } else if (userOptional3 != null) {
            Admin user2 = userOptional3;
            System.out.println("token is " + token);
            System.out.println("found a employer");
            PasswordResetToken resetToken = new PasswordResetToken(email, token, expiryTime);  // Create an instance of your PasswordResetToken entity

            try {
                passwordResetTokenRepository.save(resetToken);
                System.out.println("Token saved successfully");
                //return true;
            } catch (Exception e) {
                System.out.println("Error saving token: " + e.getMessage());
                e.printStackTrace();
                // return false;
            }

            // Create the reset link using the user's email
            String resetLink = "http://localhost:8080/api/resetpassword?token=" + token;

            // Create the email message
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("To reset your password, click the link below:\n" + resetLink);

            // Send the email
            emailSender.send(message);
        }else {
            // Handle the case where the user is not found (optional)
            // You might want to throw an exception or log a warning
            System.out.println("User with email " + email + " not found.");
            return false;
        }
        return true;
    }
    @Override
    public ResponseEntity<String> validateResetToken(String token) {
        //System.out.println("validation method starting");
        // Find the token in the database
        Optional<PasswordResetToken> resetTokenOptional = passwordResetTokenRepository.findByToken(token);

        if (!resetTokenOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Link");
        }

        PasswordResetToken resetToken = resetTokenOptional.get();

        // Check if the token has already been used
        if (resetToken.getUsed()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Link already used");
        }

        // Check if the token is expired
        if (resetToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Link expired");
        }



        return ResponseEntity.ok("Link is valid, proceed with password reset");
    }

    @Override
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
    @Override
    public LoginResponse loginAdmin(LoginDTO loginDTO) {

        // Retrieve the provided email and password
        String providedEmail = loginDTO.getEmail();
        String providedPassword = loginDTO.getPassword();

        Admin admin = adminRepo.findByEmail(providedEmail);

        // Check if the email and password match the hardcoded admin credentials
        if (admin != null) {
            // Verify the password using password encoder or plain-text (if not encoded)
            if (passwordEncoder.matches(providedPassword, admin.getPassword())) {
                // Login successful
                return new LoginResponse("Login Success!", true, providedEmail, "admin");
            } else {
                // Password does not match
                return new LoginResponse("Password Not Match", false, null, null);
            }
        } else {
            // Email does not exist in the Admin table
            return new LoginResponse("Email not Exists!", false, null, null);
        }
    }


    private LoginResponse loginSeeker(LoginDTO loginDTO) {
        //String msg = "";
        Optional<RegJobSeeker> seeker1 = regJobSeekerRepo.findByEmail(loginDTO.getEmail());
        if (seeker1.isPresent()) {
            RegJobSeeker seeker =seeker1.get();
            String password = loginDTO.getPassword();
            String encodedPassword = seeker.getPassword();
            boolean isPwdMatch = passwordEncoder.matches(password, encodedPassword);
            if (isPwdMatch) {
                return new LoginResponse("Login Success!", true,seeker.getEmail(),"seeker");
            } else {
                return new LoginResponse("Password Not Match", false,null,null);
            }
        } else {
            return new LoginResponse("Email not Exists!", false,null,null);
        }
    }


    private LoginResponse loginEmployer(LoginDTO loginDTO) {
        // String msg = "";
        Optional<RegJobEmployers> employerOpt = regJobEmployerRepo.findByEmail(loginDTO.getEmail());
        if (employerOpt.isPresent()) {
            RegJobEmployers employer=employerOpt.get();
            String password = loginDTO.getPassword();
            String encodedPassword = employer.getPassword();
            boolean isPwdMatch = passwordEncoder.matches(password, encodedPassword);
            if (isPwdMatch) {
                //Optional<RegJobEmployers> employerOpt = regJobSeekerRepo.findOneByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
                return new LoginResponse("Login Success!", true,employer.getEmail(),"employer");


            } else {
                return new LoginResponse("Password Not Match", false,null,null);
            }

        } else {
            return new LoginResponse("Email not Exists!", false,null,null);
        }
    }

    @Override
    public UpdateProfileResponse updateProfile(UpdateJobSeekerProfileDTO updateJobSeekerProfileDTO) {
        Optional<RegJobSeeker> seekerOpt = regJobSeekerRepo.findByEmail(updateJobSeekerProfileDTO.getEmail());

        if (seekerOpt.isPresent()){
            RegJobSeeker regJobSeeker=seekerOpt.get();
            regJobSeeker.setFname(updateJobSeekerProfileDTO.getFname());
            regJobSeeker.setLname(updateJobSeekerProfileDTO.getLname());
            regJobSeeker.setDob(updateJobSeekerProfileDTO.getDob());
            regJobSeeker.setBio(updateJobSeekerProfileDTO.getBio());
            regJobSeeker.setExperience(updateJobSeekerProfileDTO.getExperience());
            regJobSeeker.setSkills(updateJobSeekerProfileDTO.getSkills());
            regJobSeeker.setPhone(updateJobSeekerProfileDTO.getPhone());
            regJobSeeker.setCountry(updateJobSeekerProfileDTO.getCountry());
            regJobSeeker.setUniversity(updateJobSeekerProfileDTO.getUniversity());
            regJobSeeker.setDegree(updateJobSeekerProfileDTO.getDegree());
            regJobSeeker.setDclass(updateJobSeekerProfileDTO.getDclass());
            regJobSeeker.setProjects(updateJobSeekerProfileDTO.getProjects());
            regJobSeeker.setAbout(updateJobSeekerProfileDTO.getAbout());




            if (updateJobSeekerProfileDTO.getPassword() != null && !updateJobSeekerProfileDTO.getPassword().isEmpty()) {
                regJobSeeker.setPassword(passwordEncoder.encode(updateJobSeekerProfileDTO.getPassword()));
            }


            regJobSeekerRepo.save(regJobSeeker);
            return new UpdateProfileResponse("Profile updated successfully!", true);

        }else {
            return new UpdateProfileResponse("Email not found!",false);
        }
    }

    public void save(RegJobSeeker regjobSeeker) {
        try {
            if (regjobSeeker == null) {
                throw new IllegalArgumentException("JobSeeker object cannot be null");
            }
            regJobSeekerRepo.save(regjobSeeker);
            System.out.println("JobSeeker saved successfully: " + regjobSeeker);
        } catch (Exception e) {
            System.err.println("Error saving JobSeeker: " + e.getMessage());
            e.printStackTrace();
            // Optionally, rethrow or handle the exception as needed
        }
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        // Validate the token using the existing method
        ResponseEntity<String> validationResponse = validateResetToken(token);

        // Check if the token is valid
        if (!validationResponse.getStatusCode().equals(HttpStatus.OK)) {
            // If the token is not valid, print the response body and return false
            System.out.println(validationResponse.getBody());
            return false; // Token is invalid or has issues
        }

        // Token is valid, proceed to reset the password
        // Retrieve the valid token again from the database
        Optional<PasswordResetToken> resetTokenOptional = passwordResetTokenRepository.findByToken(token);
        PasswordResetToken resetToken = resetTokenOptional.get(); // Safe to get since we just validated it

        // Use the email from the token to find the user
        String email = resetToken.getEmail();

        // Check in RegJobSeeker
        Optional<RegJobSeeker> seekerOptional = regJobSeekerRepo.findByEmail(email);
        if (seekerOptional.isPresent()) {
            RegJobSeeker seeker = seekerOptional.get();
            seeker.setPassword(passwordEncoder.encode(newPassword)); // Encode the new password
            regJobSeekerRepo.save(seeker); // Save the updated seeker
            // Mark the token as used and save
            resetToken.setUsed(true);
            passwordResetTokenRepository.save(resetToken);
            return true; // Password reset successful
        }

        // Check in RegJobEmployers
        Optional<RegJobEmployers> employerOptional = regJobEmployerRepo.findByEmail(email);
        if (employerOptional.isPresent()) {
            RegJobEmployers employer = employerOptional.get();
            employer.setPassword(passwordEncoder.encode(newPassword)); // Encode the new password
            regJobEmployerRepo.save(employer); // Save the updated employer
            // Mark the token as used and save
            resetToken.setUsed(true);
            passwordResetTokenRepository.save(resetToken);
            return true; // Password reset successful
        }

        Admin admin =adminRepo.findByEmail(email);
        if (admin!=null) {

            admin.setPassword(passwordEncoder.encode(newPassword)); // Encode the new password
            adminRepo.save(admin); // Save the updated employer
            // Mark the token as used and save
            resetToken.setUsed(true);
            passwordResetTokenRepository.save(resetToken);
            return true; // Password reset successful
        }

        // If the email is not found in either table
        System.out.println("User with email " + email + " not found.");
        return false; // Password reset failed
    }


    @Override
    public UpdateProfileResponse empUpdateProfile(EmpUpdateProfileDTO empUpdateProdileDTO) {
        Optional<RegJobEmployers> employerOpt = regJobEmployerRepo.findByEmail(empUpdateProdileDTO.getEmail());

        if (employerOpt.isPresent()){
            RegJobEmployers regJobEmployers=employerOpt.get();
            regJobEmployers.setCompanyName(empUpdateProdileDTO.getCompanyName());
            regJobEmployers.setAddress(empUpdateProdileDTO.getAddress());
            regJobEmployers.setPassword(empUpdateProdileDTO.getPassword());
            regJobEmployers.setCoreValues(empUpdateProdileDTO.getCoreValues());
            regJobEmployers.setYears(empUpdateProdileDTO.getYears());
            regJobEmployers.setNo_of_Employees(empUpdateProdileDTO.getNo_of_Employees());
            regJobEmployers.setProjectsCompleted(empUpdateProdileDTO.getProjectsCompleted());
            regJobEmployers.setAbout(empUpdateProdileDTO.getAbout());


            if (empUpdateProdileDTO.getPassword() != null && !empUpdateProdileDTO.getPassword().isEmpty()) {
                regJobEmployers.setPassword(passwordEncoder.encode(empUpdateProdileDTO.getPassword()));
            }

            regJobEmployerRepo.save(regJobEmployers);
            return new UpdateProfileResponse("Profile updated successfully!", true);

        }else {
            return new UpdateProfileResponse("Email not found!",false);
        }
    }

}