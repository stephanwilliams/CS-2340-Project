package com.team19.cs2340.finance;

import java.math.BigDecimal;

public class Account implements IAccount {
	private long accountId;
	private String fullName;
	private String displayName;
	private BigDecimal balance;
	private BigDecimal monthlyInterest;
	

	public Account(long accountId, String fullName, String displayName,
			BigDecimal balance, BigDecimal monthlyInterest) {
		this.accountId = accountId;
		this.fullName = fullName;
		this.displayName = displayName;
		this.balance = balance;
		this.monthlyInterest = monthlyInterest;
	}

	@Override
	public long getAccountId() {
		return accountId;
	}

	@Override
	public String getFullName() {
		return fullName;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public BigDecimal getBalance() {
		return balance;
	}

	@Override
	public BigDecimal getMonthlyInterest() {
		return monthlyInterest;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public void setMonthlyInterest(BigDecimal monthlyInterest) {
		this.monthlyInterest = monthlyInterest;
	}
}
