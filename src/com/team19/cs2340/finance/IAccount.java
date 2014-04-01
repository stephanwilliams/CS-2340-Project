package com.team19.cs2340.finance;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The interface class for account handling.
 *
 */
public interface IAccount extends Serializable {
	/**
	 * Gets account id.
	 * 
	 * @return		the ID of the account requested (long)
	 */
	public long getAccountId();
	/**
	 * Gets full name.
	 * 
	 * @return		the full name of the account holder (String)
	 */
	public String getFullName();
	/**
	 * Gets display name.
	 * 
	 * @return		the display name of the account (String)
	 */
	public String getDisplayName();
	/**
	 * Gets balance of account.
	 * 
	 * @return		the balance of the account (BigDecimal)
	 */
	public BigDecimal getBalance();
	/**
	 * Gets monthly interest of the account.
	 * 
	 * @return		the monthly interest of the account (BigDecimal)
	 */
	public BigDecimal getMonthlyInterest();
}
