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
	
	/* (non-Javadoc)
	 * @see com.team19.cs2340.user.IUser#getUsername()
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * @param username			the user name to be set for the user
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/* (non-Javadoc)
	 * @see com.team19.cs2340.user.IUser#getPasswordHash()
	 */
	@Override
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash		the password hash to be set for the user
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/* (non-Javadoc)
	 * @see com.team19.cs2340.user.IUser#getAccountType()
	 */
	@Override
	public AccountType getAccountType() {
		return accountType;
	}
	
	/**
	 * @param accountType		the AccountType to be set for the user
	 */
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	
}
