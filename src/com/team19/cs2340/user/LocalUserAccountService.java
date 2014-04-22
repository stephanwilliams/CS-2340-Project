package com.team19.cs2340.user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.team19.cs2340.DatabaseHelper;

/**
 * Concrete implementation of IUserAccountService that uses a local SQLite
 * database for storage.
 *
 */
class LocalUserAccountService implements IUserAccountService {
    /**
     * The database helper used to interact with the database.
     */
    private DatabaseHelper dbHelper;
    /**
     * Reference to the application's database.
     */
    private SQLiteDatabase db;

    /**
     * Instantiates a LocalUserAccountService with the given context.
     * 
     * @param context a context
     */
    public LocalUserAccountService(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.team19.cs2340.user.IUserAccountService#authenticateUser(java.lang
     * .String, java.lang.String)
     */
    @Override
    public IUser authenticateUser(String username, String password)
        throws UserAccountException {
        IUser user = getUser(username);
        if (user == null) {
            return null;
        }
        String passwordHash = hashPassword(password);
        if (user.getPasswordHash().equals(passwordHash)) {
            return user;
        } else {
            throw new UserAccountException("Invalid password");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.team19.cs2340.user.IUserAccountService#userExists(java.lang.String)
     */
    @Override
    public boolean userExists(String username) {
        Cursor cursor = db.query("users", new String[] {"username"},
                "username = ?", new String[] {username}, null, null, null);
        return cursor.moveToFirst();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.team19.cs2340.user.IUserAccountService#createUser(java.lang.String,
     * java.lang.String)
     */
    @Override
    public IUser createUser(String username, String password)
        throws UserAccountException {
        if (userExists(username)) {
            throw new UserAccountException("Username already exists");
        }
        if (!(username.length() > 0)) {
            throw new UserAccountException("No username specified!");
        }
        if (!(password.length() > 0)) {
            throw new UserAccountException("No password specified!");
        }

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

    /**
     * This function finds and returns a User object.
     * 
     * @param username
     *            the username of the requested user
     * @return the requested IUser object
     * @throws UserAccountException if an error occurred retrieving the user
     */
    private IUser getUser(String username) throws UserAccountException {
        Cursor cursor = db.query("users", new String[] {"username",
            "password", "accountType"}, "username = ?",
            new String[] {username}, null, null, null);
        if (cursor.moveToFirst()) {
            IUser user = new User(cursor.getString(0), cursor.getString(1),
                    IUser.AccountType.values()[cursor.getInt(2)]);
            return user;
        } else {
            throw new UserAccountException("User does not exist");
        }
    }

    /**
     * This function hashes and returns a password with SHA-512.
     * 
     * @param password
     *            the password to be hashed
     * @return hashed password (String object)
     */
    private String hashPassword(String password) {
        String hashString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] pwd = password.getBytes("UTF-8");
            md.update(pwd);
            byte[] hash = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            hashString = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hashString;
    }

    @Override
    public List<IUser> getUsers(IUser admin) throws UserAccountException {
        if (admin.getAccountType() != IUser.AccountType.ADMIN)
            throw new UserAccountException("Insufficent privilege to list users");
        Cursor cursor = db.query("users",
                                 new String[] {"username"},
                                 null, null, null, null, null);
        List<IUser> users = new ArrayList<IUser>();
        while (cursor.moveToNext()) {
            users.add(getUser(cursor.getString(0)));
        }
        return users;
    }

    @Override
    public IUser changePassword(IUser admin, IUser user, String newPassword) throws UserAccountException {
        if (admin.getAccountType() != IUser.AccountType.ADMIN) {
            throw new UserAccountException("Insufficient privilege to change user password");
        }
        if (!(newPassword.length() > 0)) {
            throw new UserAccountException("No password specified");
        }

        String passwordHash = hashPassword(newPassword);

        ContentValues cv = new ContentValues();
        cv.put("password", passwordHash);
        db.update("users", cv, "username = ?", new String[] {user.getUsername()});

        return getUser(user.getUsername());
    }
}
