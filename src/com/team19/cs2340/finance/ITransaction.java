package com.team19.cs2340.finance;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The interface class for transaction handling.
 * 
 */
public interface ITransaction extends Serializable {

    /**
     * Possible types of transactions.
     * 
     */
    public static enum TransactionType {
        /**
         * Denotes a deposit.
         */
        DEPOSIT,
        /**
         * Denotes a withdrawal.
         */
        WITHDRAWAL
    }

    /**
     * @return get the timestamp in which the transaction was added (long)
     */
    long getAddedTimestamp();

    /**
     * @return get the timestamp in which the transaction was put into effect
     *         (long)
     */
    long getEffectiveTimestamp();

    /**
     * @return get the TransactionType object of the transaction
     */
    TransactionType getType();

    /**
     * @return get the category of the transaction (String)
     */
    String getCategory();

    /**
     * @return get the monetary of the transaction (BigDecimal)
     */
    BigDecimal getAmount();

    /**
     * @return get the reason behind the transaction (String)
     */
    String getReason();
}
