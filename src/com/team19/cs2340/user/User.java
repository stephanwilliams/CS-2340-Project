package com.team19.cs2340.user;

class User implements IUser {
	private static final long serialVersionUID = -4509252018208709296L;
	private String username;
	private String passwordHash;
	private AccountType accountType;
	
	public User(String username, String passwordHash, AccountType accountType) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.accountType = accountType;
	}
	
	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public AccountType getAccountType() {
		return accountType;
	}
	
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	
}
