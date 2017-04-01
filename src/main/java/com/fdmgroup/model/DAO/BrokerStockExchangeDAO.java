package com.fdmgroup.model.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.apache.log4j.Logger;
import com.fdmgroup.model.Broker;
import com.fdmgroup.model.BrokerStockExchange;
import com.fdmgroup.model.StockExchange;

public class BrokerStockExchangeDAO {

	private EntityManager entityManager;
	static Logger log = Logger.getLogger(BrokerStockExchangeDAO.class);

	public BrokerStockExchangeDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public BrokerStockExchange addBrokerStockExchange(BrokerStockExchange brokerStockExchange) {
		if (brokerStockExchange == null)
			return null;
		int broker_id = brokerStockExchange.getBroker_id().getBroker_id();
		int stock_ex_id = brokerStockExchange.getStock_ex_id().getStock_ex_id();
		if (broker_id == 0 || stock_ex_id == 0)
			return null;
		Broker broker = entityManager.find(Broker.class, broker_id);
		StockExchange stockExchange = entityManager.find(StockExchange.class, stock_ex_id);
		if(broker == null || stockExchange == null){
			throw new RollbackException();
		}
		
		entityManager.persist(brokerStockExchange);
		log.info("Inserted broker: " + broker_id + " into the StockExchange" + stock_ex_id);
		return brokerStockExchange;

	}

	public BrokerStockExchange removeBrokerStockExchange(BrokerStockExchange brokerStockExchange) {
		if (brokerStockExchange == null)
			return null;
		int broker_id = brokerStockExchange.getBroker_id().getBroker_id();
		int stock_ex_id = brokerStockExchange.getStock_ex_id().getStock_ex_id();
		if (broker_id == 0 || stock_ex_id == 0)
			return null;
		BrokerStockExchange brokerStockExchangeToBeRemoved = entityManager.merge(brokerStockExchange);
		entityManager.remove(brokerStockExchangeToBeRemoved);
		return brokerStockExchange;

	}

	public List<StockExchange> listStockExchangesForBroker(int i) {
		if (i == 0)
			return null;
		BrokerDAO brokerDAO = new BrokerDAO(entityManager);
		Broker broker = brokerDAO.findBroker(i);
		if (broker == null) {
			log.error("Invalid broker id entered");
			return null;
		}
		@SuppressWarnings("unchecked")
		List<StockExchange> stockExchange = entityManager.createNativeQuery(
				"SELECT stock_exchanges.stock_ex_id, stock_exchanges.name, stock_exchanges.symbol, stock_exchanges.place_id FROM broker_stock_ex INNER JOIN stock_exchanges ON broker_stock_ex.stock_ex_id=stock_exchanges.stock_ex_id WHERE broker_id="
						+ i,
				StockExchange.class).getResultList();
		return stockExchange;

	}

	@SuppressWarnings("unchecked")
	public List<Broker> listBrokersForStockExchange(int i) {
		if (i == 0)
			return null;
		StockExchangeDAO stockExchangeDAO = new StockExchangeDAO(entityManager);
		StockExchange stockExchange = stockExchangeDAO.findStockExchange(i);
		if (stockExchange == null) {
			log.error("Invalid stock exchange id entered");
			return null;
		}
		List<Broker> brokers = entityManager.createNativeQuery(
				"SELECT brokers.broker_id, brokers.first_name, brokers.last_name FROM broker_stock_ex INNER JOIN brokers ON broker_stock_ex.broker_id=brokers.broker_id WHERE stock_ex_id="
						+ i,
				Broker.class).getResultList();
		return brokers;
	}

}
