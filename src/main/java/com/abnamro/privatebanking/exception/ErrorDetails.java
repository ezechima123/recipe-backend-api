/*
 * Copyright 2023-2023 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.exception;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A model class for error details
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private int status;
    private Long timestamp;
    private String message;
    private List<String> errors = new ArrayList<>();

    public void addError(String message) {
        this.errors.add(message);
    }
}