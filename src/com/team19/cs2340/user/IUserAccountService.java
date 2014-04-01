package com.team19.cs2340.user;

public interface IUserAccountService {
	/**
	 * This function authenticates an user and returns an user object.
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws UserAccountException
	 */
	public IUser authenticateUser(String username, String password) throws UserAccountException;
	/**
	 * This function checks whether an user exists.
	 * 
	 * @param username
	 * @return
	 */
	public boolean userExists(String username);
	/**
	 * This function creates an user object
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws UserAccountException
	 */
	public IUser createUser(String username, String password) throws UserAccountException;
}
