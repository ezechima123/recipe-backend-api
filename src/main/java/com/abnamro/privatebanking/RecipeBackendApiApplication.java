/*
 * Copyright 2023-2023 the original author 
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

import com.abnamro.privatebanking.config.MongoDbConfig;
import com.abnamro.privatebanking.util.ValidationService;

import lombok.extern.slf4j.Slf4j;

/**
 * This is the startup main class for the Recipe backend Application
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Import(value = { MongoDbConfig.class, ValidationService.class })
@Slf4j
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RecipeBackendApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeBackendApiApplication.class, args);
	}
}
