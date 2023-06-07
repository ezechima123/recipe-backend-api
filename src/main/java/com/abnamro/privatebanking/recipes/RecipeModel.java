package com.abnamro.privatebanking.recipes;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.abnamro.privatebanking.users.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Recipes")
@CompoundIndex(def = "{'referenceId': 1, 'title': 1}", unique = true)
public class RecipeModel {
    @Id
    private String id;
    @Indexed
    private String referenceId;
    private String title;
    private RecipeType recipeType;
    private int servings;
    private List<String> ingredients;
    private List<String> instructions;
    private LocalDateTime createdDate;
    @DBRef
    private UserModel initiatedBy;
    private LocalDateTime modifiedDate;
    private UserModel updatedBy;
    private String status;
    private String comment;

    public enum RecipeType {
        ALL, VEGETERIAN, NONVEGETERIAN
    }

}