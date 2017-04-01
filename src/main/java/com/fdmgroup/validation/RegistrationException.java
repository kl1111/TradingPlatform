package com.fdmgroup.validation;

public class RegistrationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

public String getMessage() {
	return message;
}

public RegistrationException(){};

public RegistrationException(String message){
	this.message = message;
}

}
