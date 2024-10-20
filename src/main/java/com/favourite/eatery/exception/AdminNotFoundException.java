package com.favourite.eatery.exception;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(Long id) {
        super("Could not find administrator " + id);
    }
}
