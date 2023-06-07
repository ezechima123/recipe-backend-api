package com.abnamro.privatebanking.shared;

public class MessageUtil {

    public static String RECIPE_NOT_FOUND(String id) {
        return "Recipe Id" + id + " not found!";
    }

    public static String RECIPE_ALREADY_DELETED(String id) {
        return "Recipe Id" + id + " already Deleted!";
    }

    public static String recipeExist(String referenceId) {
        return "Recipe  " + referenceId + " already exist!!";
    }

    public static String userNotFound(String userId) {
        return "User " + userId + " not Found";
    }

}