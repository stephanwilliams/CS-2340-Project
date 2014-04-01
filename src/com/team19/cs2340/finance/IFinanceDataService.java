package com.team19.cs2340.finance;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.team19.cs2340.finance.ITransaction.TransactionType;
import com.team19.cs2340.user.IUser;

/**
 *	Interface for implementations of FinanceDataService.
 *
 */
public interface IFinanceDataService {
	/**
	 * This function creates, stores, and returns an account object.
	 * 
	 * @param user							the account holder
	 * @param fullName						the name of the account holder
	 * @param displayName					the displayed name of the account
	 * @param balance						the balance of the account
	 * @param monthlyInterest				the monthly interest of the account
	 * @return 								the created IAccount object
	 * @throws FinanceDataException
	 */
	public IAccount createAccount(IUser user, String fullName, String displayName,
			BigDecimal balance, BigDecimal monthlyInterest) throws FinanceDataException;
	/**
	 * This function creates, stores, and returns a transaction object.
	 * 
	 * @param account						the account the transaction belongs to
	 * @param addedDate						the date in which the transaction was created
	 * @param type							the type of transaction
	 * @param category						the category of the transaction
	 * @param amount						the monetary amount of the transaction
	 * @param reason						the reason behind the transaction
	 * @return 								the created ITransaction
	 * @throws FinanceDataException
	 */
	public ITransaction createTransaction(IAccount account, long addedDate, TransactionType type,
			String category, BigDecimal amount, String reason) throws FinanceDataException;
	
	/**
	 * This function finds and returns an account object.
	 * 
	 * @param user							the user the account belongs to
	 * @param accountId						the ID of the account
	 * @return 								the requested IAccount object	
	 * @throws FinanceDataException
	 */
	public IAccount getAccount(IUser user, long accountId) throws FinanceDataException;
	/**
	 * This function finds accounts, populates a list with the accounts, and returns the list. 
	 * 
	 * @param user							the user the list of accounts belong to
	 * @return 								the List populated with requested IAccount objects
	 * @throws FinanceDataException
	 */
	public List<IAccount> getAccounts(IUser user) throws FinanceDataException;
	/**
	 * This function finds transaction, populates a list with the transactions, and returns the list.
	 * 
	 * @param account						the account the transactions belong to
	 * @return 		 						the List populated with requested ITransaction objects
	 * @throws FinanceDataException
	 */
	public List<ITransaction> getTransactions(IAccount account) throws FinanceDataException;
	/**
	 * This function finds Category Spending Reports, populations a map with the reports, and returns the map.
	 * 
	 * @param user							the user that the report generation is for
	 * @param startTimestamp				the starting time for the spending report
	 * @param endTimestamp					the ending time of the spending report
	 * @return 								the Map populated with requested transactions (String array)
	 */
	public Map<String, BigDecimal> getCategorySpendingReport(IUser user, long startTimestamp, long endTimestamp);
	
}
