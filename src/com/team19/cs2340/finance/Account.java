package com.team19.cs2340.finance;

import java.math.BigDecimal;

/**
 * This class is an concrete implementation of the IAccount interface
 */
public class Account implements IAccount {
	/**
	 * The UID of the serial version
	 */
	private static final long serialVersionUID = 4048567527276603206L;
	/**
	 * The id of the account
	 */
	private long accountId;
	/**
	 * The full name of the user who owns the account
	 */
	private String fullName;
	/**
	 * The display name of the account
	 */
	private String displayName;
	/**
	 * The balance of the account
	 */
	private BigDecimal balance;
	/**
	 * The monthly interest of the account
	 */
	private BigDecimal monthlyInterest;
	

	/**
	 * Creates an account object
	 * 
	 * @param accountId			the id of the account		
	 * @param fullName			the full name of the user who owns the account
	 * @param displayName		the display name of the account
	 * @param balance			the balance of the account
	 * @param monthlyInterest	the monthly interest of the account
	 */
	public Account(long accountId, String fullName, String displayName,
			BigDecimal balance, BigDecimal monthlyInterest) {
		this.accountId = accountId;
		this.fullName = fullName;
		this.displayName = displayName;
		this.balance = balance;
		this.monthlyInterest = monthlyInterest;
	}

	/* (non-Javadoc)
	 * @see com.team19.cs2340.finance.IAccount#getAccountId()
	 */
	@Override
	public long getAccountId() {
		return accountId;
	}

	/* (non-Javadoc)
	 * @see com.team19.cs2340.finance.IAccount#getFullName()
	 */
	@Override
	public String getFullName() {
		return fullName;
	}

	/* (non-Javadoc)
	 * @see com.team19.cs2340.finance.IAccount#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return displayName;
	}

	/* (non-Javadoc)
	 * @see com.team19.cs2340.finance.IAccount#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		return balance;
	}

	/* (non-Javadoc)
	 * @see com.team19.cs2340.finance.IAccount#getMonthlyInterest()
	 */
	@Override
	public BigDecimal getMonthlyInterest() {
		return monthlyInterest;
	}

	/**
	 * @param accountId			the ID to be set for the account
	 */
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @param fullName			the fullname of the user to be set for the account
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @param displayName		the display name to be set for the account 
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @param balance			the balance to be set for the account
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * @param monthlyInterest	the monthly interest to be est for the account
	 */
	public void setMonthlyInterest(BigDecimal monthlyInterest) {
		this.monthlyInterest = monthlyInterest;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return displayName;
	}
}
