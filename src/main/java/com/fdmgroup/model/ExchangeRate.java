package com.fdmgroup.model;

import java.sql.Date;

import javax.persistence.*;

@Entity(name="exchange_rates")
@IdClass(ExchangeRateConcatenation.class)
public class ExchangeRate {
	
	@Id
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="currency_id")
	private Currency currency_id;
	
	@Column(name="exchange_rate")
	private double exchange_rate;
	
	@Id
	@Column(name="issue_day")
	private Date issue_day;
	
	@Transient
	private ExchangeRateConcatenation exchangeRateConcatenation;
	
	public ExchangeRate() {}
	
	public ExchangeRate(Currency currency, double exchangeRate, Date issueDay) {
		exchangeRateConcatenation = new ExchangeRateConcatenation(currency.getCurrency_id(), issueDay);
		this.currency_id = currency;
		this.exchange_rate = exchangeRate;
		this.issue_day = issueDay;
	}

	public Currency getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(Currency currency_id) {
		this.currency_id = currency_id;
	}

	public double getExchange_rate() {
		return exchange_rate;
	}

	public void setExchange_rate(double exchange_rate) {
		this.exchange_rate = exchange_rate;
	}

	public Date getIssue_day() {
		return issue_day;
	}

	public void setIssue_day(Date issue_day) {
		this.issue_day = issue_day;
	}

}
