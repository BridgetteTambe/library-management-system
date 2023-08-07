package com.drycode.exceptions;

import com.drycode.exceptions.dto.ExceptionResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ExceptionResponse exception(Exception e) {
        return new ExceptionResponse(e.getMessage());
    }
}
