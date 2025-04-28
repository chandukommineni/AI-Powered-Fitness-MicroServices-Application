package com.fitness.activityservice.controller.exceptions;


import com.fitness.activityservice.exception.ActivityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.fitness.activityservice.dto.Error;
@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(ActivityNotFoundException.class)
    public ResponseEntity<Error> userNotFoundExceptionHandler(Exception e){
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.NOT_FOUND);
    }


}
