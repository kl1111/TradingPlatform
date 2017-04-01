package com.fdmgroup.model;

import javax.persistence.*;

/**
* This is an entity class for Share objects.
*
*/

@Entity(name="shares")
public class Share {
	
	@Id
	@SequenceGenerator(name="share_id", sequenceName="share_id_seq", allocationSize=1, initialValue=0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="share_id")
	@Column(name="share_id") 
	private int share_id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)	
	@JoinColumn(name="company_id")
	private Company company;
	
	@ManyToOne(cascade = CascadeType.PERSIST)	
	@JoinColumn(name="currency_id")
	private Currency currency;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="stock_ex_id")
	private StockExchange stockExchange;
	
	public Share() {}
	
	public Share(Company company, Currency currency, StockExchange stockExchange) {
		this.company = company;
		this.currency = currency;
		this.stockExchange = stockExchange;
	}
	
	public int getId() {
		return share_id;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public StockExchange getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(StockExchange stockExchange) {
		this.stockExchange = stockExchange;
	}

	public int getShare_id() {
		return share_id;
	}

	public void setShare_id(int share_id) {
		this.share_id = share_id;
	}
	
}
