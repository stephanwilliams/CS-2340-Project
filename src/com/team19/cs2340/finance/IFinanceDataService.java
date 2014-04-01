package com.team19.cs2340.finance;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.team19.cs2340.finance.ITransaction.TransactionType;
import com.team19.cs2340.user.IUser;

public interface IFinanceDataService {
	/**
	 * This function creates, stores, and returns an account object.
	 * 
	 * @param user
	 * @param fullName
	 * @param displayName
	 * @param balance
	 * @param monthlyInterest
	 * @return
	 * @throws FinanceDataException
	 */
	public IAccount createAccount(IUser user, String fullName, String displayName,
			BigDecimal balance, BigDecimal monthlyInterest) throws FinanceDataException;
	/**
	 * This function creates, stores, and returns a transaction object.
	 * 
	 * @param account
	 * @param addedDate
	 * @param type
	 * @param category
	 * @param amount
	 * @param reason
	 * @return
	 * @throws FinanceDataException
	 */
	public ITransaction createTransaction(IAccount account, long addedDate, TransactionType type,
			String category, BigDecimal amount, String reason) throws FinanceDataException;
	
	/**
	 * This function finds and returns an account object.
	 * 
	 * @param user
	 * @param accountId
	 * @return
	 * @throws FinanceDataException
	 */
	public IAccount getAccount(IUser user, long accountId) throws FinanceDataException;
	/**
	 * This function finds accounts, populates a list with the accounts, and returns the list. 
	 * 
	 * @param user
	 * @return
	 * @throws FinanceDataException
	 */
	public List<IAccount> getAccounts(IUser user) throws FinanceDataException;
	/**
	 * This function finds transaction, populates a list with the transactions, and returns the list.
	 * 
	 * @param account
	 * @return
	 * @throws FinanceDataException
	 */
	public List<ITransaction> getTransactions(IAccount account) throws FinanceDataException;
	/**
	 * This function finds Category Spending Reports, populations a map with the reports, and returns the map.
	 * 
	 * @param user
	 * @param startTimestamp
	 * @param endTimestamp
	 * @return
	 */
	public Map<String, BigDecimal> getCategorySpendingReport(IUser user, long startTimestamp, long endTimestamp);
	
}
