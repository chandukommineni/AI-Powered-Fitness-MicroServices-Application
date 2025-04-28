package com.fitness.userservice.controller.exceptions;


import com.fitness.userservice.dto.Error;
import com.fitness.userservice.exceptions.UserAlreadyExistsException;
import com.fitness.userservice.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> userNotFoundExceptionHandler(Exception e){
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Error> userAlreadyExistsExceptionHandler(Exception e){
        return new ResponseEntity<>(new Error(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

}
