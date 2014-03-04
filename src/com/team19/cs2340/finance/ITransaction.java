package com.team19.cs2340.finance;

import java.math.BigDecimal;

public interface ITransaction {
	
	public static enum TransactionType {
		DEPOSIT,
		WITHDRAWAL
	}
	
	
	public long getAddedTimestamp();
	public long getEffectiveTimestamp();
	public TransactionType getType();
	public String getCategory();
	public BigDecimal getAmount();
}

