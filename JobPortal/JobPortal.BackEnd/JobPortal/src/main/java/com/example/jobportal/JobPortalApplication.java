package com.example.jobportal;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


/**
 * The main entry point for the Job Portal application.
 * This class configures and launches the Spring Boot application.
 */
@SpringBootApplication
@EnableWebSecurity

public class JobPortalApplication {
	/**
	 * The main method that starts the Spring Boot application.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(JobPortalApplication.class, args);
	}
	/**
	 * Creates a ModelMapper bean for object mapping.
	 * @return A new ModelMapper instance.
	 */
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}

// test1
