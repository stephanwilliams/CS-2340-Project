package com.team19.cs2340.user;

/**
 * Concrete implementation of the IUser interface.
 *
 */
class User implements IUser {
    /**
     * UID for serialization.
     */
    private static final long serialVersionUID = -4509252018208709296L;
    /**
     * The user's username.
     */
    private String username;
    /**
     * A SHA-512 hashed representation of the user's password.
     */
    private String passwordHash;
    /**
     * An enum representing the user's account type - account holder or admin.
     */
    private AccountType accountType;

    /**
     * Instantiates a new user object.
     * 
     * @param username the user's username
     * @param passwordHash a SHA-512 hashed representation of the user's password
     * @param accountType the user's account type
     */
    public User(String username, String passwordHash, AccountType accountType) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.accountType = accountType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.team19.cs2340.user.IUser#getUsername()
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the user name to be set for the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.team19.cs2340.user.IUser#getPasswordHash()
     */
    @Override
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * @param passwordHash
     *            the password hash to be set for the user
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.team19.cs2340.user.IUser#getAccountType()
     */
    @Override
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * @param accountType
     *            the AccountType to be set for the user
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

}
