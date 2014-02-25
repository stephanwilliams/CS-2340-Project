package com.team19.cs2340.finance;

import java.math.BigDecimal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.team19.cs2340.DatabaseHelper;
import com.team19.cs2340.user.IUser;

public class LocalFinanceDataService implements IFinanceDataService{
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	
	public LocalFinanceDataService(Context context) {
		this.dbHelper = new DatabaseHelper(context);
		this.db = dbHelper.getWritableDatabase();
	}
	
	@Override
	public IAccount createAccount(IUser user, String fullName, String displayName,
			BigDecimal balance, BigDecimal monthlyInterest) {
		ContentValues cv = new ContentValues();
		cv.put("username", user.getUsername());
		cv.put("fullName", fullName);
		cv.put("displayName", displayName);
		cv.put("balance", balance.toString());
		cv.put("monthlyInterest", monthlyInterest.toString());

		long id = db.insert("accounts", null, cv);

		if (id == -1) {
			// failed to create account
			return null;
		} else {
			return getAccount(user, id);
		}
	}
	
	@Override
	public IAccount getAccount(IUser user, long accountId) {
		Cursor cursor =
			db.query("accounts",
					 new String[] {
						"_id", "username", "fullName", "displayName",
						"balance", "monthlyInterest" },
					 "_id = ?",
					 new String[] { Long.toString(accountId),
									user.getUsername() },
					 null,
					 null,
					 null);
		if (!cursor.moveToFirst()) {
			// invalid account ID
			return null;
		} else {
			if (!cursor.getString(1).equals(user.getUsername())) {
				// account does not belong to given user
				return null;
			} else {
				IAccount account = new Account(
						cursor.getLong(0),
						cursor.getString(2),
						cursor.getString(3),
						new BigDecimal(cursor.getString(4)),
						new BigDecimal(cursor.getString(5)));
				return account;
			}
		}
		
	}
	
}
