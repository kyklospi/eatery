package com.favourite.eatery.exception;

public class EateryNotFoundException extends RuntimeException {
    public EateryNotFoundException(Long id) {
        super("Could not find eatery " + id);
    }
}
