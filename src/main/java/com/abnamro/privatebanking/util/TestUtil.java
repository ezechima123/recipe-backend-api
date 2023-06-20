/*
 * Copyright 2023-2023 the original author 
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.util;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.abnamro.privatebanking.model.domain.Recipe;
import com.abnamro.privatebanking.model.domain.UserProfile;
import com.abnamro.privatebanking.model.dtos.LoginRequest;
import com.abnamro.privatebanking.model.dtos.RecipeDto;
import com.abnamro.privatebanking.model.dtos.RecipeRequest;
import com.abnamro.privatebanking.model.enums.RecipeStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is an utility class for generating objects needed for Unit and
 * Integration testing cases ONLY
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Component
@Configuration
@Data
@NoArgsConstructor
public class TestUtil {

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserProfile generateUserProfile(String email) {
        return UserProfile.builder().email(email).fullName("Chima Ezemamama1")
                .passWordHash(bCryptPasswordEncoder.encode("pwd123#$"))
                .build();
    }

    public String objectToJson(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object jsonToObject(final String json) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, Object.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Recipe generateRecipe(String referenceId, UserProfile user) {

        return Recipe.builder()
                .referenceId(referenceId)
                .title("Egusi Soup")
                .vegetarian(true)
                .servings(2)
                .ingredients(Arrays.asList("2 Cubes of Magi", "Pepper and Meat", "Vegetables Leaves"))
                .instructions(Arrays.asList("Wash and Cut meat", "Add ingredients", "Cook till tender"))
                .lastMaintainedBy(user)
                .lastMaintainedDate(LocalDateTime.now())
                .status(RecipeStatus.ACTIVE)
                .comment("Recipe Created").build();
    }

    public RecipeRequest createRecipeRequest(String referenceId, String email) {
        return RecipeRequest.builder()
                .referenceId(referenceId)
                .title("Egusi Soup")
                .vegetarian(Boolean.TRUE)
                .servings(2)
                .ingredients(Arrays.asList("2 Cubes of Magi", "Pepper and Meat", "Vegetables Leaves"))
                .instructions(Arrays.asList("Wash and Cut meat", "Add ingredients", "Cook till tender"))
                .maintainedBy(email)
                .comment("Recipe Created").build();
    }

    public LoginRequest generateLoginRequest(String email, String password) {

        return LoginRequest.builder()
                .email(email)
                .password(password)
                .build();
    }

    public RecipeDto createRecipeDto(String referenceId, String email) {

        return RecipeDto.builder()
                // .id(referenceId)
                .referenceId(referenceId)
                .title("Egusi Soup")
                .vegetarian(true)
                .servings(2)
                .ingredients(Arrays.asList("2 Cubes of Magi", "Pepper and Meat", "Vegetables Leaves"))
                .instructions(Arrays.asList("Wash and Cut meat", "Add ingredients", "Cook till tender"))
                .maintainedBy(email)
                .comment("Recipe Created").build();
    }

    public Recipe generateRecipeWithoutUser(String id, String referenceId) {

        return Recipe.builder()
                .id(id)
                .referenceId(referenceId)
                .title("Egusi Soup")
                .vegetarian(true)
                .servings(2)
                .ingredients(Arrays.asList("2 Cubes of Magi", "Pepper and Meat", "Vegetables Leaves"))
                .instructions(Arrays.asList("Wash and Cut meat", "Add ingredients", "Cook till tender"))
                .lastMaintainedDate(LocalDateTime.now())
                .status(RecipeStatus.ACTIVE)
                .comment("Recipe Created").build();
    }

}