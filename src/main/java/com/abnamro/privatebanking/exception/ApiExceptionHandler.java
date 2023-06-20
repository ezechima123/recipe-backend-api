/*
 * Copyright 2023-2023 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import com.abnamro.privatebanking.constant.ApplicationConstants;
import com.abnamro.privatebanking.model.dtos.BaseResponse;

/**
 * A model class for error details
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseBody
    public BaseResponse handleRecipeNotFoundException(RecipeNotFoundException e) {
        return getApiException(e.getMessage(), HttpStatus.NOT_FOUND.value(), null);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public BaseResponse handleBadRequestException(BadRequestException e) {
        return getApiException(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public BaseResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return getApiException(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return getApiException(e.getMessage(), HttpStatus.BAD_REQUEST.value(), errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public BaseResponse handleConstraintViolationException(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>();
        e.getConstraintViolations().forEach(cv -> errors.add(cv.getMessage()));
        return getApiException(e.getMessage(), HttpStatus.BAD_REQUEST.value(), errors);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public BaseResponse handleDuplicateException(DuplicateKeyException e) {
        return getApiException(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    public BaseResponse handleUsernameNotFoundException(UsernameNotFoundException e) {
        return getApiException(e.getMessage(), HttpStatus.NOT_FOUND.value(), null);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public BaseResponse handleAuthenticationException(AuthenticationException e) {
        return getApiException(e.getMessage(), HttpStatus.UNAUTHORIZED.value(), null);
    }

    @ExceptionHandler(ResourceAccessException.class)
    @ResponseBody
    public BaseResponse handleUserNotAuthenticated(ResourceAccessException e) {
        return getApiException(e.getMessage(), HttpStatus.UNAUTHORIZED.value(), null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse handleException(Exception e) {
        return getApiException(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null);
    }

    private BaseResponse getApiException(String message, int statusCode, List<String> errorList) {
        return BaseResponse.builder().message(message)
                .responseCode(ApplicationConstants.RESPONSE_ERROR_CODE)
                .errors(errorList)
                .status(statusCode).build();
    }

}