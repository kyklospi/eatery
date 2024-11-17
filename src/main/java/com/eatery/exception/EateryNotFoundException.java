package com.eatery.exception;

public class EateryNotFoundException extends RuntimeException {
    public EateryNotFoundException(Long id) {
        super("Could not find eatery " + id);
    }

    public EateryNotFoundException(String s) {
        super("Could not find eatery " + s);
    }
}
