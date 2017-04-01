package com.fdmgroup.model;

import java.io.Serializable;
import java.sql.Date;

public class ExchangeRateConcatenation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5180347394512175511L;
	
	private int currency_id;
	private Date issue_day;
	
	public ExchangeRateConcatenation() {}
	
	public ExchangeRateConcatenation(int currencyId, Date issue_day) {
		this.currency_id = currencyId;
		this.issue_day = issue_day;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currency_id;
		result = prime * result + ((issue_day == null) ? 0 : issue_day.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExchangeRateConcatenation other = (ExchangeRateConcatenation) obj;
		if (currency_id != other.currency_id)
			return false;
		if (issue_day == null) {
			if (other.issue_day != null)
				return false;
		} else if (!issue_day.equals(other.issue_day))
			return false;
		return true;
	}

	public int getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(int currency_id) {
		this.currency_id = currency_id;
	}

	public Date getIssue_day() {
		return issue_day;
	}

	public void setIssue_day(Date issue_day) {
		this.issue_day = issue_day;
	}

}
