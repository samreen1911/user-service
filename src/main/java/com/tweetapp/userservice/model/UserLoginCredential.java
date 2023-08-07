package com.tweetapp.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * user login credentials
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginCredential {
	private String username;
	private String password;

}