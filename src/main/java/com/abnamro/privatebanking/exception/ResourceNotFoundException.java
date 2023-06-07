/*
 * Copyright 2023-2023 the original author
 * Licensed under the ABN AMRO, Version 1.0 (the "License");
 */
package com.abnamro.privatebanking.exception;

/**
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}