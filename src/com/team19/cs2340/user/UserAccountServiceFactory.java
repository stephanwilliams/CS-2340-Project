package com.team19.cs2340.user;

import android.content.Context;

public class UserAccountServiceFactory {
	public IUserAccountService getUserAccountService(Context context) {
		return new LocalUserAccountService(context);
	}
}
