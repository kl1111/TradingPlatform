package com.fdmgroup.model.DAO;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

import com.fdmgroup.model.Place;
import com.fdmgroup.model.StockExchange;
import com.fdmgroup.validation.InvalidStockExchangeException;

public class StockExchangeDAO {
	private EntityManager entitymanager;
	static Logger log = Logger.getLogger(StockExchange.class);
	
	public StockExchangeDAO(EntityManager em) {
		this.entitymanager = em;
	}
	
	public StockExchange createStockExchange(String name, String symbol, Place place) {
		StockExchange stockExchange = new StockExchange(name, symbol, place);
		EntityTransaction entityTransaction = entitymanager.getTransaction();
		entityTransaction.begin();
		entitymanager.persist(stockExchange);
		entityTransaction.commit();
		
		log.info("Created stock exchange: " + stockExchange.getName() + " into the table.");
		
		return stockExchange;
	}
	
	public void removeStockExchange(int stock_ex_id) throws InvalidStockExchangeException {
		StockExchange stockExchange = findStockExchange(stock_ex_id);
		EntityTransaction entityTransaction = entitymanager.getTransaction();
		if (stockExchange != null) {
			log.info("Removed stock exchange: " + stockExchange.getName() + " from the table.");
			
			entityTransaction.begin();
			entitymanager.remove(stockExchange);
			entityTransaction.commit();
		}
		else{
			throw new InvalidStockExchangeException("Stock Exchange Not Exist");
		}
	}
	
	public void updateStockExchange(int stock_ex_id, String name, String symbol){
		StockExchange stockExchange = findStockExchange(stock_ex_id);
		EntityTransaction entityTransaction = entitymanager.getTransaction();
		
		entityTransaction.begin();
		stockExchange.setName(name);
		stockExchange.setSymbol(symbol);
		entityTransaction.commit();
	}
	
	//find by ID
	public StockExchange findStockExchange(int stock_ex_id) {
		return entitymanager.find(StockExchange.class, stock_ex_id);
	}
	
	//find by name
	public StockExchange findStockExchangeByName(String name){
		try{
			StockExchange stockExchange = new StockExchange();
			stockExchange = (StockExchange) entitymanager.createNativeQuery("SELECT * FROM Stock_Exchanges WHERE name = "+ "'"+name+"'", StockExchange.class).getSingleResult();
			return stockExchange;
		}catch (NoResultException e){
			return null;
		}
	}
	
	//find by symbol
	public StockExchange findStockExchangeBySymbol(String symbol){
		try{
			StockExchange stockExchange = new StockExchange();
			stockExchange = (StockExchange) entitymanager.createNativeQuery("SELECT * FROM Stock_Exchanges WHERE symbol = "+ "'"+symbol+"'", StockExchange.class).getSingleResult();
			return stockExchange;
		}catch (NoResultException e){
			return null;
		}
	}
	
	public EntityManager getEntityManager() {
		return entitymanager;
	}
	
	@SuppressWarnings("unchecked")
	public StockExchange[] ListOfStockExchanges(){
		List<StockExchange> resultList = new ArrayList<StockExchange>();
		Query query = entitymanager.createNativeQuery("SELECT stock_ex_id, name, symbol, place_id FROM Stock_Exchanges", StockExchange.class);
		resultList = query.getResultList();
		StockExchange[] listOfStockExchanges = new StockExchange[resultList.size()];
		resultList.toArray(listOfStockExchanges);
        return listOfStockExchanges;
	}
}
