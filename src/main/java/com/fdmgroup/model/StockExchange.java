package com.fdmgroup.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
* This is an entity class for Stock Exchange objects.
*
*/

@Entity(name = "Stock_Exchanges")
public class StockExchange {
	@Id
	@SequenceGenerator(name="stock_ex_id", sequenceName="stock_ex_id_seq", allocationSize=1, initialValue=0)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="stock_ex_id")
	@Column(name = "stock_ex_id")
	private int stock_ex_id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "symbol")
	private String symbol;
	
	@ManyToOne(cascade = CascadeType.PERSIST)	
	@JoinColumn(name = "place_id")
	private Place place_id;
	
	public StockExchange (){}
	
	public StockExchange(String name, String symbol, Place place_id){
		this.name = name;
		this.symbol = symbol;
		this.place_id = place_id;
	}

	public int getStock_ex_id() {
		return stock_ex_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Place getPlaceID() {
		return place_id;
	}
	public void setStock_ex_id(int stock_ex_id) {
		this.stock_ex_id=stock_ex_id;
	}
	
	
}
