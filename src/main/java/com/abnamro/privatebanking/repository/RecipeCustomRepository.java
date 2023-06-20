/*
 * Copyright 2023-2024 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.repository;

import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;

import com.abnamro.privatebanking.model.domain.Recipe;
import com.abnamro.privatebanking.model.dtos.SearchRequest;

/**
 * Interface for filtering Recipes
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
public interface RecipeCustomRepository {

        public Page<Recipe> searchRecipesByCriteria(SearchRequest searchRequestDto);

        public Page<Recipe> queryRecipeByFilters(MultiValueMap<String, String> filters, int page, int size);
}
