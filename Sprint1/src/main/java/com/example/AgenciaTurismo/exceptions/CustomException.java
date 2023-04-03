package com.example.AgenciaTurismo.exceptions;

public class CustomException extends RuntimeException {
    private String name;

    public CustomException(String name, String message) {
        super(message);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}