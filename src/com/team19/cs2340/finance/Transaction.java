package com.team19.cs2340.finance;

import java.math.BigDecimal;

public class Transaction {

	private long addedTimestamp;
	private long effectiveTimestamp;
	private String category;
	private BigDecimal amount;
	
	
	public Transaction(long addedTimestamp, long effectiveTimestamp,
			String category, BigDecimal amount) {
		this.addedTimestamp = addedTimestamp;
		this.effectiveTimestamp = effectiveTimestamp;
		this.category = category;
		this.amount = amount;
	}
	
	public long getAddedTimestamp() {
		return addedTimestamp;
	}
	public void setAddedTimestamp(long addedTimestamp) {
		this.addedTimestamp = addedTimestamp;
	}
	public long getEffectiveTimestamp() {
		return effectiveTimestamp;
	}
	public void setEffectiveTimestamp(long effectiveTimestamp) {
		this.effectiveTimestamp = effectiveTimestamp;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
