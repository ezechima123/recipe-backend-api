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
 * A Dto class that encapsulate pagable Search Response
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private int status;
    private String message;
    private long totalCount;
    private int page;
    private int size;
    private String responseCode;
    private List<RecipeDto> recipes;
}