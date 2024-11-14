package com.favourite.eatery.exception;

public class EateryManagerNotFoundException extends RuntimeException {
    public EateryManagerNotFoundException(Long id) {
        super("Could not find eatery manager " + id);
    }
}
