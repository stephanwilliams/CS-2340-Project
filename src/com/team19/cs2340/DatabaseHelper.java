package com.team19.cs2340;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static final int VERSION = 7;
	public static final String DATABASE_NAME = "finance.db";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE users ("
				+ "username TEXT PRIMARY KEY,"
				+ "password TEXT NOT NULL,"
				+ "accountType INTEGER NOT NULL DEFAULT 0"
				+ ");");
		db.execSQL("INSERT INTO users "
				+ "(username, password, accountType)"
				+ " VALUES "
				+ "('admin', 'fd37ca5ca8763ae077a5e9740212319591603c42a08a60dcc91d12e7e457b024f6bdfdc10cdc1383e1602ff2092b4bc1bb8cac9306a9965eb352435f5dfe8bb0', 1);");
		db.execSQL("CREATE TABLE accounts ("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "username TEXT NOT NULL,"
				+ "fullName TEXT NOT NULL,"
				+ "displayName TEXT NOT NULL,"
				+ "balance TEXT NOT NULL DEFAULT 0,"
				+ "monthlyInterest TEXT NOT NULL DEFAULT 0"
				+ ");");
		db.execSQL("CREATE TABLE transactions ("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "account INTEGER NOT NULL,"
				+ "addedTimestamp INTEGER NOT NULL,"
				+ "effectiveTimestamp INTEGER NOT NULL,"
				+ "type INTEGER NOT NULL,"
				+ "amount TEXT NOT NULL,"
				+ "category TEXT NOT NULL,"
				+ "reason TEXT"
				+ ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("UPGRADING THE DATABASE");
		db.execSQL("DROP TABLE IF EXISTS users");
		db.execSQL("DROP TABLE IF EXISTS accounts");
		db.execSQL("DROP TABLE IF EXISTS transactions");
		onCreate(db);
	}

}
