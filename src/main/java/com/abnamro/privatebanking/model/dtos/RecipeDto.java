/*
 * Copyright 2023-2023 the original author 
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.model.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Dto class that maps directly to the Recipe Document
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    private String id;
    private String referenceId;
    private String title;
    private boolean vegetarian;
    private int servings;
    private List<String> ingredients;
    private List<String> instructions;
    private LocalDateTime maintainedDate;
    private String maintainedBy;
    private String status;
    private String comment;

}