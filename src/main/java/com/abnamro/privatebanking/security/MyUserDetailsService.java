/*
 * Copyright 2023-2023 the original author 
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.abnamro.privatebanking.constant.ApplicationConstants;
import com.abnamro.privatebanking.model.domain.UserProfile;
import com.abnamro.privatebanking.repository.UserProfileRepository;

/**
 * The class wraps and retrieves the User Document class from the database
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserProfileRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserProfile user = userRepo.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format(ApplicationConstants.USER_NOT_FOUND, email)));

        return new org.springframework.security.core.userdetails.User(
                email,
                user.getPassWordHash(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}