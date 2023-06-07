/*
 * Copyright 2023-2024 the original author or authors.
 *
 * Licensed under the ABN AMRO, Version 2.0 (the "License");
 */

package com.abnamro.privatebanking.recipes;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abnamro.privatebanking.exception.ResourceNotFoundException;
import com.abnamro.privatebanking.shared.MessageUtil;
import com.abnamro.privatebanking.users.UserModel;
import com.abnamro.privatebanking.users.UserRepository;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public RecipeResponseDto getRecipeById(String recipeId) {

        RecipeModel recipeModel = recipeRepository.findById(recipeId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(MessageUtil.RECIPE_NOT_FOUND(recipeId)));
        return recipeMapper.recipeModelToRecipeDTO(recipeModel);
    }

    @Override
    public RecipeResponseDto addRecipe(NewRecipeDto newRecipeDto) {
        LocalDateTime localDateTime = LocalDateTime.now();

        Optional<RecipeModel> recipeModelOptional = recipeRepository.findByReferenceId(newRecipeDto.getReferenceId());
        if (recipeModelOptional.isPresent()) {
            throw new ResourceNotFoundException(MessageUtil.recipeExist(newRecipeDto.getReferenceId()));
        }

        UserModel createdByUser = userRepository.findById(newRecipeDto.getCreatedBy())
                .orElseThrow(
                        () -> new ResourceNotFoundException(MessageUtil.userNotFound(newRecipeDto.getCreatedBy())));

        RecipeModel recipeModel = recipeMapper.newRecipeDtoToRecipeModel(newRecipeDto);
        recipeModel.setInitiatedBy(createdByUser);
        recipeModel.setCreatedDate(localDateTime);
        recipeModel.setModifiedDate(localDateTime);
        recipeModel.setStatus("ACTIVE");
        return recipeMapper.recipeModelToRecipeDTO(recipeRepository.save(recipeModel));
    }

    @Override
    public RecipeResponseDto updateRecipe(String id, RecipeResponseDto recipeDTO) {
        Optional<RecipeModel> recipeModelOptional = recipeRepository.findById(id);
        if (!recipeModelOptional.isPresent()) {
            throw new ResourceNotFoundException(MessageUtil.RECIPE_NOT_FOUND(id));
        }

        UserModel modifiedByUser = this.userRepository.findById(recipeDTO.getModifiedBy())
                .orElseThrow(
                        () -> new ResourceNotFoundException(MessageUtil.userNotFound(recipeDTO.getModifiedBy())));

        RecipeModel recipeModel = recipeModelOptional.get();
        recipeModel.setReferenceId(recipeDTO.getReferenceId());
        recipeModel.setTitle(recipeDTO.getTitle());
        recipeModel.setIngredients(recipeDTO.getIngredients());
        recipeModel.setInstructions(recipeDTO.getInstructions());
        recipeModel.setUpdatedBy(modifiedByUser);
        recipeModel.setModifiedDate(LocalDateTime.now());
        recipeModel.setComment(recipeDTO.getComment());
        return recipeMapper.recipeModelToRecipeDTO(recipeRepository.save(recipeModel));
    }

    @Override
    public void deleteRecipe(String recipeId, String comment) {

        Optional<RecipeModel> recipeModelOptional = recipeRepository.findById(recipeId);
        if (!recipeModelOptional.isPresent()) {
            throw new ResourceNotFoundException(MessageUtil.RECIPE_NOT_FOUND(recipeId));
        }

        RecipeModel recipeModel = recipeModelOptional.get();
        if (recipeModel.getStatus().equalsIgnoreCase("DELETED")) {
            throw new ResourceNotFoundException(MessageUtil.RECIPE_ALREADY_DELETED(recipeId));
        }
        recipeModel.setStatus("DELETED");
        recipeModel.setComment(comment);
        recipeRepository.save(recipeModel);
    }

    @Override
    public SearchResponseDto getRecipeByCriteria(SearchRequestDto searchRequestDto) {

        Pageable pageable = PageRequest.of(searchRequestDto.getPageNumber(), searchRequestDto.getPageSize());
        Page<RecipeModel> recipeModelPage = recipeRepository.searchRecipesByCriteria(searchRequestDto, pageable);
        return SearchResponseDto.builder().pageNumber(recipeModelPage.getNumber()).pageSize(recipeModelPage.getSize())
                .totalPage(recipeModelPage.getTotalPages())
                .recipes(recipeMapper.recipeModelListToRecipeDtoList(recipeModelPage.getContent())).build();
    }

}