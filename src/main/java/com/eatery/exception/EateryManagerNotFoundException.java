package com.eatery.exception;

/**
 * Custom exception to indicate that an EateryManager could not be found.
 * This exception is thrown when an EateryManager is not found in the database.
 */
public class EateryManagerNotFoundException extends RuntimeException {
    public EateryManagerNotFoundException() {
        super("Could not find eatery manager ");
    }
}
