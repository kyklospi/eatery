package com.eatery.exception;

public class EateryNotFoundException extends RuntimeException {
    public EateryNotFoundException() {
        super("Could not find eatery");
    }

    public EateryNotFoundException(String s) {
        super("Could not find eatery " + s);
    }
}
