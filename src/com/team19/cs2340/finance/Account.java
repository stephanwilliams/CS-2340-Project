package com.team19.cs2340.finance;

import java.math.BigDecimal;

/**
 * @author ptolemy
 *
 */
public class Account implements IAccount {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4048567527276603206L;
	/**
	 * 
	 */
	private long accountId;
	/**
	 * 
	 */
	private String fullName;
	/**
	 * 
	 */
	private String displayName;
	/**
	 * 
	 */
	private BigDecimal balance;
	/**
	 * 
	 */
	private BigDecimal monthlyInterest;
	

	/**
	 * @param accountId
	 * @param fullName
	 * @param displayName
	 * @param balance
	 * @param monthlyInterest
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
