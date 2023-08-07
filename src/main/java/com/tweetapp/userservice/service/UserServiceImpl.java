package com.tweetapp.userservice.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tweetapp.userservice.dto.UserDTO;
import com.tweetapp.userservice.exception.NotFoundException;
import com.tweetapp.userservice.exception.UserAlreadyRegisteredException;
import com.tweetapp.userservice.model.UserCredentials;
import com.tweetapp.userservice.model.Users;
import com.tweetapp.userservice.repository.UserRepository;

/**
 * This class is used for load User Credential
 *
 */
@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository userRepository;

	String userNotFound = "User Not Found.";

	@Override
	public UserDetails loadUserByUsername(String username) {
		Users uname = userRepository.findById(username).orElseThrow(() -> new NotFoundException(userNotFound));
		if (uname.getUsername().equals(""))
			throw new UsernameNotFoundException("No user with this name found");
		return new UserCredentials(uname);

	}

	@Override
	public void register(UserDTO userDTO) throws UserAlreadyRegisteredException {
		if (userRepository.findById(userDTO.getUsername()).isPresent()) {
			throw new UserAlreadyRegisteredException("User Already Registered!!!");
		} else {
			Users user = new Users();
			user.setAvatar(userDTO.getAvatar());
			user.setContactNumber(userDTO.getContactNumber());
			user.setEmail(userDTO.getEmail());
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setPassword(userDTO.getPassword());
			user.setUsername(userDTO.getUsername());
			userRepository.save(user);
			logger.info("User registered successfully!!!");
		}
	}

	@Override
	public Users findUser(String username) {
		return userRepository.findById(username).orElseThrow(() -> new NotFoundException(userNotFound));
	}

	@Override
	public List<Users> findAllUser() {
		List<Users> users = userRepository.findAll();
		Comparator<Users> compareByFirstName = (Users user1, Users user2) -> user1.getFirstName().toLowerCase()
				.compareTo(user2.getFirstName().toLowerCase());
		Collections.sort(users, compareByFirstName);
		return users;
	}

}