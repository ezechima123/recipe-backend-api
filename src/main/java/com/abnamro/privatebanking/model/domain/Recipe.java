/*
 * Copyright 2023-2023 the original author 
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.model.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.abnamro.privatebanking.model.enums.RecipeStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class that describes the Recipe Document
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Recipe")
@CompoundIndex(def = "{'referenceId': 1, 'status': 1}", unique = true)
public class Recipe {
    @Id
    private String id;
    @NotNull
    private String referenceId;
    @NotNull
    private String title;
    private boolean vegetarian;
    private int servings;
    @NotEmpty()
    @Size(min = 2)
    private List<String> ingredients;
    @NotEmpty()
    @Size(min = 2)
    private List<String> instructions;
    @DBRef
    private UserProfile lastMaintainedBy;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastMaintainedDate;
    @NotNull
    private RecipeStatus status;
    @NotNull
    private String comment;
}