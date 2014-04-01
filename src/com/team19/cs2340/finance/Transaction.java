package com.team19.cs2340.finance;

import java.math.BigDecimal;

/**
 * This class is an concrete implementation of the Transaction interface.
 *
 */
class Transaction implements ITransaction {
	/**
	 * the UID of the serial version.
	 */
	private static final long serialVersionUID = 6256312418840575109L;
	/**
	 * the timestamp in which the transaction was added.
	 */
	private long addedTimestamp;
	/**
	 * the timestamp in which the transaction was put into effect. 
	 */
	private long effectiveTimestamp;
	/**
	 * the category of the transaction.
	 */
	private String category;
	/**
	 * the monetary amount of the transaction.
	 */
	private BigDecimal amount;
	/**
	 * the type of the transaction.
	 */
	private TransactionType type;
	/**
	 * the reason behind the transaction.
	 */
	private String reason;
	
	/**
	 * Creates a transaction object.
	 * 
	 * @param addedTimestamp					the timestamp in which the transaction was added
	 * @param effectiveTimestamp				the timestamp in which the transaction was put into effect 
	 * @param type								the type of the transaction
	 * @param amount							the monetary amount of the transaction
	 * @param category							the category of the transaction
	 * @param reason							the reason behind the transaction
	 */
	public Transaction(long addedTimestamp, long effectiveTimestamp,
			TransactionType type, BigDecimal amount, String category, String reason) {
		this.addedTimestamp = addedTimestamp;
		this.effectiveTimestamp = effectiveTimestamp;
		this.type = type;
		this.amount = amount;
		this.category = category;
		this.reason = reason;
	}
	
	/* (non-Javadoc)
	 * @see com.team19.cs2340.finance.ITransaction#getAddedTimestamp()
	 */
	@Override
	public long getAddedTimestamp() {
		return addedTimestamp;
	}
	/**
	 * @param addedTimestamp				 	the timestamp in which the transaction was added
	 */
	public void setAddedTimestamp(long addedTimestamp) {
		this.addedTimestamp = addedTimestamp;
	}
	
	/* (non-Javadoc)
	 * @see com.team19.cs2340.finance.ITransaction#getEffectiveTimestamp()
	 */
	@Override
	public long getEffectiveTimestamp() {
		return effectiveTimestamp;
	}
	/**
	 * @param effectiveTimestamp				the timestamp in which the transaction was put into effect 
	 */
	public void setEffectiveTimestamp(long effectiveTimestamp) {
		this.effectiveTimestamp = effectiveTimestamp;
	}
	
	/* (non-Javadoc)
	 * @see com.team19.cs2340.finance.ITransaction#getCategory()
	 */
	@Override
	public String getCategory() {
		return category;
	}
	/**
	 * @param category							the category of the transaction
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/* (non-Javadoc)
	 * @see com.team19.cs2340.finance.ITransaction#getReason()
	 */
	@Override
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason							the reason behind the transaction
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	/* (non-Javadoc)
	 * @see com.team19.cs2340.finance.ITransaction#getAmount()
	 */
	@Override
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * @param amount							the monetary amount of the transaction
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/* (non-Javadoc)
	 * @see com.team19.cs2340.finance.ITransaction#getType()
	 */
	@Override
	public TransactionType getType() {
		return type;
	}
	
	/**
	 * @param type								the type of the transaction
	 */
	public void setType(TransactionType type) {
		this.type = type;
	}
	
}
