package com.team19.cs2340.finance;

import java.io.Serializable;
import java.math.BigDecimal;

public interface ITransaction extends Serializable {
	
	public static enum TransactionType {
		DEPOSIT,
		WITHDRAWAL
	}
	
	
	/**
	 * @return		get the timestamp in which the transaction was added (long)
	 */
	public long getAddedTimestamp();
	/**
	 * @return		get the timestamp in which the transaction was put into effect (long)
	 */
	public long getEffectiveTimestamp();
	/**
	 * @return		get the TransactionType object of the transaction
	 */
	public TransactionType getType();
	/**
	 * @return		get the category of the transaction (String)
	 */
	public String getCategory();
	/**
	 * @return		get the monetary of the transaction (BigDecimal)
	 */
	public BigDecimal getAmount();
	/**
	 * @return		get the reason behind the transaction (String)
	 */
	public String getReason();
}

