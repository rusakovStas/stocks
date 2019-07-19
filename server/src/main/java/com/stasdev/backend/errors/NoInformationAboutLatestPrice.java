package com.stasdev.backend.errors;

public class NoInformationAboutLatestPrice extends RuntimeException {
    public NoInformationAboutLatestPrice(String message) {
        super(message);
    }
}
