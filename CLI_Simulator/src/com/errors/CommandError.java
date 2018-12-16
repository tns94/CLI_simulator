package com.errors;
/*
 * Custom exception for error produced
 */
public class CommandError extends Exception{

	public CommandError() {
		super();
		
	}

	public CommandError(String message) {
		super(message);
		
	}

	@Override
	public String getMessage() {
		
		return super.getMessage();
	}

}
