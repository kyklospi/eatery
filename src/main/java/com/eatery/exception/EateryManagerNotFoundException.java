package com.eatery.exception;

public class EateryManagerNotFoundException extends RuntimeException {
    public EateryManagerNotFoundException() {
        super("Could not find eatery manager ");
    }
}
