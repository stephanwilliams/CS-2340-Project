package com.team19.cs2340.finance;

import java.io.Serializable;
import java.math.BigDecimal;

public interface IAccount extends Serializable {
	/**
	 * @return		the ID of the account requested (long)
	 */
	public long getAccountId();
	/**
	 * @return		the full name of the account holder (String)
	 */
	public String getFullName();
	/**
	 * @return		the display name of the account (String)
	 */
	public String getDisplayName();
	/**
	 * @return		the balance of the account (BigDecimal)
	 */
	public BigDecimal getBalance();
	/**
	 * @return		the monthly interest of the account (BigDecimal)
	 */
	public BigDecimal getMonthlyInterest();
}
