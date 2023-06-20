/*
 * Copyright 2023-2024 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;

import com.abnamro.privatebanking.model.domain.Recipe;
import com.abnamro.privatebanking.model.dtos.RecipeRequest;
import com.abnamro.privatebanking.model.dtos.SearchRequest;

/**
 * The Service interface
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
public interface RecipeService {

    Recipe createRecipe(RecipeRequest recipeRequest);

    Recipe updateRecipe(String id, RecipeRequest recipeDto);

    Recipe deleteRecipe(String recipeId, String comment);

    Recipe getRecipeById(String id);

    Page<Recipe> searchRecipesByCriteria(SearchRequest searchRequestDto);

    Page<Recipe> queryRecipeByFilters(MultiValueMap<String, String> filters, int page, int size);
}