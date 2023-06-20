/*
 * Copyright 2023-2023 the original author 
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

/**
 * The configuration swagger class for the API documentation
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Configuration
public class OpenAPIConfig {

    @Value("${openapi.doc.server.url}")
    private String serverDocUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("chimaemmanuel.ezeamama@gmail.com");
        contact.setName("Chima Ezeamama");
        contact.setUrl("https://www.abnamro.com");

        Server server = new Server();
        server.setUrl(serverDocUrl);
        server.setDescription("Server URL");

        License mitLicense = new License().name("ABN AMRO License").url("https://abnamro.com/licenses/mit/");

        Info info = new Info()
                .title("Recipe Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage Recipes.")
                .termsOfService("https://www.abnamro.com/en/careers")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(Arrays.asList(server));
    }

}