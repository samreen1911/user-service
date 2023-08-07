package com.tweetapp.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User Already Registered!!!")

public class UserAlreadyRegisteredException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyRegisteredException(String message) {
		super(message);
	}
}
