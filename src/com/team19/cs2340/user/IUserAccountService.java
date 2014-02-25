package com.team19.cs2340.user;

public interface IUserAccountService {
	public IUser authenticateUser(String username, String password);
	public boolean userExists(String username);
	public boolean createUser(String username, String password);
}
