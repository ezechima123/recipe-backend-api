/*
 * Copyright 2023-2024 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.abnamro.privatebanking.model.domain.Recipe;
import com.abnamro.privatebanking.model.dtos.RecipeDto;
import com.abnamro.privatebanking.model.dtos.RecipeRequest;

/**
 * This is a mapper class that defines the relationship between the Document and
 * DTO class
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface RecipeMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    Recipe convertDtoToRecipe(RecipeRequest recipeRequest);

    @Mapping(source = "lastMaintainedDate", target = "maintainedDate")
    @Mapping(source = "recipeModel.lastMaintainedBy.email", target = "maintainedBy")
    RecipeDto convertToRecipeDto(Recipe recipeModel);

    List<RecipeDto> recipeModelListToRecipeDtoList(List<Recipe> recipeModelList);
}