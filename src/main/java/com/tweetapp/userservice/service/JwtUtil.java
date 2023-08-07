package com.tweetapp.userservice.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * This is JwtUtils class
 *
 */
@Service
public class JwtUtil {
	
	Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	/**
	 * set secret key
	 */
	private static final String secretKey = "hackfse-secret";

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * 
	 * 
	 * 
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * 
	 * 
	 * 
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

	/**
	 * 
	 * check token is expired
	 * 
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * @param userName This method generated the jwt token using userName, secret
	 *                 Key and jwt Expiration time.
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	/**
	 * this method is creating a token
	 * 
	 */
	private String createToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 300))
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	/**
	 * @param authToken This is Jwt token. This method is used validation of user
	 *                  Jwt token.
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);

		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	/**
	 * check the validity of the token
	 */
	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}

}