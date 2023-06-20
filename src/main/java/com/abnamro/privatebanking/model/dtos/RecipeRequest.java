/*
 * Copyright 2023-2023 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.model.dtos;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Dto class that encapsulate Recipe request parameters
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequest {
    @NotNull(message = "ReferenceID must be specified")
    private String referenceId;
    @NotNull(message = "Title must be specified")
    private String title;
    @NotNull(message = "Vegetarian must be specified")
    @JsonProperty("vegetarian")
    private Boolean vegetarian;
    @NotNull(message = "Number of Servings must be specified")
    @Min(value = 1, message = "Number of Servings must be at least 1")
    private int servings;
    @Email(message = "MaintainedBy should be a valid Email ID", regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
    @NotNull(message = "MaintainedBy must be specified")
    private String maintainedBy;
    @NotEmpty(message = "Ingredients must be specified")
    @Size(min = 3, message = "Recipe must have at Least 3 Ingredients")
    private List<String> ingredients;
    @NotEmpty(message = "Instructions must be specified")
    @Size(min = 3, message = "Recipe must have at Least 3 Instructions")
    private List<String> instructions;
    @NotNull(message = "Comment must be specified")
    private String comment;
}