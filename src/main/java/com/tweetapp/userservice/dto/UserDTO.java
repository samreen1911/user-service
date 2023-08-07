package com.tweetapp.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String contactNumber;
	private String avatar;

}
