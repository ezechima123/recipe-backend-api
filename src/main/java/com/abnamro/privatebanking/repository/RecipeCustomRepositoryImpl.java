/*
 * Copyright 2023-2024 the original author or authors.
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */
package com.abnamro.privatebanking.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import com.abnamro.privatebanking.model.domain.Recipe;
import com.abnamro.privatebanking.model.dtos.FilterQuery;
import com.abnamro.privatebanking.model.dtos.SearchRequest;
import com.abnamro.privatebanking.util.ValidationService;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation class for filtering and Searching Recipes based on Criteria
 * 
 * @author Chima Emmanuel Ezeamama
 * @version 1.0
 */
@Slf4j
@Repository
public class RecipeCustomRepositoryImpl implements RecipeCustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ValidationService validationService;

    @Override
    public Page<Recipe> searchRecipesByCriteria(SearchRequest searchRequestDto) {

        Pageable page = PageRequest.of(searchRequestDto.getPage(), searchRequestDto.getSize());
        final Query query = new Query().with(page);
        final List<Criteria> criteria = new ArrayList<>();
        query.fields().include("id").include("referenceId").include("title").include("vegetarian")
                .include("servings").include("ingredients").include("instructions").include("lastMaintainedBy")
                .include("lastMaintainedDate").include("status").include("comment");

        criteria.add(Criteria.where("status").ne("DELETED"));

        if (!searchRequestDto.getSearchType().equals("All")) {
            criteria.add(Criteria.where("vegetarian").is(searchRequestDto.isVegetarian()));
            if (searchRequestDto.getServings() > 0)
                criteria.add(Criteria.where("servings").is(searchRequestDto.getServings()));
            if (validationService.isNotEmpty(searchRequestDto.getIngredient()))
                criteria.add(Criteria.where("ingredients").in(searchRequestDto.getIngredient()));
            if (validationService.isNotEmpty(searchRequestDto.getInstruction()))
                criteria.add(Criteria.where("instructions").in(searchRequestDto.getInstruction()));
        }

        log.debug("Search Criteria Size {}", criteria.size());
        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));

        log.debug("ResultSize {}", mongoTemplate.find(query, Recipe.class).size());

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Recipe.class),
                page,
                () -> mongoTemplate.count(
                        query.skip(0).limit(0),
                        Recipe.class));

    }

    @Override
    public Page<Recipe> queryRecipeByFilters(MultiValueMap<String, String> filters, int page, int size) {
        Pageable pageResponse = PageRequest.of(page, size);
        final Query query = new Query().with(pageResponse);
        final List<Criteria> criteria = new ArrayList<>();
        query.fields().include("id").include("referenceId").include("title").include("vegetarian")
                .include("servings").include("ingredients").include("instructions").include("lastMaintainedBy")
                .include("lastMaintainedDate").include("status").include("comment");

        criteria.add(Criteria.where("status").ne("DELETED"));

        for (String filterExpression : filters.get("filter")) {
            log.debug("FilterExpression {}", filterExpression);
            FilterQuery filterQuery = validationService.processFilterQuery(filterExpression);
            log.debug("FilterQuery {}", filterQuery.toString());

            switch (filterQuery.getOperator()) {
                case "eq":
                    criteria.add(Criteria.where(filterQuery.getFilterBy()).is(filterQuery.getFilterValue()));
                    break;
                case "ne":
                    criteria.add(Criteria.where(filterQuery.getFilterBy()).ne(filterQuery.getFilterValue()));
                    break;
                case "in":
                    criteria.add(Criteria.where(filterQuery.getFilterBy()).in(filterQuery.getFilterValue()));
                    break;
                case "nin":
                    criteria.add(Criteria.where(filterQuery.getFilterBy()).nin(filterQuery.getFilterValue()));
                    break;
                case "gt":
                    criteria.add(Criteria.where(filterQuery.getFilterBy()).gt(filterQuery.getFilterValue()));
                    break;
                case "lt":
                    criteria.add(Criteria.where(filterQuery.getFilterBy()).lt(filterQuery.getFilterValue()));
                    break;
            }

        }
        log.debug("Search Criteria Size {}", criteria.size());
        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));

        log.debug("ResultSize {}", mongoTemplate.find(query, Recipe.class).size());

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Recipe.class),
                pageResponse,
                () -> mongoTemplate.count(
                        query.skip(0).limit(0),
                        Recipe.class));
    }

}