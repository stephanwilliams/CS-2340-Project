package com.team19.cs2340.finance;

import java.math.BigDecimal;

class Transaction implements ITransaction {
	private static final long serialVersionUID = 6256312418840575109L;
	private long addedTimestamp;
	private long effectiveTimestamp;
	private String category;
	private BigDecimal amount;
	private TransactionType type;
	
	
	public Transaction(long addedTimestamp, long effectiveTimestamp,
			String category, BigDecimal amount) {
		this.addedTimestamp = addedTimestamp;
		this.effectiveTimestamp = effectiveTimestamp;
		this.category = category;
		this.amount = amount;
	}
	
	@Override
	public long getAddedTimestamp() {
		return addedTimestamp;
	}
	public void setAddedTimestamp(long addedTimestamp) {
		this.addedTimestamp = addedTimestamp;
	}
	
	@Override
	public long getEffectiveTimestamp() {
		return effectiveTimestamp;
	}
	public void setEffectiveTimestamp(long effectiveTimestamp) {
		this.effectiveTimestamp = effectiveTimestamp;
	}
	
	@Override
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public TransactionType getType() {
		return type;
	}
	
	public void setType(TransactionType type) {
		this.type = type;
	}
	
}
