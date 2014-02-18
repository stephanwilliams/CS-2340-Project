package com.team19.cs2340.user;

import java.io.Serializable;

public interface IUser extends Serializable {
	
	public static enum AccountType {
		ACCOUNT_HOLDER,
		ADMIN
	}
	
	public String getUsername();
	public String getPasswordHash();
	public IUser.AccountType getAccountType();

}
