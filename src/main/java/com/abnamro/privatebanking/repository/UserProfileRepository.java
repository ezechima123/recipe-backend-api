/*
 * Copyright 2023-2024 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.abnamro.privatebanking.model.domain.UserProfile;

/**
 * UserProfileRepository interface for CRUD on UserProfile Document
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {
    @Query("{'email': ?0}")
    Optional<UserProfile> findByEmail(String email);
}