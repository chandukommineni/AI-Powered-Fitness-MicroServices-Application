package com.fitness.activityservice.controller.exceptions;


import com.fitness.activityservice.exception.ActivityNotFoundException;

import com.fitness.activityservice.exception.InvalidUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.fitness.activityservice.dto.Error;
@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(ActivityNotFoundException.class)
    public ResponseEntity<Error> activityNotFoundExceptionHandler(Exception e){
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Error> invalidUserExceptionHandler(Exception e){
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);

    }


}
