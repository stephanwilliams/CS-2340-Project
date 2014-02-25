package com.team19.cs2340.user;

public interface IUserAccountService {
	public IUser authenticateUser(String username, String password) throws UserAccountException;
	public boolean userExists(String username);
	public IUser createUser(String username, String password) throws UserAccountException;
}
