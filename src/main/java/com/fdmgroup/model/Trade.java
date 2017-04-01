package com.fdmgroup.model;

import java.sql.Date;

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
* This is an entity class for Trade objects.
*
*/

@Entity(name = "trades")
public class Trade {

	@Id
	@SequenceGenerator(name = "trades_id", sequenceName = "trades_id_seq", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trades_id")
	private int trade_id;

	@ManyToOne(cascade = CascadeType.PERSIST)	
	@JoinColumn(name = "share_id")
	private Share share;

	@ManyToOne(cascade = CascadeType.PERSIST)	
	@JoinColumn(name = "broker_id")
	private Broker broker;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(cascade = CascadeType.PERSIST)	
	@JoinColumn(name = "stock_ex_id")
	private StockExchange stockExchange;

	@Column(name = "transaction_time")
	private Date transaction_time;

	@Column(name = "share_amount")
	private int share_amount;

	@Column(name = "price_total")
	private double price_total;

	@Column(name = "action")
	private String action;

	public Trade() {

	}

	public Trade(Share share, Broker broker, User user, StockExchange stockExchange, Date transaction_time, int share_amount, double price_total, String action) {
		this.share = share;
		this.broker = broker;
		this.user = user;
		this.stockExchange = stockExchange;
		this.transaction_time = transaction_time;
		this.share_amount = share_amount;
		this.price_total = price_total;
		this.setAction(action);
	}

	public int getTrade_id() {
		return trade_id;
	}

	public void setTrade_id(int trade_id) {
		this.trade_id = trade_id;
	}

	public Share getShare_id() {
		return share;
	}

	public void setShare_id(Share share_id) {
		this.share = share_id;
	}

	public Broker getBroker_id() {
		return broker;
	}

	public void setBroker_id(Broker broker_id) {
		this.broker = broker_id;
	}

	public StockExchange getStock_ex_id() {
		return stockExchange;
	}

	public void setStock_ex_id(StockExchange stock_ex_id) {
		this.stockExchange = stock_ex_id;
	}

	public Date getTransaction_time() {
		return transaction_time;
	}

	public void setTransaction_time(Date transaction_time) {
		this.transaction_time = transaction_time;
	}

	public int getShare_amount() {
		return share_amount;
	}

	public void setShare_amount(int share_amount) {
		this.share_amount = share_amount;
	}

	public double getPrice_total() {
		return price_total;
	}

	public void setPrice_total(double price_total) {
		this.price_total = price_total;
	}

	public Share getShare() {
		return share;
	}

	public void setShare(Share share) {
		this.share = share;
	}

	public Broker getBroker() {
		return broker;
	}

	public void setBroker(Broker broker) {
		this.broker = broker;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public StockExchange getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(StockExchange stockExchange) {
		this.stockExchange = stockExchange;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
