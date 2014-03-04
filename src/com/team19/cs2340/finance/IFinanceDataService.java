package com.team19.cs2340.finance;

import java.math.BigDecimal;
import java.util.List;

import com.team19.cs2340.finance.ITransaction.TransactionType;
import com.team19.cs2340.user.IUser;

public interface IFinanceDataService {
	public IAccount createAccount(IUser user, String fullName, String displayName,
			BigDecimal balance, BigDecimal monthlyInterest) throws FinanceDataException;
	public ITransaction createTransaction(IAccount account, long addedDate, TransactionType type,
			String category, BigDecimal amount) throws FinanceDataException;
	
	public IAccount getAccount(IUser user, long accountId) throws FinanceDataException;
	public List<IAccount> getAccounts(IUser user) throws FinanceDataException;
	public List<ITransaction> getTransactions(IAccount account) throws FinanceDataException;
	
	
}
