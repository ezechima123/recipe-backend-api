package com.abnamro.privatebanking.recipes;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.abnamro.privatebanking.recipes.RecipeModel.RecipeType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewRecipeDto {
    @NotNull(message = "ReferenceId cannot be empty")
    @Size(min = 10, message = "ReferenceId should be at least 10 characters")
    private String referenceId;
    @NotNull(message = "Title cannot be empty")
    private String title;
    @NotNull(message = "IsVegetarian cannot be empty")
    private RecipeType recipeType;
    @NotNull(message = "Number of Servings cannot be empty")
    @Min(value = 1, message = "Number of Servings must be at least 1")
    private int servings;
    @Email(message = "CreatedBy should be valid email Id", regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
    @NotNull(message = "CreatedBy cannot be empty")
    private String createdBy;
    @NotEmpty(message = "List of Ingredients cannot be empty")
    @Size(min = 10, message = "Recipe must have at Least 3 Ingredients")
    private List<String> listOfIngredients;
    @NotEmpty(message = "List of Instructions cannot be empty")
    @Size(min = 10, message = "Recipe must have at Least 3 Instructions")
    private List<String> listOfInstructions;
    @NotNull(message = "Please comment on the Recipe")
    private String comment;

}