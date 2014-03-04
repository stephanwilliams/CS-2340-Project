package com.team19.cs2340.finance;

import java.io.Serializable;
import java.math.BigDecimal;

public interface ITransaction extends Serializable {
	
	public static enum TransactionType {
		DEPOSIT,
		WITHDRAWAL
	}
	
	
	public long getAddedTimestamp();
	public long getEffectiveTimestamp();
	public TransactionType getType();
	public String getCategory();
	public BigDecimal getAmount();
	public String getReason();
}

