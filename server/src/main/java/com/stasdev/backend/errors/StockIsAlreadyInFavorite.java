package com.stasdev.backend.errors;

public class StockIsAlreadyInFavorite extends RuntimeException {
    public StockIsAlreadyInFavorite(String message) {
        super(message);
    }
}
