package com.abnamro.privatebanking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Objects;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.abnamro.privatebanking.model.dtos.BaseResponse;
import com.abnamro.privatebanking.model.dtos.RecipeRequest;
import com.abnamro.privatebanking.model.dtos.RecipeResponse;
import com.abnamro.privatebanking.repository.RecipeRepository;
import com.abnamro.privatebanking.util.TestUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeIntegrationTest {

    @Autowired
    TestUtil testUtil;
    @Autowired
    private TestRestTemplate restTemplate;
    private static HttpHeaders headers;

    @Value("${init.user1.name}")
    private String initUser1Name;

    @Value("${init.users.password}")
    private String initUsersPassword;
    private static final String invalidUserEmail = "invalid@gmail.com";
    private static final String recipeReference = "4572-8228-0000";

    private static final String authUrl = "/api/auth/login";
    private static final String recipeUrl = "/api/v1/recipes";

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach
    public void setUpEach() {
        recipeRepository.deleteAll();
    }

    @DisplayName("Login with the Wrong Credentials")
    @Test
    @Order(1)
    void testWrongCredentialsLogin() throws Exception {
        BaseResponse loginResponse = LoginRequest(invalidUserEmail, initUsersPassword);
        assertEquals(HttpStatus.BAD_REQUEST.value(), loginResponse.getStatus());
        assertEquals("1", loginResponse.getResponseCode());
    }

    @DisplayName("Login with the Empty Credentials")
    @Test
    @Order(2)
    void testEmptyCredentialLogin() throws Exception {
        BaseResponse loginResponse = LoginRequest(" ", initUsersPassword);
        assertEquals(HttpStatus.BAD_REQUEST.value(), loginResponse.getStatus());
        assertEquals("1", loginResponse.getResponseCode());
        Assertions.assertThat(loginResponse.getErrors().size()).isGreaterThan(0);
    }

    @DisplayName("Login with the right Credentials")
    @Test
    @Order(3)
    void testSuccessFullLogin() throws Exception {
        BaseResponse loginResponse = LoginRequest(initUser1Name, initUsersPassword);
        assertNotNull(loginResponse.getMessage());

        assertEquals(HttpStatus.OK.value(), loginResponse.getStatus());
        assertEquals("0", loginResponse.getResponseCode());
    }

    @DisplayName("Create a Recipe")
    @Test
    @Order(4)
    void testCreateRecipe() throws Exception {
        // Login to generate the Token
        BaseResponse loginResponse = LoginRequest(initUser1Name, initUsersPassword);
        assertNotNull(loginResponse.getMessage());

        // set the Token on the Authorization Tag Header
        headers.set("Authorization", "Bearer " + loginResponse.getMessage());

        // Creating the Recipe Request
        RecipeResponse recipeApiResponse = createRecipeRequest(recipeReference);
        assertEquals(HttpStatus.CREATED.value(), recipeApiResponse.getStatus());
        assertEquals(recipeReference, recipeApiResponse.getData().getReferenceId());
        assertEquals("Success OK", recipeApiResponse.getMessage());
    }

    @DisplayName("Delete a Recipe")
    @Test
    @Order(5)
    void testDeleteRecipe() throws Exception {
        // Recipe created on the system
        RecipeResponse createAPIResponse = createRecipeRequest(recipeReference);
        assertEquals(HttpStatus.CREATED.value(), createAPIResponse.getStatus());
        assertEquals(recipeReference, createAPIResponse.getData().getReferenceId());

        // Generate the update Request
        String comment = "Test";
        String deleteUrl = recipeUrl + "/" + createAPIResponse.getData().getId() + "/" + comment;
        HttpEntity<String> responseEntity = new HttpEntity<>(headers);
        ResponseEntity<RecipeResponse> response = restTemplate.exchange(
                deleteUrl, HttpMethod.DELETE, responseEntity, RecipeResponse.class);

        // Assert Response
        RecipeResponse deleteResponse = Objects.requireNonNull(response.getBody());
        assertEquals(HttpStatus.OK.value(), deleteResponse.getStatus());
        assertEquals("Success OK", deleteResponse.getMessage());
    }

    @DisplayName("Fetch a Recipe using Id")
    @Test
    @Order(5)
    void testGetRecipeById() throws Exception {
        // Recipe created on the system
        RecipeResponse createAPIResponse = createRecipeRequest(recipeReference);
        assertEquals(HttpStatus.CREATED.value(), createAPIResponse.getStatus());
        assertEquals(recipeReference, createAPIResponse.getData().getReferenceId());
        assertNotNull(createAPIResponse.getData().getId());

        // Generate the get Request
        String getUrl = recipeUrl + "/" + createAPIResponse.getData().getId();
        HttpEntity<String> responseEntity = new HttpEntity<>(headers);
        ResponseEntity<RecipeResponse> response = restTemplate.exchange(
                getUrl, HttpMethod.GET, responseEntity, RecipeResponse.class);
        RecipeResponse getResponse = Objects.requireNonNull(response.getBody());

        // Assert Response
        assertEquals(HttpStatus.OK.value(), getResponse.getStatus());
        assertEquals("Success OK", getResponse.getMessage());
        assertEquals(recipeReference, getResponse.getData().getReferenceId());
    }

    @DisplayName("Update Recipe")
    @Test
    @Order(6)
    void testUpdateRecipe() throws Exception {
        // Recipe created on the system
        RecipeResponse createAPIResponse = createRecipeRequest(recipeReference);
        assertEquals(HttpStatus.CREATED.value(), createAPIResponse.getStatus());
        assertEquals(recipeReference, createAPIResponse.getData().getReferenceId());

        // Generate the update Request
        String updateUrl = recipeUrl + "/" + createAPIResponse.getData().getId();
        RecipeRequest modifyRequest = testUtil.createRecipeRequest(recipeReference,
                createAPIResponse.getData().getMaintainedBy());
        modifyRequest.setReferenceId(createAPIResponse.getData().getId());
        modifyRequest.setComment("UPDATED");
        modifyRequest.setTitle("AMRO FOODa");

        HttpEntity<String> responseEntity = new HttpEntity<>(testUtil.objectToJson(modifyRequest), headers);
        ResponseEntity<RecipeResponse> response = restTemplate.exchange(
                updateUrl, HttpMethod.PUT, responseEntity, RecipeResponse.class);
        RecipeResponse updateResponse = Objects.requireNonNull(response.getBody());

        // Assert Response
        assertEquals(HttpStatus.OK.value(), updateResponse.getStatus());
        assertEquals(modifyRequest.getComment(), updateResponse.getData().getComment());
        assertEquals(modifyRequest.getTitle(), updateResponse.getData().getTitle());
    }

    private BaseResponse LoginRequest(String email, String password) throws Exception {
        HttpEntity<String> responseEntity = new HttpEntity<>(
                testUtil.objectToJson(testUtil.generateLoginRequest(email, password)), headers);
        ResponseEntity<BaseResponse> response = restTemplate.exchange(
                authUrl, HttpMethod.POST, responseEntity, BaseResponse.class);
        return Objects.requireNonNull(response.getBody());
    }

    private RecipeResponse createRecipeRequest(String referenceId) throws Exception {
        RecipeRequest recipeRequest = testUtil.createRecipeRequest(referenceId, initUser1Name);
        System.out.println("HELLO =>" + testUtil.objectToJson(recipeRequest));
        HttpEntity<String> responseEntity = new HttpEntity<>(testUtil.objectToJson(recipeRequest), headers);
        ResponseEntity<RecipeResponse> response = restTemplate.exchange(
                recipeUrl, HttpMethod.POST, responseEntity, RecipeResponse.class);
        return Objects.requireNonNull(response.getBody());
    }

}
