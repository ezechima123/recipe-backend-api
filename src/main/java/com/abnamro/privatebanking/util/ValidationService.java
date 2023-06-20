/*
 * Copyright 2023-2023 the original author 
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.util;

import java.lang.reflect.Field;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abnamro.privatebanking.model.domain.Recipe;
import com.abnamro.privatebanking.model.dtos.FilterQuery;
import com.abnamro.privatebanking.model.dtos.RecipeRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * The class holding defined values
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Slf4j
@Component
public class ValidationService {

    @Autowired
    private Validator validator;

    private boolean validateQueryParams(String queryField) {
        for (Field field : Recipe.class.getDeclaredFields()) {
            if (field.getName().equals(queryField)) {
                return true;
            }
        }
        return false;
    }

    private Object getFieldValueType(String queryField, String value) {
        for (Field field : Recipe.class.getDeclaredFields()) {
            if (field.getName().equals(queryField)) {
                String fieldType = field.getType().getSimpleName();
                log.debug("FieldType {}", fieldType);
                if (fieldType.matches(".*\\b(boolean|Boolean)\\b.*")) {
                    return Boolean.parseBoolean(value);
                } else if (fieldType.matches(".*\\b(int|Integer|long|Long)\\b.*")) {
                    return Integer.parseInt(value);
                }
                return value;
            }
        }
        return false;
    }

    public FilterQuery processFilterQuery(String filterExpression) throws ArrayIndexOutOfBoundsException {
        // filter=vegetarian:eq:true
        String[] filterExpressionList = filterExpression.split(":");

        return FilterQuery.builder().filterBy(filterExpressionList[0]).operator(filterExpressionList[1])
                // .filterValue(filterExpressionList[2]).build();
                .filterValue(getFieldValueType(filterExpressionList[0], filterExpressionList[2])).build();

    }

    public boolean isNotEmpty(String value) {
        return (value != null && !value.isEmpty());
    }

    public boolean validateRecipeRequest(RecipeRequest recipeRequest) {
        Set<ConstraintViolation<RecipeRequest>> violations = validator.validate(recipeRequest);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            violations.stream().forEach(violation -> sb.append(violation.getMessage()));
            throw new ConstraintViolationException(sb.toString(), violations);
        }
        return true;
    }

}