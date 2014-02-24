package com.team19.cs2340.finance;

import java.math.BigDecimal;

public interface IAccount {
	public long getAccountId();
	public String getFullName();
	public String getDisplayName();
	public BigDecimal getBalance();
	public BigDecimal getMonthlyInterest();
}
