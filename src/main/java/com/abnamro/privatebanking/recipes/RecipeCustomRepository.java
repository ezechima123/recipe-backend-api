package com.abnamro.privatebanking.recipes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeCustomRepository {

        public Page<RecipeModel> searchRecipesByCriteria(SearchRequestDto searchRequestDto, Pageable page);
}
