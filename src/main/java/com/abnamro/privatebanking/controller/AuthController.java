/*
 * Copyright 2023-2024 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abnamro.privatebanking.constant.ApplicationConstants;
import com.abnamro.privatebanking.model.domain.UserProfile;
import com.abnamro.privatebanking.model.dtos.BaseResponse;
import com.abnamro.privatebanking.model.dtos.LoginRequest;
import com.abnamro.privatebanking.repository.UserProfileRepository;
import com.abnamro.privatebanking.security.JWTUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * The class that will handle the Login authentication request
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Login", description = "Recipe management Authentication")
public class AuthController {

        @Autowired
        private UserProfileRepository userRepo;
        @Autowired
        private JWTUtil jwtUtil;
        @Autowired
        private AuthenticationManager authManager;

        /**
         * This operation is used to fetch UserProfile Details.
         * 
         * @return UserProfile based on the email availble on the session
         */
        @GetMapping("/user/info")
        @Operation(summary = "Fetch UserProfile", description = "Fetch UserProfile from the System", tags = {
                        "Login", "get" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "User Fetch OK", content = {
                                        @Content(schema = @Schema(implementation = UserProfile.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "404", description = "User Not Found", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }) })
        public UserProfile getUserDetails() {
                String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                                String.format(ApplicationConstants.USER_NOT_FOUND, email)));
        }

        /**
         * This operation is used to create a Recipe.
         * 
         * @param loginRequest A DataTransfer Object holding the Login Object
         * @return ApiResponse with the generated Token
         */

        @PostMapping("/login")
        @Operation(summary = "Login", description = "Use for Authentication", tags = {
                        "Login", "post" })
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Login request OK", content = {
                                        @Content(schema = @Schema(implementation = UserProfile.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "404", description = "User Not Found", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                                        @Content(schema = @Schema(implementation = ApiResponse.class), mediaType = "application/json") }) })
        public ResponseEntity<BaseResponse> loginHandler(@Valid @RequestBody LoginRequest loginRequest) {
                try {
                        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                                        loginRequest.getEmail(), loginRequest.getPassword());
                        authManager.authenticate(authInputToken);
                        String token = jwtUtil.generateToken(loginRequest.getEmail());

                        BaseResponse loginResponse = BaseResponse.builder()
                                        .status(HttpStatus.OK.value())
                                        .message(token)
                                        .responseCode(ApplicationConstants.RESPONSE_SUCCESS_CODE)
                                        .build();

                        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
                } catch (AuthenticationException authExc) {
                        throw new RuntimeException(ApplicationConstants.INVALID_LOGIN_CREDENTIALS);
                }
        }

}