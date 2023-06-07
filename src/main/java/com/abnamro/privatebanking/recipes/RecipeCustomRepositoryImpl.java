package com.abnamro.privatebanking.recipes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.abnamro.privatebanking.shared.ValidationUtil;

@Repository
public class RecipeCustomRepositoryImpl implements RecipeCustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Page<RecipeModel> searchRecipesByCriteria(SearchRequestDto searchRequestDto, Pageable page) {

        final Query query = new Query().with(page);
        query.fields().include("id").include("referenceId").include("id")
                .include("referenceId").include("id").include("referenceId");

        final List<Criteria> criteria = new ArrayList<>();
        criteria.add(Criteria.where("recipeType").is(searchRequestDto.getRecipeType()));
        if (searchRequestDto.getServings() > 0)
            criteria.add(Criteria.where("servings").is(searchRequestDto.getServings()));
        if (ValidationUtil.isNotEmpty(searchRequestDto.getIngredient()))
            criteria.add(Criteria.where("ingredientRef").in(searchRequestDto.getIngredient()));
        if (ValidationUtil.isNotEmpty(searchRequestDto.getInstruction()))
            criteria.add(Criteria.where("instructionRef").in(searchRequestDto.getInstruction()));
        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, RecipeModel.class),
                page,
                () -> mongoTemplate.count(query.skip(0).limit(0), RecipeModel.class));

    }

}