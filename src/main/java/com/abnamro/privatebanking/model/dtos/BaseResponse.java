/*
 * Copyright 2023-2023 the original author 
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.model.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * A Base class that encapsulate APi response for the Endpoints
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "timestamp", "status", "responseCode", "message", "errors" })
public class BaseResponse {
    private int status;
    private String message;
    private String responseCode;
    private List<String> errors;

    public void addError(String error) {
        errors.add(error);
    }

}