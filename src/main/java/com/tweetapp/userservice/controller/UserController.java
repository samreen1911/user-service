package com.tweetapp.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.tweetapp.userservice.dto.UserDTO;
import com.tweetapp.userservice.exception.LoginFailedException;
import com.tweetapp.userservice.exception.NotFoundException;
import com.tweetapp.userservice.exception.UserAlreadyRegisteredException;
import com.tweetapp.userservice.model.AuthorizationResponse;
import com.tweetapp.userservice.model.UserCredentials;
import com.tweetapp.userservice.model.UserToken;
import com.tweetapp.userservice.model.Users;
import com.tweetapp.userservice.service.JwtUtil;
import com.tweetapp.userservice.service.UserService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * This is authentication controller class
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/v1.0/tweets")
@CrossOrigin(origins = "*")
@Api
public class UserController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserService userService;

	@GetMapping(value = "/health")
	public ResponseEntity<String> healthCheckMethod() {
		log.info("Health check method");
		return ResponseEntity.ok("User Service health check successful...");
	}

	/**
	 * @param loginRequest This parameter is Object of LoginRequest Class and
	 *                     Contains UserName and Password. This Rest Point is used
	 *                     for UserCredential Signing and generate Jwt token.
	 */
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody UserCredentials userCredentials) {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getUsername(),
					userCredentials.getPassword()));
		} catch (Exception e) {
			return new ResponseEntity<>("Invalid username/password", HttpStatus.UNAUTHORIZED);
		}
		final UserDetails userDetails = userService.loadUserByUsername(userCredentials.getUsername() + "");

		final String jwt = jwtUtil.generateToken(userDetails);
		log.info("Token generated for ", userCredentials.getUsername());
		log.info("Token:" + jwt);
		return new ResponseEntity<>(new UserToken(userCredentials.getUsername(), jwt), HttpStatus.OK);

	}

	/**
	 * @param token -> This is Jwt token.
	 * @return
	 * 
	 *         This Rest Point is used for Validate the user, using Jwt token.
	 */
	@GetMapping(value = "/auth/validate")
	public ResponseEntity<AuthorizationResponse> getValidation(@RequestHeader("Authorization") String token) {
		final String token1 = token.substring(7);
		AuthorizationResponse auth = new AuthorizationResponse();

		log.info("Token validation for " + jwtUtil.extractUsername(token1));
		if (Boolean.TRUE.equals(jwtUtil.validateToken(token1))) {
			log.info("Token validated");
			auth.setUsername(jwtUtil.extractUsername(token1));
			auth.setValid(true);
			log.info("True block: " + auth.getUsername() + " " + auth.isValid());
			return new ResponseEntity<>(auth, HttpStatus.OK);

		} else {
			auth.setUsername(jwtUtil.extractUsername(token1));
			auth.setValid(false);
			log.info("False block: " + auth.getUsername() + " " + auth.isValid());
			return new ResponseEntity<>(auth, HttpStatus.FORBIDDEN);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserDTO user) throws UserAlreadyRegisteredException {
		try {
			userService.register(user);
			return new ResponseEntity<>(new Gson().toJson(user), HttpStatus.CREATED);
		} catch (UserAlreadyRegisteredException e) {
			return new ResponseEntity<>("User Already Registered!!!", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/user/{username}")
	public ResponseEntity<Users> findUser(@PathVariable String username,
			@RequestHeader(name = "Authorization") String token) throws Exception {

		AuthorizationResponse authorizationResponse = getValidation(token).getBody();
		if (authorizationResponse != null && authorizationResponse.isValid()) {
			log.info("Successfully Logged in");
			log.info("fetching user details!");
			try {
				Users user = userService.findUser(username);
				return new ResponseEntity<>(user, HttpStatus.OK);
			} catch (Exception e) {
				throw new NotFoundException();
			}
		} else

		{
			log.info("Method findUser failed!");
			throw new LoginFailedException();
		}
	}

	@GetMapping("/user/all")
	public ResponseEntity<List<Users>> getAllUser(@RequestHeader(name = "Authorization") String token)
			throws NotFoundException, LoginFailedException {
		AuthorizationResponse authorizationResponse = getValidation(token).getBody();
		if (authorizationResponse != null && authorizationResponse.isValid()) {
			log.info("Successfully Logged in");
			log.info("Fetching user details!");
			try {
				List<Users> users = userService.findAllUser();
				return new ResponseEntity<>(users, HttpStatus.OK);
			} catch (Exception e) {
				throw new NotFoundException();
			}

		} else {
			log.info("Method findUser failed!");
			throw new LoginFailedException();
		}
	}
}
