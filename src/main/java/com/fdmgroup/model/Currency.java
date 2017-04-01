package com.fdmgroup.model;

import javax.persistence.*;

/**
* This is an entity class for Currency objects.
*
*/

@Entity(name = "currencies")
public class Currency {

	@Id
	@SequenceGenerator(name = "currency_id", sequenceName = "currency_id_seq", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_id")
	private int currency_id;

	@Column(name = "symbol")
	private String symbol;

	@Column(name = "name")
	private String name;

	public Currency() {
	}

	public Currency( String symbol, String name) {
		this.symbol = symbol;
		this.name = name;
	}

	public int getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(int currency_id) {
		this.currency_id = currency_id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol_id(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
