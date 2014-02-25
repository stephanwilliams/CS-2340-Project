package com.team19.cs2340.user;

import android.content.Context;

public class UserAccountServiceFactory {
	public static IUserAccountService createUserAccountService(Context context) {
		return new LocalUserAccountService(context);
	}
}
