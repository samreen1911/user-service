package com.tweetapp.userservice.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.tweetapp.userservice.dto.UserDTO;
import com.tweetapp.userservice.model.Users;

public interface UserService extends UserDetailsService {
	public UserDetails loadUserByUsername(String username);

	public void register(UserDTO user);

	public Users findUser(String username);

	public List<Users> findAllUser();

}
