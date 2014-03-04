package com.team19.cs2340.finance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.team19.cs2340.DatabaseHelper;
import com.team19.cs2340.finance.ITransaction.TransactionType;
import com.team19.cs2340.user.IUser;

class LocalFinanceDataService implements IFinanceDataService{
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	
	public LocalFinanceDataService(Context context) {
		this.dbHelper = new DatabaseHelper(context);
		this.db = dbHelper.getWritableDatabase();
	}
	
	@Override
	public IAccount createAccount(IUser user, String fullName, String displayName,
			BigDecimal balance, BigDecimal monthlyInterest) throws FinanceDataException {
		ContentValues cv = new ContentValues();
		cv.put("username", user.getUsername());
		cv.put("fullName", fullName);
		cv.put("displayName", displayName);	
		cv.put("balance", balance.toString());
		cv.put("monthlyInterest", monthlyInterest.toString());

		long id = db.insert("accounts", null, cv);

		if (id == -1) {
			throw new FinanceDataException("Account creation failed");
		} else {
			return getAccount(user, id);
		}
	}
	
	@Override
	public ITransaction createTransaction(IAccount account, long addedDate,
			TransactionType type, String category, BigDecimal amount)
			throws FinanceDataException {
		
		ContentValues cv = new ContentValues();
		cv.put("effectiveDate", addedDate);
		cv.put("type", type.ordinal());
		cv.put("category", category);
		cv.put("amount", amount.toString());
		
		return null;
	}

	

	
	@Override
	public IAccount getAccount(IUser user, long accountId) throws FinanceDataException {
		Cursor cursor =
			db.query("accounts",
					 new String[] {
						"_id", "username", "fullName", "displayName",
						"balance", "monthlyInterest" },
					 "_id = ? AND username = ?",
					 new String[] { Long.toString(accountId),
									user.getUsername() },
					 null,
					 null,
					 null);
		if (!cursor.moveToFirst()) {
			throw new FinanceDataException("Account does not exist");
		} else {
			if (!cursor.getString(1).equals(user.getUsername())) {
				throw new FinanceDataException("Account belongs to different user");
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

	@Override
	public List<IAccount> getAccounts(IUser user) throws FinanceDataException {
		Cursor cursor =
				db.query("accounts",
						 new String[] { "_id" },
						 "username = ?",
						 new String[] { user.getUsername() },
						 null,
						 null,
						 null);
		List<IAccount> accounts = new ArrayList<IAccount>();
		while (cursor.moveToNext()) {
			accounts.add(getAccount(user, cursor.getLong(0)));
		}
		return accounts;
	}


	@Override
	public List<ITransaction> getTransactions(IAccount account)
			throws FinanceDataException {
		Cursor cursor = 
				db.query("transactions",
						new String[] { "_id" },
						"account = ?",
						new String[] { Long.toString(account.getAccountId()) },
						null,
						null,
						null);
		List<ITransaction> transactions = new ArrayList<ITransaction>();
		while(cursor.moveToNext()){
			transactions.add(getTransaction(account, cursor.getLong(0)));
		}
		return transactions;
	}
	
	private ITransaction getTransaction(IAccount account, long transactionId) throws FinanceDataException {
		Cursor cursor =
				db.query("transactions",
						new String[] {
							"_id", "addedTimestamp", "effectiveTimestamp",
							"type", "amount", "category" },
						"_id = ? AND account = ?",
						new String[] { Long.toString(transactionId),
									   Long.toString(account.getAccountId()) },
						null,
						null,
						null);
		if (!cursor.moveToFirst()) {
			throw new FinanceDataException("Transaction does not exist");
		} else {
			if (!(cursor.getLong(1) == account.getAccountId())) {
				throw new FinanceDataException("Transaction does not belong to account");
			} else {
				ITransaction transaction = new Transaction(
						cursor.getLong(2),
						cursor.getLong(3),
						ITransaction.TransactionType.values()[cursor.getInt(4)],
						new BigDecimal(cursor.getString(5)),
						cursor.getString(6));
				return transaction;
			}
		}
	}
}
