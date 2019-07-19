package com.stasdev.backend.errors;

public class NoInformationAboutSector extends RuntimeException {
    public NoInformationAboutSector(String message) {
        super(message);
    }
}
