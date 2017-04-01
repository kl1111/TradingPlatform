package com.fdmgroup.validation;

public class InvalidCompanyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6067900656832226818L;

private String message;

public String getMessage() {
	return message;
}

public InvalidCompanyException(){};

public InvalidCompanyException(String message){
	this.message = message;
}
	
}
