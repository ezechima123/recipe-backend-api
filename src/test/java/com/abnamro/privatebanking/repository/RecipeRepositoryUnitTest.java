package com.abnamro.privatebanking.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;

import com.abnamro.privatebanking.model.domain.Recipe;
import com.abnamro.privatebanking.model.domain.UserProfile;
import com.abnamro.privatebanking.util.TestUtil;

@DataMongoTest
@Import(value = { TestUtil.class })
public class RecipeRepositoryUnitTest {

    @Autowired
    TestUtil testUtil;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserProfileRepository userRepository;
    private static final String userEmail = "unitTest1@gmail.com";
    private static final String recipeReference1 = "4572-8228-0000";
    private static final String recipeReference2 = "4572-8228-0001";
    private static final String invalidRecipe = "0005044524";

    @AfterEach
    public void destroy() {
        userRepository.deleteAll();
        recipeRepository.deleteAll();
    }

    @DisplayName("Get All Recipes")
    @Test
    public void testFindAllRecipes() {

        UserProfile user = userRepository.save(testUtil.generateUserProfile(userEmail));
        recipeRepository.save(testUtil.generateRecipe(recipeReference1, user));
        recipeRepository.save(testUtil.generateRecipe(recipeReference2, user));

        List<Recipe> recipeList = recipeRepository.findAll();
        Assertions.assertThat(recipeList.size()).isEqualTo(2);
        Assertions.assertThat(recipeList.get(0).getReferenceId()).isEqualTo(recipeReference1);
        Assertions.assertThat(recipeList.get(1).getReferenceId()).isEqualTo(recipeReference2);
    }

    @DisplayName("JUnit test to create a Recipe")
    @Test
    public void testCreateRecipe() {
        UserProfile user = userRepository.save(testUtil.generateUserProfile(userEmail));
        Recipe generatedRecipe = testUtil.generateRecipe(recipeReference1, user);
        Recipe returnedRecipe = recipeRepository.save(generatedRecipe);

        Assertions.assertThat(returnedRecipe).isNotNull();
        Assertions.assertThat(returnedRecipe.getReferenceId()).isEqualTo(generatedRecipe.getReferenceId());
    }

    @DisplayName("Test  Invalid Recipe")
    @Test
    public void testFindInvalidRecipe() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            recipeRepository.findById(invalidRecipe).get();
        });
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getClass()).isEqualTo(NoSuchElementException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("No value present");
    }

    @DisplayName("Update a Recipe")
    @Test
    void testUpdateRecipe() {
        UserProfile user = userRepository.save(testUtil.generateUserProfile(userEmail));
        Recipe generatedRecipe = testUtil.generateRecipe(recipeReference1, user);

        Recipe recipe = recipeRepository.save(generatedRecipe);
        recipe.setTitle("Obono Soup Italian");
        recipe.setComment("Updated");
        Recipe returnedRecipe = recipeRepository.save(recipe);

        assertNotNull(returnedRecipe);
        assertEquals(generatedRecipe.getTitle(), returnedRecipe.getTitle());
        assertEquals(generatedRecipe.getComment(), returnedRecipe.getComment());
    }

    @DisplayName("Delete a Recipe")
    @Test
    public void testDeleteRecipe() {

        UserProfile user = userRepository.save(testUtil.generateUserProfile(userEmail));
        Recipe generatedRecipe = testUtil.generateRecipe(recipeReference1, user);
        Recipe recipe = recipeRepository.save(generatedRecipe);

        userRepository.deleteById(recipe.getId());
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            userRepository.findById(recipe.getId()).get();
        });
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getClass()).isEqualTo(NoSuchElementException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("No value present");
    }

    @DisplayName("Find Recipe by Reference")
    @Test
    void testfindRecipeByReference() {
        UserProfile user = userRepository.save(testUtil.generateUserProfile(userEmail));
        Recipe generatedRecipe = testUtil.generateRecipe(recipeReference1, user);
        recipeRepository.save(generatedRecipe);

        Recipe returnedRecipe = recipeRepository.findByReferenceId(recipeReference1).get();
        assertNotNull(returnedRecipe);
        assertEquals(recipeReference1, returnedRecipe.getReferenceId());
    }

    @DisplayName("Test for DuplicateException")
    @Test
    void testRecipeForDuplicateException() {
        UserProfile user = userRepository.save(testUtil.generateUserProfile(userEmail));
        Recipe generatedRecipe1 = testUtil.generateRecipe(recipeReference1, user);
        Recipe generatedRecipe2 = testUtil.generateRecipe(recipeReference1, user);

        recipeRepository.save(generatedRecipe1);

        Exception exception = assertThrows(DuplicateKeyException.class, () -> {
            recipeRepository.save(generatedRecipe2);
        });
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getClass()).isEqualTo(DuplicateKeyException.class);
    }

}
