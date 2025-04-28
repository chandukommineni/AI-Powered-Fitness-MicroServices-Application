package com.fitness.userservice.controller;

import com.fitness.userservice.dto.UserRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;


    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserDetails(@PathVariable String userId){
        return new ResponseEntity<>(userService.getUserDetails(userId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.registerUser(userRequest),HttpStatus.CREATED);
    }




}
