package com.abnamro.privatebanking.shared;

import java.util.Date;
import org.springframework.http.HttpStatus;
import com.abnamro.privatebanking.exception.ErrorDetails;

public class ExceptionUtil {

    public static ErrorDetails generateError(String message, int status) {
        ErrorDetails error = new ErrorDetails();
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.addError(message);
        return error;
    }

}