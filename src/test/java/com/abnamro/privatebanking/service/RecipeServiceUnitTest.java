package com.abnamro.privatebanking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.abnamro.privatebanking.model.domain.Recipe;
import com.abnamro.privatebanking.model.domain.UserProfile;
import com.abnamro.privatebanking.model.dtos.RecipeRequest;
import com.abnamro.privatebanking.repository.RecipeRepository;
import com.abnamro.privatebanking.repository.UserProfileRepository;
import com.abnamro.privatebanking.util.TestUtil;

@Import(value = { TestUtil.class })
@ExtendWith(SpringExtension.class)
public class RecipeServiceUnitTest {

    @Autowired
    TestUtil testUtil;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UserProfileRepository userRepository;
    @InjectMocks
    private RecipeServiceImpl recipeService;
    private static final String userEmail = "unitTest1@gmail.com";
    private static final String recipeReference = "4572-8228-0000";
    private static final String recipeId = "6486cc77ccccccooo999923";

    @DisplayName("Create a Recipe")
    @Test
    void testCreateRecipe() {

        // Arrange
        UserProfile user = testUtil.generateUserProfile(userEmail);
        Recipe recipe = testUtil.generateRecipe(recipeReference, user);
        RecipeRequest recipeRequest = testUtil.createRecipeRequest(recipeReference, userEmail);

        // Act
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(recipeRepository.findByReferenceId(recipe.getReferenceId())).thenReturn(Optional.empty());
        when(recipeRepository.save(Mockito.any(Recipe.class))).thenReturn(recipe);
        Recipe recipeDbMock = recipeService.createRecipe(recipeRequest);

        // Assert
        assertNotNull(recipeDbMock.getReferenceId());
        assertEquals(recipe.getReferenceId(), recipeDbMock.getReferenceId());
    }

    @DisplayName("Get Recipe with Id")
    @Test
    void testGetRecipeById() {
        // Arrange
        Recipe recipe = testUtil.generateRecipeWithoutUser(recipeId, recipeReference);
        // Act
        when(recipeRepository.findById(recipe.getId())).thenReturn(Optional.of(recipe));
        // Assert
        Recipe recipeDbMock = recipeService.getRecipeById(recipe.getId());
        assertNotNull(recipeDbMock.getReferenceId());
        assertEquals(recipe.getId(), recipeDbMock.getId());
    }

    @DisplayName("update a Recipe")
    @Test
    void testUpdateOrDeleteRecipe() {
        // Arrange
        UserProfile user = testUtil.generateUserProfile(userEmail);
        Recipe recipe = testUtil.generateRecipeWithoutUser(recipeId, recipeReference);
        Recipe updatedRecipe = testUtil.generateRecipeWithoutUser(recipeId, recipeReference);
        RecipeRequest updateRequest = testUtil.createRecipeRequest(recipeReference, userEmail);

        // update title and servings on request
        updateRequest.setTitle("American Soup");
        updateRequest.setServings(10);

        updatedRecipe.setTitle("American Soup");
        updatedRecipe.setServings(10);

        // Act
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(recipeRepository.findById(recipe.getId())).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(Mockito.any(Recipe.class))).thenReturn(updatedRecipe);
        Recipe recipeDbMock = recipeService.updateRecipe(recipeId, updateRequest);
        // Assert
        assertNotNull(recipeDbMock.getReferenceId());
        assertEquals("American Soup", recipeDbMock.getTitle());
        assertEquals(10, recipeDbMock.getServings());
    }

}
