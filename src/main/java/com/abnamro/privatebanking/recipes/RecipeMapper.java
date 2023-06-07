package com.abnamro.privatebanking.recipes;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
    RecipeModel newRecipeDtoToRecipeModel(NewRecipeDto newRecipeDto);

    RecipeResponseDto recipeModelToRecipeDTO(RecipeModel recipeModel);

    List<RecipeResponseDto> recipeModelListToRecipeDtoList(List<RecipeModel> recipeModelList);
}