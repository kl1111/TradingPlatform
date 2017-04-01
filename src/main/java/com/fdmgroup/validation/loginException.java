package com.fdmgroup.validation;

public class loginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1334923572823379771L;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public loginException(){};

	public loginException(String message){
		this.message = message;
	}

}
