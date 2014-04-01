package com.team19.cs2340.user;

/**
 * An exception thrown when issues were encountered working with user data.
 *
 */
public class UserAccountException extends Exception {

    /**
     * UID for serialization.
     */
    private static final long serialVersionUID = 1105300393864012098L;

    /**
     * Instantiates a new UserAccountException with the given error message.
     * 
     * @param message the error message
     */
    public UserAccountException(String message) {
        super(message);
    }
}