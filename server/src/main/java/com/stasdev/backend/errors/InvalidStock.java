package com.stasdev.backend.errors;

public class InvalidStock extends RuntimeException {
    public InvalidStock(String message) {
        super(message);
    }
}
