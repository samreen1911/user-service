package com.tweetapp.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.userservice.model.Users;

/**
 * 
 * user mongo repository
 * 
 */
@Repository
public interface UserRepository extends MongoRepository<Users, String> {

}