/*
 * Copyright 2023-2024 the original author or authors.
 *
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */

package com.abnamro.privatebanking.recipes;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Sample repository managing {@link Recipe} entities.
 *
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 * @since 6-6-2023
 */
public interface RecipeRepository extends MongoRepository<RecipeModel, String>, RecipeCustomRepository {
    @Query("{'referenceId': ?0}")
    Optional<RecipeModel> findByReferenceId(String referenceId);
}