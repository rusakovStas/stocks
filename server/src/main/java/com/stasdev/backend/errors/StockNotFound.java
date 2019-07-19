package com.stasdev.backend.errors;

public class StockNotFound extends RuntimeException {
    public StockNotFound(String message) {
        super(message);
    }
}
