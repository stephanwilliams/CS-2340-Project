package com.team19.cs2340.user;

import java.io.Serializable;

public interface IUser extends Serializable {
	
	public static enum AccountType {
		ACCOUNT_HOLDER,
		ADMIN
	}
	
	/**
	 * @return		the username of the user requested
	 */
	public String getUsername();
	/**
	 * @return		the password hash (String)
	 */
	public String getPasswordHash();
	/**
	 * @return		the AccountType of the IUser
	 */
	public IUser.AccountType getAccountType();

}
