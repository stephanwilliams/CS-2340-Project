package com.team19.cs2340.user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.team19.cs2340.DatabaseHelper;

public class LocalUserAccountService implements IUserAccountService {
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	
	public LocalUserAccountService(Context context) {
		this.dbHelper = new DatabaseHelper(context);
		this.db = dbHelper.getWritableDatabase();
	}
	
	@Override
	public IUser authenticateUser(String username, String password) {
		IUser user = getUser(username);
		if (user == null) return null;
		String passwordHash = hashPassword(password);
		if (user.getPasswordHash().equals(passwordHash)) {
			return user;
		}
		return null;
	}
	
	@Override
	public boolean userExists(String username){
		Cursor cursor = db.rawQuery("SELECT username" +
									" FROM users" +
									" WHERE username = ?",
									new String [] { username });
		return cursor.moveToFirst();
	}
	
	@Override
	public boolean createUser(String username, String password){
		db.execSQL("INSERT INTO users "
				+ "(username, password)"
				+ " VALUES "
				+ "(?, ?);",
				new String[] { username, hashPassword(password) });
		return true;
	}
	
	private IUser getUser(String username) {
		Cursor cursor = db.query("users",
								 new String[] { "username", "password", "accountType" },
								 "username = ?",
								 new String[] { username },
								 null,
								 null,
								 null);
		if (cursor.moveToFirst()) {
			IUser user = new User (cursor.getString(0),
								   cursor.getString(1),
								   IUser.AccountType.values()[cursor.getInt(2)]);
			return user;
		} else {
			return null;
		}
	}
	
	private String hashPassword(String password) {
		String hashString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] pwd = password.getBytes("UTF-8");
			md.update(pwd);
			byte[] hash = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : hash) sb.append(String.format("%02x", b));
			hashString = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hashString;
	}
}
