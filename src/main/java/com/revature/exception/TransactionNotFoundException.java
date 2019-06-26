package com.revature.exception;

public class TransactionNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TransactionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}