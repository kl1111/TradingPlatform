package com.fdmgroup.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
* This is a class to handle the connection between brokers and stock exchanges.
*
*/

@Entity(name = "broker_stock_ex")
@IdClass(BrokerStockExchangeConcatenation.class)
public class BrokerStockExchange {

	@Id
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "broker_id")
	private Broker broker_id;

	@Id
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "stock_ex_id")
	private StockExchange stock_ex_id;

	@Transient
	private BrokerStockExchangeConcatenation brokerStockExchangeConcatenation;

	public BrokerStockExchange() {
	}

	public BrokerStockExchange(Broker broker_id, StockExchange stock_ex_id) {
		brokerStockExchangeConcatenation = new BrokerStockExchangeConcatenation(broker_id.getBroker_id(), stock_ex_id.getStock_ex_id());
		this.broker_id = broker_id;
		this.stock_ex_id = stock_ex_id;
	}

	public Broker getBroker_id() {
		return broker_id;
	}

	public void setBroker_id(Broker broker_id) {
		this.broker_id = broker_id;
	}

	public StockExchange getStock_ex_id() {
		return stock_ex_id;
	}

	public void setStock_ex_id(StockExchange stock_ex_id) {
		this.stock_ex_id = stock_ex_id;
	}

}
