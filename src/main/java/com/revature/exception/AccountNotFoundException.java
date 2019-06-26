package com.revature.exception;

public class AccountNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
