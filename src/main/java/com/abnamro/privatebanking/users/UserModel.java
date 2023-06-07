/*
 * Copyright 2023-2023 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * A model class for users in the system
 * </p>
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
public class UserModel {
    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    private String fullName;
    private String pwd;
}