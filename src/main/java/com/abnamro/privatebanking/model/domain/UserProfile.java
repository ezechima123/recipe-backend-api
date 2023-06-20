/*
 * Copyright 2023-2023 the original author 
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.model.domain;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class that describes the UserProfile Document
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "User")
@CompoundIndex(def = "{'email': 1}", unique = true)
public class UserProfile {
    @Id
    private String id;
    @NotNull
    private String email;
    @NotNull
    private String fullName;
    @NotNull
    private String passWordHash;
}