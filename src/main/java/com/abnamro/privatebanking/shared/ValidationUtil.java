package com.abnamro.privatebanking.shared;

public class ValidationUtil {

    public static boolean isNotEmpty(String value) {
        return (value != null && !value.isEmpty());
    }

}