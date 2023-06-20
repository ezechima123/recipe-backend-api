/*
 * Copyright 2023-2024 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.exception;

/**
 * This captures error messages emanating from BadRequest
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = -3332292340000888871L;
    private String message;

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    public BadRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }
}