package com.fdmgroup.model;

import java.io.Serializable;

public class BrokerStockExchangeConcatenation implements Serializable {
	
	/**
	* This is a concatenation class.
	*
	*/
	
	private static final long serialVersionUID = 6568552713379773973L;
	
	private int broker_id;
	private int stock_ex_id;
	
	public BrokerStockExchangeConcatenation() {}
	
	public BrokerStockExchangeConcatenation(int broker_id, int stock_ex_id) {
		this.broker_id = broker_id;
		this.stock_ex_id = stock_ex_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + broker_id;
		result = prime * result + stock_ex_id;
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
		BrokerStockExchangeConcatenation other = (BrokerStockExchangeConcatenation) obj;
		if (broker_id != other.broker_id)
			return false;
		if (stock_ex_id != other.stock_ex_id)
			return false;
		return true;
	}

	public int getBroker_id() {
		return broker_id;
	}

	public void setBroker_id(int broker_id) {
		this.broker_id = broker_id;
	}

	public int getStock_ex_id() {
		return stock_ex_id;
	}

	public void setStock_ex_id(int stock_ex_id) {
		this.stock_ex_id = stock_ex_id;
	}
	
}
