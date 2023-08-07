package com.tweetapp.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Authentication Microservice's main spring boot application class
 *
 */
@SpringBootApplication
@EnableMongoRepositories
public class UserServiceApplication {
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}
