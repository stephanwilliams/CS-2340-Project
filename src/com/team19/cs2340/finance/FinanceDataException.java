package com.team19.cs2340.finance;

/**
 * Exception class for finance data service.
 */
public class FinanceDataException extends Exception {

    /**
     * The UID of the serial version.
     */
    private static final long serialVersionUID = -3189836613166128874L;

    /**
     * Sends String message of what the exception was.
     * 
     * @param message
     *            message of the exception (String)
     */
    public FinanceDataException(String message) {
        super(message);
    }

}
