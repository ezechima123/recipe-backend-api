package com.abnamro.privatebanking.shared;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.abnamro.privatebanking.recipes.SearchResponseDto;

public class ResponseHandler {

    public static ResponseEntity<?> responseBuilder(String message, HttpStatus httpStatus,
            Object responseObject) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", httpStatus);
        responseMap.put("message", message);
        responseMap.put("data", responseObject);
        return new ResponseEntity<>(responseMap, httpStatus);
    }

    public static ResponseEntity<?> responseSearchBuilder(String message, HttpStatus httpStatus,
            SearchResponseDto searchResponseDto) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", httpStatus);
        responseMap.put("message", message);
        responseMap.put("pageNumber", searchResponseDto.getPageNumber());
        responseMap.put("pageSize", searchResponseDto.getPageSize());
        responseMap.put("TotalCount", searchResponseDto.getTotalCount());
        responseMap.put("TotalPage", searchResponseDto.getTotalPage());
        responseMap.put("data", searchResponseDto.getRecipes());
        return new ResponseEntity<>(responseMap, httpStatus);
    }

}