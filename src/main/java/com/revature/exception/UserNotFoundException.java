package com.revature.exception;

public class UserNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
