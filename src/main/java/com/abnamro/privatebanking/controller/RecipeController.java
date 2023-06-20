/*
 * Copyright 2023-2023 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 * 
 */
package com.abnamro.privatebanking.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abnamro.privatebanking.constant.ApplicationConstants;
import com.abnamro.privatebanking.mapper.RecipeMapper;
import com.abnamro.privatebanking.model.domain.Recipe;
import com.abnamro.privatebanking.model.dtos.RecipeDto;
import com.abnamro.privatebanking.model.dtos.RecipeRequest;
import com.abnamro.privatebanking.model.dtos.RecipeResponse;
import com.abnamro.privatebanking.model.dtos.SearchRequest;
import com.abnamro.privatebanking.model.dtos.SearchResponse;
import com.abnamro.privatebanking.service.RecipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * This class RecipeController is used for the management of Recipe
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name = "Recipe", description = "Recipe management APIs")
public class RecipeController {

        @Autowired
        private RecipeService recipeService;

        /**
         * This operation is used to create a Recipe.
         * 
         * @param recipeRequest A DataTransfer Object holding the Recipe Object
         * @return RecipeApiResponse that encapsulates timestamp,status,Message,Recipe
         */
        @PostMapping("/v1/recipes")
        @Operation(summary = "Create a Recipe", description = "Create a Recipe with RecipeRequest Object. The response is RecipeResponse", tags = {
                        "recipe", "post" })
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Recipe creation OK", content = {
                                        @Content(schema = @Schema(implementation = RecipeResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "404", description = "User Not Found", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "409", description = "Duplicate reference or  Recipe already Exist", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }) })

        public ResponseEntity<RecipeResponse> createRecipe(
                        @Valid @RequestBody RecipeRequest recipeRequest) {
                log.debug("Call createRecipe API");

                RecipeDto recipeDto = RecipeMapper.INSTANCE
                                .convertToRecipeDto(recipeService.createRecipe(recipeRequest));

                RecipeResponse recipeApiResponse = RecipeResponse.builder()
                                .status(HttpStatus.CREATED.value())
                                .message(ApplicationConstants.SUCCESS_MESSAGE)
                                .responseCode(ApplicationConstants.RESPONSE_SUCCESS_CODE)
                                .data(recipeDto)
                                .build();

                return new ResponseEntity<>(recipeApiResponse, HttpStatus.CREATED);

        }

        /**
         * This operation is used to fetch a Recipe.
         * 
         * @param recipeId An Id String that will be used to retrive the Recipe
         * @return RecipeResponse that encapsulates timestamp,status,Message,Recipe
         */
        @GetMapping("/v1/recipes/{recipeId}")
        @Operation(summary = "Fetch a Recipe by ID", description = "Get a RecipeResponse object with Recipe ID.", tags = {
                        "recipe", "get" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Recipe fetch OK", content = {
                                        @Content(schema = @Schema(implementation = RecipeResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "404", description = "Recipe Not Found", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }) })
        public ResponseEntity<RecipeResponse> getRecipe(
                        @PathVariable("recipeId") @NotNull(message = "Recipe ID must be Specified") String recipeId) {
                log.debug("Getting Recipe with ID: {}", recipeId);

                RecipeDto recipeDto = RecipeMapper.INSTANCE.convertToRecipeDto(recipeService.getRecipeById(recipeId));
                RecipeResponse recipeApiResponse = RecipeResponse.builder()
                                .status(HttpStatus.OK.value())
                                .message(ApplicationConstants.SUCCESS_MESSAGE)
                                .responseCode(ApplicationConstants.RESPONSE_SUCCESS_CODE)
                                .data(recipeDto).build();

                return new ResponseEntity<>(recipeApiResponse, HttpStatus.OK);
        }

        /**
         * This operation deletes a Recipe
         * 
         * @param recipeId An Id String of the Recipe to be deleted
         * @param comment  A String showing the comment or note for the delete
         * @return RecipeResponse that encapsulates timestamp,status,Message,Recipe
         */
        @DeleteMapping("/v1/recipes/{recipeId}/{comment}")
        @Operation(summary = "Delete a Recipe", description = "Delete a Recipe using the ID and comment", tags = {
                        "recipe", "delete" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Recipe deleted OK", content = {
                                        @Content(schema = @Schema(implementation = RecipeResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "404", description = "Recipe Not Found", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "500", description = "Error with the System", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }) })
        public ResponseEntity<RecipeResponse> removeRecipe(
                        @PathVariable("recipeId") @NotNull(message = "Recipe ID must be Specified") String recipeId,
                        @PathVariable("comment") @NotNull(message = "Comment must be Specified") String comment) {
                log.debug("Call deleteRecipe API");

                RecipeDto recipeDto = RecipeMapper.INSTANCE
                                .convertToRecipeDto(recipeService.deleteRecipe(recipeId, comment));

                RecipeResponse recipeApiResponse = RecipeResponse.builder()
                                .status(HttpStatus.OK.value())
                                .message(ApplicationConstants.SUCCESS_MESSAGE)
                                .responseCode(ApplicationConstants.RESPONSE_SUCCESS_CODE)
                                .data(recipeDto).build();

                return new ResponseEntity<>(recipeApiResponse, HttpStatus.OK);
        }

        /**
         * This operation Updates a Recipe
         * 
         * @param recipeId      The String parameter that references the Recipe
         * @param recipeRequest an Object that holds other values to be updated
         * @return RecipeResponse that encapsulates timestamp,status,Message,Recipe
         */
        @PutMapping("/v1/recipes/{recipeId}")
        @Operation(summary = "Update a Recipe", description = "Update a Recipe with the Id and RecipeRequest", tags = {
                        "recipe", "put" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Recipe Updates OK", content = {
                                        @Content(schema = @Schema(implementation = RecipeResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "400", description = "Invalid Parameters supplied", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "404", description = "User or Recipe Reference Not Found", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "500", description = "Error with the System", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }) })
        public ResponseEntity<RecipeResponse> updateRecipe(
                        @PathVariable("recipeId") @NotNull(message = "Recipe ID must be Specified") String recipeId,
                        @Valid @RequestBody RecipeRequest recipeRequest) {

                RecipeDto recipeDto = RecipeMapper.INSTANCE
                                .convertToRecipeDto(recipeService.updateRecipe(recipeId, recipeRequest));

                RecipeResponse recipeApiResponse = RecipeResponse.builder()
                                .status(HttpStatus.OK.value())
                                .message(ApplicationConstants.SUCCESS_MESSAGE)
                                .responseCode(ApplicationConstants.RESPONSE_SUCCESS_CODE)
                                .data(recipeDto).build();

                return new ResponseEntity<>(recipeApiResponse, HttpStatus.OK);
        }

        /**
         * This operation is used to Search for Recipe
         * 
         * @param searchType   search type is either All or Normal
         * @param vegetarian   a boolean parameter that shows if Recipe type Vegetarian
         * @param servings     Number of Servings that can the Recipe can provide
         * @param ingredients  search by Ingredient
         * @param instructions search by Instruction
         * @param page         number of the page returned
         * @param size         number of entries in each page
         * @return SearchResponse Page object with customers after filtering
         */
        @Operation(summary = "Search Recipe", description = "Search Recipe based on Criteria and returned SearchResponse", tags = {
                        "recipe", "get" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Search Recipe Succesful", content = {
                                        @Content(schema = @Schema(implementation = SearchResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "400", description = "Invalid Parameters supplied", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "500", description = "Error with the System", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }) })
        @GetMapping("/v1/recipes/search2")
        public ResponseEntity<SearchResponse> searchRecipeByCriteria(
                        @RequestParam(required = false, defaultValue = "All") String searchType,
                        @RequestParam(required = false) boolean vegetarian,
                        @RequestParam(required = false, defaultValue = "-1") Integer servings,
                        @RequestParam(required = false) String ingredients,
                        @RequestParam(required = false) String instructions,
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "20") Integer size) {

                log.debug("Call searchRecipeByCriteria API");

                SearchRequest searchRequest = new SearchRequest();
                searchRequest.setServings(servings);
                searchRequest.setIngredient(ingredients);
                searchRequest.setInstruction(instructions);
                searchRequest.setPage(page);
                searchRequest.setSize(size);
                searchRequest.setVegetarian(vegetarian);
                searchRequest.setSearchType(searchType);

                log.debug("search Criteria Params " + searchRequest.toString());

                Page<Recipe> pageRecipe = recipeService.searchRecipesByCriteria(searchRequest);

                log.debug("Number of Returned RecipeList " + pageRecipe.getContent().size());

                SearchResponse searchResponseDto = SearchResponse.builder()

                                .page(pageRecipe.getNumber())
                                .size(pageRecipe.getSize())
                                .totalCount(pageRecipe.getTotalElements())
                                .status(HttpStatus.OK.value())
                                .message(ApplicationConstants.SUCCESS_MESSAGE)
                                .responseCode(ApplicationConstants.RESPONSE_SUCCESS_CODE)
                                .recipes(RecipeMapper.INSTANCE.recipeModelListToRecipeDtoList(pageRecipe.getContent()))
                                .build();

                return new ResponseEntity<>(searchResponseDto,
                                HttpStatus.OK);
        }

        /**
         * This operation is used to Search for Recipe
         * 
         * @param filters search by filters
         * @param page    number of the page returned
         * @param size    number of entries in each page
         * @return SearchResponse Page object with customers after filtering
         */
        @Operation(summary = "Search Recipe", description = "Search Recipe based on Criteria and returned SearchResponse", tags = {
                        "recipe", "get" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Search Recipe Succesful", content = {
                                        @Content(schema = @Schema(implementation = SearchResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "400", description = "Invalid Parameters supplied", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "500", description = "Error with the System", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }) })
        @GetMapping("/v1/recipes/search")
        public ResponseEntity<SearchResponse> searchRecipeByCriteria(
                        @RequestParam MultiValueMap<String, String> filters,
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "20") Integer size) {

                log.debug("Call searchRecipeByCriteria API");

                Page<Recipe> pageRecipe = recipeService.queryRecipeByFilters(filters, page, size);
                log.debug("Number of Returned RecipeList " + pageRecipe.getContent().size());
                SearchResponse searchResponseDto = SearchResponse.builder()
                                .page(pageRecipe.getNumber())
                                .size(pageRecipe.getSize())
                                .totalCount(pageRecipe.getTotalElements())
                                .status(HttpStatus.OK.value())
                                .message(ApplicationConstants.SUCCESS_MESSAGE)
                                .responseCode(ApplicationConstants.RESPONSE_SUCCESS_CODE)
                                .recipes(RecipeMapper.INSTANCE.recipeModelListToRecipeDtoList(pageRecipe.getContent()))
                                .build();
                return new ResponseEntity<>(searchResponseDto,
                                HttpStatus.OK);
        }

}