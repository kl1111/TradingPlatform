package com.fdmgroup.model;

import javax.persistence.*;

/**
* This is an entity class for Wallet objects.
*
*/

@Entity(name="wallets")
public class Wallet {

	@Id
	@Column(name="user_id") // attributes
	private int user_id;
	@Column(name="money")
	private double money;
	
	public Wallet() {} // constructors
	
	public Wallet(int user_id, double money) {
		this.setUser_id(user_id);
		this.setMoney(money);
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

}
