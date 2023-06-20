package com.abnamro.privatebanking.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import com.abnamro.privatebanking.model.domain.UserProfile;
import com.abnamro.privatebanking.util.TestUtil;

@DataMongoTest
@Import(value = { TestUtil.class })
public class UserProfileRepositoryUnitTest {
    @Autowired
    TestUtil testUtil;
    @Autowired
    private UserProfileRepository userRepository;

    private static final String userEmail1 = "unitTest1@gmail.com";
    private static final String userEmail2 = "unitTest2@gmail.com";
    private static final String userEmail3 = "unitTest3@gmail.com";
    private static final String invalidUserEmail = "invalid@gmail.com";

    @BeforeEach
    public void setUp() {
        userRepository.save(testUtil.generateUserProfile(userEmail1));
        userRepository.save(testUtil.generateUserProfile(userEmail2));
    }

    @AfterEach
    public void destroy() {
        userRepository.deleteAll();
    }

    @DisplayName("Get AllUsers Test")
    @Test
    @Order(1)
    public void testFindAllUserProfiles() {
        List<UserProfile> userList = userRepository.findAll();
        Assertions.assertThat(userList.size()).isEqualTo(2);
        Assertions.assertThat(userList.get(0).getEmail()).isEqualTo(userEmail1);
        Assertions.assertThat(userList.get(1).getEmail()).isEqualTo(userEmail2);
    }

    @Order(2)
    @DisplayName("FindUserBy Email ")
    @Test
    void testfindUserProfileByEmail() {
        UserProfile user = userRepository.findByEmail(userEmail1).get();
        assertNotNull(user);
        assertEquals(userEmail1, user.getEmail());
    }

    @Order(3)
    @DisplayName("Create a User")
    @Test
    public void testCreateUserProfile() {
        UserProfile user = testUtil.generateUserProfile(userEmail3);
        UserProfile returnedUser = userRepository.save(user);
        Assertions.assertThat(returnedUser).isNotNull();
        Assertions.assertThat(user.getEmail()).isEqualTo(returnedUser.getEmail());
    }

    @Order(4)
    @DisplayName("Test Invalid UserEmail")
    @Test
    public void testInvalidUserProfile() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            userRepository.findByEmail(invalidUserEmail).get();
        });
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getClass()).isEqualTo(NoSuchElementException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("No value present");
    }

    @Order(5)
    @DisplayName("Update a User")
    @Test
    void testUpdateUserProfile() {

        UserProfile user = userRepository.findByEmail(userEmail1).get();
        user.setFullName("Chim AbnAmro");
        UserProfile returnedUser = userRepository.save(user);

        assertNotNull(returnedUser);
        assertEquals(user.getFullName(), returnedUser.getFullName());
    }

    @Order(6)
    @DisplayName("Delete a User")
    @Test
    public void testDeleteUserProfile() {
        UserProfile user = userRepository.findByEmail(userEmail1).get();
        userRepository.deleteById(user.getId());
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            userRepository.findById(user.getId()).get();
        });
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getClass()).isEqualTo(NoSuchElementException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("No value present");
    }

}
