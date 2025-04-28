package com.fitness.userservice.dto;

import com.fitness.userservice.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UserRequest {


    private String firstName;
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "Invalid Email format")
    private String email;


    @NotBlank(message = "password is required")
    @Size(min = 6,message = "Password must be atleast 6 characters long ")
    private String password;


}
