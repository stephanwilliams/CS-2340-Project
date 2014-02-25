package com.team19.cs2340.finance;

import android.content.Context;

public class FinanceDataServiceFactory {
	public static IFinanceDataService createFinanceDataService(Context context) {
		return new LocalFinanceDataService(context);
	}
}
