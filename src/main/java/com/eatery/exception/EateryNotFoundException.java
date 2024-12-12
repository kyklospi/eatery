package com.eatery.exception;

/**
 * Custom exception to indicate that an Eatery could not be found.
 * This exception is thrown when an Eatery is not found in the database.
 */
public class EateryNotFoundException extends RuntimeException {
    public EateryNotFoundException() {
        super("Could not find eatery");
    }

    public EateryNotFoundException(String s) {
        super("Could not find eatery " + s);
    }
}
