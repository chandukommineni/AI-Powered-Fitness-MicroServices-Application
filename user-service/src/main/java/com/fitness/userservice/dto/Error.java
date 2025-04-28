package com.fitness.userservice.dto;

import lombok.Data;

@Data
public class Error {
    private String message;
    private boolean error=true;

    public Error(String message) {
        this.message = message;
    }
}
