package com.tweetapp.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Authorization response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationResponse {
	/**
	 * 
	 * username
	 */
	private String username;
	

	/**
	 * check is valid or not
	 * 
	 */
	private boolean isValid;


}