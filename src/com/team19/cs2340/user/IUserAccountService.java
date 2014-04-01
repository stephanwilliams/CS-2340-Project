package com.team19.cs2340.user;

public interface IUserAccountService {
	/**
	 * This function authenticates an user and returns an user object.
	 * 
	 * @param username						the username of the user to be authenticated
	 * @param password						the password of the user to be authenticated
	 * @return 								the authenticated IUser object
	 * @throws UserAccountException
	 */
	public IUser authenticateUser(String username, String password) throws UserAccountException;
	/**
	 * This function checks whether an user exists.
	 * 
	 * @param username						the username of the user to be checked
	 * @return 								True if user exists, else False. 
	 */
	public boolean userExists(String username);
	/**
	 * This function creates an user object
	 * 
	 * @param username						the username of the user to be created
	 * @param password						the password of the user to be created
	 * @return 								the created IUser object
	 * @throws UserAccountException
	 */
	public IUser createUser(String username, String password) throws UserAccountException;
}
