/*
 * Copyright 2023-2023 the original author 
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.abnamro.privatebanking.model.domain.UserProfile;
import com.abnamro.privatebanking.repository.UserProfileRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * The class is not necessary needed in production, However it load the MongoDB
 * with Test
 * Users so as to elimiate the need for registering service. It also deletes the
 * data when
 * the service is shutdown
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Slf4j
@Component
public class InitDbConfig {

    @Value("${init.user1.name}")
    private String initUser1Name;

    @Value("${init.user2.name}")
    private String initUser2Name;

    @Value("${init.users.password}")
    private String initUsersPassword;

    @Value("${init.users.fullname}")
    private String initUsersFullName;

    @Autowired
    UserProfileRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void init() {
        log.debug("About to add test users");
        UserProfile user1 = UserProfile.builder().email(initUser1Name).fullName(initUsersFullName)
                .passWordHash(bCryptPasswordEncoder.encode(initUsersPassword))
                .build();
        log.debug("User Test 1 initialized");
        UserProfile user2 = UserProfile.builder().email(initUser2Name).fullName(initUsersFullName)
                .passWordHash(bCryptPasswordEncoder.encode(initUsersPassword))
                .build();
        log.debug("User Test 2 initialized");
        userRepository.save(user1);
        userRepository.save(user2);

        log.debug("List of Test Users added below");
        userRepository.findAll().stream().forEach(userProfile -> log.debug(userProfile.toString()));
    }

    @PreDestroy
    public void destroy() {
        userRepository.deleteAll();
        log.debug("All Users Deleted");
    }

}