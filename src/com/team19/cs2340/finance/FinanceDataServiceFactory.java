package com.team19.cs2340.finance;

import android.content.Context;

/**
 * Factory for creating FinanceDataService objects
 *
 */
public class FinanceDataServiceFactory {
	/**
	 * @param context		a context
	 * @return				an implementation of IFinanceDataService object
	 */
	public static IFinanceDataService createFinanceDataService(Context context) {
		return new LocalFinanceDataService(context);
	}
}
