package com.abnamro.privatebanking.recipes;

import java.time.LocalDateTime;
import java.util.List;

import com.abnamro.privatebanking.recipes.RecipeModel.RecipeType;

import lombok.Data;

@Data
public class RecipeResponseDto {
    private String id;
    private String referenceId;
    private String title;
    private RecipeType recipeType;
    private int servings;
    private List<String> ingredients;
    private List<String> instructions;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime modifiedDate;
    private String modifiedBy;
    private String status;
    private String comment;

}