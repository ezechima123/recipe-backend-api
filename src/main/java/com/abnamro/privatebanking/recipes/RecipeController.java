/*
 * Copyright 2023-2023 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 * 
 */
package com.abnamro.privatebanking.recipes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.abnamro.privatebanking.recipes.RecipeModel.RecipeType;
import com.abnamro.privatebanking.shared.ResponseHandler;

/**
 * <p>
 * This class RecipeController is used for the management of Recipe
 * It implemented the GET,POST,PUT and DELETE verb actions of an API
 * </p>
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    /**
     * <p>
     * CreateRecipe method
     * </p>
     * 
     * @apiNote POST Verb
     * @param NewRecipeDto A DataTransfer Object holding the RecipeModel
     * @return ResponseEntity object with RecipeResponseDto
     * @since 1.0
     */
    @PostMapping("/v1/recipes")
    public ResponseEntity<?> createRecipe(@Valid @RequestBody NewRecipeDto createRecipeDTO) {
        RecipeResponseDto recipeDto = recipeService.addRecipe(createRecipeDTO);
        return ResponseHandler.responseBuilder("Recipe Created Successfully", HttpStatus.CREATED, recipeDto);
    }

    /**
     * <p>
     * GetRecipeById method
     * </p>
     * 
     * @apiNote GET Verb
     * @param a recipeId String parameter that will be used to retrive the Recipe
     * @return ResponseEntity object with RecipeResponseDto
     * @since 1.0
     */
    @GetMapping("/v1/recipes/{recipeId}")
    public ResponseEntity<?> getRecipe(
            @PathVariable("recipeId") @NotNull(message = "Recipe Id must be Specified") String recipeId) {
        RecipeResponseDto recipeDto = recipeService.getRecipeById(recipeId);
        return ResponseHandler.responseBuilder("Recipe retrieved Successfully", HttpStatus.OK, recipeDto);
    }

    /**
     * <p>
     * removeRecipe method
     * </p>
     * 
     * @apiNote DELETE Verb
     * @param a recipeId String parameter that will be used to retrive the Recipe
     * @param a comment String parameter to show the reason for the delete
     * @return ResponseEntity object with RecipeResponseDto
     * @since 1.0
     */
    @DeleteMapping("/v1/recipes/{recipeId}/{comment}")
    public ResponseEntity<?> removeRecipe(
            @PathVariable("recipeId") @NotNull(message = "Recipe Id must be Specified") String recipeId,
            @PathVariable("comment") @NotNull(message = "Comment must be Specified") String comment) {
        recipeService.deleteRecipe(recipeId, comment);
        return ResponseHandler.responseBuilder("Recipe deleted Successfully", HttpStatus.OK, null);
    }

    /**
     * <p>
     * updateRecipe method
     * </p>
     * 
     * @apiNote PUT Verb
     * @param a recipeId String parameter that will be used to retrive the Recipe
     * @param a comment String parameter that will be used to retrive the Recipe
     * @return ResponseEntity object with RecipeResponseDto
     * @since 1.0
     */
    @PutMapping("/v1/recipes/{recipeId}")
    public ResponseEntity<?> updateRecipe(
            @PathVariable("recipeId") @NotNull(message = "Recipe Id must be Specified") String recipeId,
            @Valid @RequestBody RecipeResponseDto recipeDto) {
        RecipeResponseDto recipeDtoUpdated = recipeService.updateRecipe(recipeId, recipeDto);
        return ResponseHandler.responseBuilder("Recipe updated Successfully", HttpStatus.OK, recipeDtoUpdated);
    }

    /**
     * 
     * <p>
     * searchRecipeByCriteria
     * </p>
     * 
     * @apiNote GET Verb
     * @param RecipeType  what type of recipe
     * @param servings    Number of Servings
     * @param ingredient  Number of Ingredient
     * @param instruction Number of Instruction
     * @param pageNumber  number of the page returned
     * @param pageSize    number of entries in each page
     * @return Page object with customers after filtering
     * @since 1.0
     */
    @GetMapping("/v1/recipes")
    public ResponseEntity<?> searchRecipeByCriteria(
            @RequestParam(required = false) RecipeType recipeType,
            @RequestParam(required = false) Integer servings,
            @RequestParam(required = false) String ingredient,
            @RequestParam(required = false) String instruction,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "50") Integer pageSize) {

        SearchResponseDto searchResponseDto = recipeService.getRecipeByCriteria(
                SearchRequestDto.builder().recipeType(recipeType).servings(servings).ingredient(ingredient)
                        .instruction(instruction).pageNumber(pageNumber).pageSize(pageSize).build());
        return ResponseHandler.responseBuilder("Recipe retrieved Successfully", HttpStatus.OK, searchResponseDto);
    }

}