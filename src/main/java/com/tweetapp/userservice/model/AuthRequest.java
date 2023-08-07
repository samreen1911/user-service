package com.tweetapp.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * authentication request to get user name and password
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
	/**
	 * username
	 * 
	 */
	private String username;
	/**
	 * 
	 * password
	 * 
	 */
	private String password;

}