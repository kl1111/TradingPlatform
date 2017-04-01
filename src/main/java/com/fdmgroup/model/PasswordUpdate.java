package com.fdmgroup.model;

/**
* This class handles the case that a user changes his password.
*
*/

public class PasswordUpdate {
	private String passwordold;
	private String confirm;
	private String passwordnew;
	
	public PasswordUpdate(){
		
	}
	
	public PasswordUpdate(String passwordold, String passwordnew, String confirm){
		this.passwordnew = passwordnew;
		this.passwordold = passwordold;
		this.confirm = confirm;
	}

	public String getPasswordold() {
		return passwordold;
	}

	public void setPasswordold(String passwordold) {
		this.passwordold = passwordold;
	}

	public String getPasswordnew() {
		return passwordnew;
	}

	public void setPasswordnew(String passwordnew) {
		this.passwordnew = passwordnew;
	}
	
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
}
