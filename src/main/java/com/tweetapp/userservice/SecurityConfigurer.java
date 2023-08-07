package com.tweetapp.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tweetapp.userservice.service.JwtRequestFilter;
import com.tweetapp.userservice.service.UserService;

/**
 * This is Security configure class
 *
 */
@SuppressWarnings("deprecation")
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	/**
	 * This is User Details Service class
	 * 
	 */
	@Autowired
	private UserService userService;
	/**
	 * This is object of Jwt Filter class.
	 * 
	 */
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	/**
	 * This is configure user details
	 * 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}

	/**
	 * This configure Override method is used for configure the spring default
	 * security.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
				.antMatchers("/").permitAll().antMatchers("/api/v1.0/tweets/health").permitAll()
				.antMatchers("/api/v1.0/tweets/login").permitAll().antMatchers("/api/v1.0/tweets/register").permitAll()
				.antMatchers("/swagger-ui.html").permitAll().antMatchers("**swagger**").permitAll().anyRequest()
				.authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}

	/**
	 * This configure Override method is used for configure the spring default web
	 * security.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/v1.0/tweets/health/**").antMatchers("/api/v1.0/tweets/register**")
				.antMatchers("/api/v1.0/tweets/login**").antMatchers("swagger-ui.html**")
				.antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**");
	}

	/*
	 * main class
	 * 
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * This method is used for creating object of PasswordEncoder, that help in
	 * encoding the encrypted password.
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {

		return NoOpPasswordEncoder.getInstance();

	}

}
