package com.abnamro.privatebanking.recipes;

import com.abnamro.privatebanking.recipes.RecipeModel.RecipeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestDto {

    private RecipeType recipeType;
    private int servings;
    private String ingredient;
    private String instruction;
    private int pageNumber;
    private int pageSize;

}