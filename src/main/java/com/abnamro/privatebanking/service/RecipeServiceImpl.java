/*
 * Copyright 2023-2024 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.abnamro.privatebanking.constant.ApplicationConstants;
import com.abnamro.privatebanking.exception.RecipeNotFoundException;
import com.abnamro.privatebanking.mapper.RecipeMapper;
import com.abnamro.privatebanking.model.domain.Recipe;
import com.abnamro.privatebanking.model.domain.UserProfile;
import com.abnamro.privatebanking.model.dtos.RecipeRequest;
import com.abnamro.privatebanking.model.dtos.SearchRequest;
import com.abnamro.privatebanking.model.enums.RecipeStatus;
import com.abnamro.privatebanking.repository.RecipeRepository;
import com.abnamro.privatebanking.repository.UserProfileRepository;
import com.abnamro.privatebanking.util.ValidationService;

import lombok.extern.slf4j.Slf4j;

/**
 * RecipeServiceImpl implementation for RecipeService
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Slf4j
@Service
// @EnableCaching
public class RecipeServiceImpl implements RecipeService {

        @Autowired
        private UserProfileRepository userRepository;
        @Autowired
        private RecipeRepository recipeRepository;
        @Autowired
        ValidationService validationService;

        // @Cacheable("recipes")
        @Override
        public Recipe getRecipeById(String id) {
                /// String email = (String)
                /// SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                log.debug("About to get the Recipe {} Document from the DB", id);
                Recipe recipe = recipeRepository.findById(id)
                                .orElseThrow(() -> new RecipeNotFoundException(
                                                String.format(ApplicationConstants.RECIPE_NOT_FOUND, id)));

                log.debug("Recipe current Status", recipe.getStatus());
                if (recipe.getStatus() == RecipeStatus.DELETED) {
                        throw new RecipeNotFoundException(
                                        String.format(ApplicationConstants.RECIPE_NOT_FOUND, id));
                }
                log.debug("Chima");
                return recipe;
        }

        @Override
        public Recipe createRecipe(RecipeRequest recipeRequest) {
                log.debug("About to Create Recipe: {}", recipeRequest.toString());
                LocalDateTime localDateTime = LocalDateTime.now();

                Optional<Recipe> recipeModelOptional = recipeRepository
                                .findByReferenceId(recipeRequest.getReferenceId());

                if (recipeModelOptional.isPresent()) {
                        throw new DuplicateKeyException(String.format(ApplicationConstants.RECIPE_EXIST,
                                        recipeRequest.getReferenceId()));
                }
                UserProfile initiatedBy = userRepository.findByEmail(recipeRequest.getMaintainedBy())
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                String.format(ApplicationConstants.USER_NOT_FOUND,
                                                                recipeRequest.getMaintainedBy())));

                log.debug("About to convert  to Recipe Model");
                Recipe recipe = RecipeMapper.INSTANCE.convertDtoToRecipe(recipeRequest);
                recipe.setLastMaintainedBy(initiatedBy);
                recipe.setLastMaintainedDate(localDateTime);
                recipe.setStatus(RecipeStatus.ACTIVE);

                Recipe recipeFromDb = recipeRepository.save(recipe);
                log.debug("Recipe details successfully saved in the database");
                return recipeFromDb;
        }

        @Override
        public Recipe updateRecipe(String id, RecipeRequest recipeDTO) {

                Recipe recipe = getRecipeById(id);
                UserProfile updatedBy = userRepository.findByEmail(recipeDTO.getMaintainedBy())
                                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                                                ApplicationConstants.USER_NOT_FOUND, recipeDTO.getMaintainedBy())));

                recipe.setReferenceId(recipeDTO.getReferenceId());
                recipe.setTitle(recipeDTO.getTitle());
                recipe.setVegetarian(recipeDTO.getVegetarian());
                recipe.setIngredients(recipeDTO.getIngredients());
                recipe.setInstructions(recipeDTO.getInstructions());
                recipe.setLastMaintainedBy(updatedBy);
                recipe.setLastMaintainedDate(LocalDateTime.now());
                recipe.setComment(recipeDTO.getComment());

                Recipe recipeFromDb = recipeRepository.save(recipe);
                log.debug("Recipe details successfully updated in the database :{}", recipeFromDb.getId());
                return recipeFromDb;
        }

        @Override
        public Recipe deleteRecipe(String id, String comment) {
                log.debug("About to Delete Recipe with {} and {}", id, comment);
                Recipe recipe = recipeRepository.findById(id)
                                .orElseThrow(() -> new RecipeNotFoundException(
                                                String.format(ApplicationConstants.RECIPE_NOT_FOUND, id)));

                if (recipe.getStatus() == RecipeStatus.DELETED) {
                        throw new RecipeNotFoundException(String.format(ApplicationConstants.RECIPE_NOT_FOUND, id));
                }
                log.debug("About to update status and comment on Recipe");
                recipe.setStatus(RecipeStatus.DELETED);
                recipe.setComment(comment);

                Recipe recipeFromDb = recipeRepository.save(recipe);
                log.debug("Recipe details updated with delete status in the database :{}", recipeFromDb.getId());
                return recipeFromDb;
        }

        // @Cacheable("recipe")
        @Override
        public Page<Recipe> searchRecipesByCriteria(SearchRequest searchRequestDto) {
                return recipeRepository.searchRecipesByCriteria(searchRequestDto);
        }

        @Override
        public Page<Recipe> queryRecipeByFilters(MultiValueMap<String, String> filters, int page, int size) {
                // TODO Auto-generated method stub
                return recipeRepository.queryRecipeByFilters(filters, page, size);
        }

}