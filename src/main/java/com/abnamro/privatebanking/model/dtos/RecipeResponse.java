/*
 * Copyright 2023-2023 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.model.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * A Dto class that encapsulate Responses from the request messages
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder(toBuilder = true)
@JsonPropertyOrder({ "timestamp", "status", "responseCode", "message", "data"
})
public class RecipeResponse extends BaseResponse {
    private RecipeDto data;

}