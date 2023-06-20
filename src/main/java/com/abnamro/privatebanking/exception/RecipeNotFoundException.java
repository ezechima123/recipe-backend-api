/*
 * Copyright 2023-2023 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.exception;

/**
 * This captures error messages emanating from Recipe not found during Query
 * operations
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
public class RecipeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -3332811222234265371L;
    private String message;

    public RecipeNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public RecipeNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

}