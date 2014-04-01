package com.team19.cs2340.user;

/**
 * Interface defining a service for retrieving and working with user data.
 *
 */
public interface IUserAccountService {
    /**
     * This function authenticates an user and returns an user object.
     * 
     * @param username
     *            the username of the user to be authenticated
     * @param password
     *            the password of the user to be authenticated
     * @return the authenticated IUser object
     * @throws UserAccountException if an error occured authenticating the user
     */
    IUser authenticateUser(String username, String password)
        throws UserAccountException;

    /**
     * This function checks whether an user exists.
     * 
     * @param username
     *            the username of the user to be checked
     * @return True if user exists, else False.
     */
    boolean userExists(String username);

    /**
     * This function creates an user object.
     * 
     * @param username
     *            the username of the user to be created
     * @param password
     *            the password of the user to be created
     * @return the created IUser object
     * @throws UserAccountException if an error occured creating the user
     */
    IUser createUser(String username, String password)
        throws UserAccountException;
}
