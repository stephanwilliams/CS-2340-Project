package com.team19.cs2340.user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.team19.cs2340.DatabaseHelper;

class LocalUserAccountService implements IUserAccountService {
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	
	public LocalUserAccountService(Context context) {
		this.dbHelper = new DatabaseHelper(context);
		this.db = dbHelper.getWritableDatabase();
	}
	
	@Override
	public IUser authenticateUser(String username, String password) throws UserAccountException {
		IUser user = getUser(username);
		if (user == null) return null;
		String passwordHash = hashPassword(password);
		if (user.getPasswordHash().equals(passwordHash)) {
			return user;
		} else {
			throw new UserAccountException("Invalid password");
		}
	}
	
	@Override
	public boolean userExists(String username){
		Cursor cursor = db.query("users",
								 new String[] { "username" },
								 "username = ?",
								 new String[] { username },
								 null,
								 null,
								 null);
		return cursor.moveToFirst();
	}
	
	@Override
	public IUser createUser(String username, String password) throws UserAccountException {
		if (userExists(username)) throw new UserAccountException("Username already exists");
		if (!(username.length() > 0)) throw new UserAccountException("No username specified!");
		if (!(password.length() > 0)) throw new UserAccountException("No password specified!");
		

		ContentValues cv = new ContentValues();
		cv.put("username", username);
		cv.put("password", hashPassword(password));
		long id = db.insert("users", null, cv);
		if (id == -1) {
			throw new UserAccountException("Account creation failed");
		} else {
			return authenticateUser(username, password);
		}
	}
	
	private IUser getUser(String username) throws UserAccountException {
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
			throw new UserAccountException("User does not exist");
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
