package com.team19.cs2340.user;

import android.content.Context;

/**
 * Factory for creating concrete UserAccountService objects.
 *
 */
public abstract class UserAccountServiceFactory {
    /**
     * Returns an IUserAccountService implementation.
     * 
     * @param context the current context
     * @return a concrete IUserAccountService implementation
     */
    public static IUserAccountService createUserAccountService(Context context) {
        return new LocalUserAccountService(context);
    }
}
