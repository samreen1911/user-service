package com.tweetapp.userservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
	@Id
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String contactNumber;
	private String avatar;

}
