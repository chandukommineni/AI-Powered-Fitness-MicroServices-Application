package com.fitness.apigateway.user;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;

    private String keycloakId;
    private String email;


    private String password;


    private UserRole role;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;
}
