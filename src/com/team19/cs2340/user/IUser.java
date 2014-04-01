package com.team19.cs2340.user;

import java.io.Serializable;

/**
 * Interface defining the necessary field for a user.
 *
 */
public interface IUser extends Serializable {

    /**
     * Possible user types.
     */
    public static enum AccountType {
        /**
         * Default account type for normal users.
         */
        ACCOUNT_HOLDER,
        /**
         * Special account type for administrators.
         */
        ADMIN
    }

    /**
     * @return the username of the user requested
     */
    String getUsername();

    /**
     * @return the password hash (String)
     */
    String getPasswordHash();

    /**
     * @return the AccountType of the IUser
     */
    IUser.AccountType getAccountType();

}
