package com.tweetapp.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * user token
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private String authToken;

}