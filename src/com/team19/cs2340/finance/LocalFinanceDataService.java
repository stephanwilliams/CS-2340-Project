package com.team19.cs2340.finance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.team19.cs2340.DatabaseHelper;
import com.team19.cs2340.finance.ITransaction.TransactionType;
import com.team19.cs2340.user.IUser;

/**
 * An implementation of the IFinanceDataService that uses a local SQLite
 * database for storage.
 * 
 */
class LocalFinanceDataService implements IFinanceDataService {

    /**
     * Instatiation of the database class.
     */
    private DatabaseHelper dbHelper;
    /**
     * Instatiation of the SQLiteDatabase class.
     */
    private SQLiteDatabase db;

    /**
     * Creates a LocalFinanceDataService object.
     * 
     * @param context
     *            A context
     */
    public LocalFinanceDataService(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.team19.cs2340.finance.IFinanceDataService#createAccount(com.team19
     * .cs2340.user.IUser, java.lang.String, java.lang.String,
     * java.math.BigDecimal, java.math.BigDecimal)
     */
    @Override
    public IAccount createAccount(IUser user, String fullName,
            String displayName, BigDecimal balance, BigDecimal monthlyInterest)
        throws FinanceDataException {
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.team19.cs2340.finance.IFinanceDataService#createTransaction(com.team19
     * .cs2340.finance.IAccount, long,
     * com.team19.cs2340.finance.ITransaction.TransactionType, java.lang.String,
     * java.math.BigDecimal, java.lang.String)
     */
    @Override
    public ITransaction createTransaction(IAccount account,
            long effectiveTimestamp, TransactionType type, String category,
            BigDecimal amount, String reason) throws FinanceDataException {

        ContentValues cv = new ContentValues();
        cv.put("account", account.getAccountId());
        cv.put("addedTimestamp", System.currentTimeMillis());
        cv.put("effectiveTimestamp", effectiveTimestamp);
        cv.put("type", type.ordinal());
        cv.put("category", category);
        cv.put("amount", amount.toString());
        cv.put("reason", reason);

        long id = db.insert("transactions", null, cv);
        if (id == -1) {
            throw new FinanceDataException("Transaction creation failed");
        } else {
            return getTransaction(account, id);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.team19.cs2340.finance.IFinanceDataService#getAccount(com.team19.cs2340
     * .user.IUser, long)
     */
    @Override
    public IAccount getAccount(IUser user, long accountId)
        throws FinanceDataException {
        if (user == null) {
            throw new FinanceDataException("User must not be null");
        }
        Cursor cursor = db.query("accounts", new String[] {"_id", "username",
            "fullName", "displayName", "balance", "monthlyInterest"},
            "_id = ?", new String[] {Long.toString(accountId)}, null,
            null, null);
        if (!cursor.moveToFirst()) {
            throw new FinanceDataException("Account does not exist");
        } else {
            if (cursor.getString(1).equals(user.getUsername())) {
                IAccount account = new Account(cursor.getLong(0),
                        cursor.getString(2), cursor.getString(3),
                        new BigDecimal(cursor.getString(4)), new BigDecimal(
                                cursor.getString(5)));
                return account;
            } else {

                throw new FinanceDataException(
                        "Account belongs to different user");

            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.team19.cs2340.finance.IFinanceDataService#getAccounts(com.team19.
     * cs2340.user.IUser)
     */
    @Override
    public List<IAccount> getAccounts(IUser user) throws FinanceDataException {
        Cursor cursor = db.query("accounts", new String[] {"_id"},
                "username = ?", new String[] {user.getUsername()}, null,
                null, null);
        List<IAccount> accounts = new ArrayList<IAccount>();
        while (cursor.moveToNext()) {
            accounts.add(getAccount(user, cursor.getLong(0)));
        }
        return accounts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.team19.cs2340.finance.IFinanceDataService#getTransactions(com.team19
     * .cs2340.finance.IAccount)
     */
    @Override
    public List<ITransaction> getTransactions(IAccount account)
        throws FinanceDataException {
        if (account == null) {
            throw new FinanceDataException("Account is null");
        }
        Cursor cursor = db.query("transactions", new String[] {"_id"},
                "account = ?",
                new String[] {Long.toString(account.getAccountId())}, null,
                null, null);
        List<ITransaction> transactions = new ArrayList<ITransaction>();
        while (cursor.moveToNext()) {
            transactions.add(getTransaction(account, cursor.getLong(0)));
        }
        return transactions;
    }

    /**
     * This function finds and returns a transaction object.
     * 
     * @param account
     *            the account which the transaction belongs to
     * @param transactionId
     *            the ID of the transaction
     * @return the requested ITransaction object
     * @throws FinanceDataException if an error occurred retrieving the transaction
     */
    private ITransaction getTransaction(IAccount account, long transactionId)
        throws FinanceDataException {
        Cursor cursor = db.query(
                "transactions",
                new String[] {"_id", "account", "addedTimestamp",
                    "effectiveTimestamp", "type", "amount", "category",
                    "reason"},
                "_id = ? AND account = ?",
                new String[] {Long.toString(transactionId),
                        Long.toString(account.getAccountId())}, null, null,
                null);
        if (!cursor.moveToFirst()) {
            throw new FinanceDataException("Transaction does not exist");
        } else {
            if ((cursor.getLong(1) == account.getAccountId())) {
                ITransaction transaction = new Transaction(
                        cursor.getLong(2),
                        cursor.getLong(3),
                        ITransaction.TransactionType.values()[cursor.getInt(4)],
                        new BigDecimal(cursor.getString(5)), cursor
                                .getString(6), cursor.getString(7));
                return transaction;
            } else {

                throw new FinanceDataException(
                        "Transaction does not belong to account");

            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.team19.cs2340.finance.IFinanceDataService#getCategorySpendingReport
     * (com.team19.cs2340.user.IUser, long, long)
     */
    @Override
    public Map<String, BigDecimal> getCategorySpendingReport(IUser user,
            long startTimestamp, long endTimestamp) {
        Cursor cursor = db
                .rawQuery(
                        "SELECT transactions.category, sum(transactions.amount) "
                                + "FROM transactions "
                                + "JOIN accounts ON transactions.account = accounts._id "
                                + "WHERE accounts.username = ? "
                                + "AND transactions.effectiveTimestamp >= ? "
                                + "AND transactions.effectiveTimestamp <= ? "
                                + "GROUP BY transactions.category",
                        new String[] {user.getUsername(),
                                Long.toString(startTimestamp),
                                Long.toString(endTimestamp)});
        Map<String, BigDecimal> categorySpending = new HashMap<String, BigDecimal>();
        while (cursor.moveToNext()) {
            categorySpending.put(cursor.getString(0),
                    new BigDecimal(cursor.getString(1)));
        }
        return categorySpending;
    }

    @Override
    public Map<String, BigDecimal> getIncomeSourceReport(IUser user,
            long startTimestamp, long endTimestamp) {
        Cursor cursor = db
                .rawQuery(
                        "SELECT transactions.category, sum(transactions.amount) "
                                + "FROM transactions "
                                + "JOIN accounts ON transactions.account = accounts._id "
                                + "WHERE accounts.username = ? "
                                + "AND transactions.effectiveTimestamp >= ? "
                                + "AND transactions.effectiveTimestamp <= ? "
                                + "AND transactions.type = ? "
                                + "GROUP BY transactions.category",
                        new String[] {user.getUsername(),
                                Long.toString(startTimestamp),
                                Long.toString(endTimestamp),
                                Integer.toString(ITransaction.TransactionType.DEPOSIT.ordinal())
                                });
        Map<String, BigDecimal> incomeSource = new HashMap<String, BigDecimal>();
        while (cursor.moveToNext()) {
            incomeSource.put(cursor.getString(0), new BigDecimal(cursor.getString(1)));
        }
        return incomeSource;
    }

    @Override
    public Map<String, BigDecimal> getCashFlowReport(IUser user,
            long startTimestamp, long endTimestamp) {
        Cursor cursor = db
                .rawQuery(
                        "SELECT transactions.type, sum(transactions.amount) "
                                + "FROM transactions "
                                + "JOIN accounts ON transactions.account = accounts._id "
                                + "WHERE accounts.username = ? "
                                + "AND transactions.effectiveTimestamp >= ? "
                                + "AND transactions.effectiveTimestamp <= ? "
                                + "GROUP BY transactions.type",
                        new String[] {user.getUsername(),
                                Long.toString(startTimestamp),
                                Long.toString(endTimestamp)
                                });
        Map<String, BigDecimal> cashFlow = new HashMap<String, BigDecimal>();
        cashFlow.put("Income", BigDecimal.ZERO);
        cashFlow.put("Expenses", BigDecimal.ZERO);
        while (cursor.moveToNext()) {
            switch (ITransaction.TransactionType.values()[cursor.getInt(0)]) {
            case DEPOSIT:
                cashFlow.put("Income", new BigDecimal(cursor.getString(1)));
                break;
            case WITHDRAWAL:
                cashFlow.put("Expenses", new BigDecimal(cursor.getString(1)));
                break;
            }
        }
        return cashFlow;
    }
}
