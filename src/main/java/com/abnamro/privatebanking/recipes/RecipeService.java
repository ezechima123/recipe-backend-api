package com.abnamro.privatebanking.recipes;

public interface RecipeService {

    RecipeResponseDto addRecipe(NewRecipeDto newRecipeDto);

    RecipeResponseDto updateRecipe(String id, RecipeResponseDto recipeDto);

    void deleteRecipe(String recipeId, String comment);

    RecipeResponseDto getRecipeById(String recipeId);

    SearchResponseDto getRecipeByCriteria(SearchRequestDto searchRequestDto);
}