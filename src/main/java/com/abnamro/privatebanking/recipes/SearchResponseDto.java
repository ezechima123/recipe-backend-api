package com.abnamro.privatebanking.recipes;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseDto {
    private String status;
    private int totalCount;
    private int totalPage;
    private int pageNumber;
    private int pageSize;
    private List<RecipeResponseDto> recipes;

}