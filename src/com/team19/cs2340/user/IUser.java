package com.team19.cs2340.user;

public interface IUser {
	
	public static enum AccountType {
		ACCOUNT_HOLDER,
		ADMIN
	}
	
	public String getUsername();
	public String getPasswordHash();
	public IUser.AccountType getAccountType();

}
