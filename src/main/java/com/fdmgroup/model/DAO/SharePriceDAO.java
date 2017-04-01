package com.fdmgroup.model.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.fdmgroup.model.Share;
import com.fdmgroup.model.SharePrice;
import com.fdmgroup.model.SharePriceConcatenation;
import com.fdmgroup.validation.SharePriceException;

public class SharePriceDAO {

	static Logger log = Logger.getLogger(SharePriceDAO.class);
	EntityManagerFactory entityManagerFactory;
	EntityManager entityManager;
	EntityTransaction entityTransaction;

	public SharePriceDAO() {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject"); // DB
																						// stuff
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}

	public void insert(Share share, long time, double price) throws SharePriceException {
		if (share == null)
			throw new SharePriceException("SharePriceDAO insert method: did not find the share.");
		if (time < 0)
			throw new SharePriceException("SharePriceDAO insert method: negative timestamps are not allowed.");
		SharePrice sharePrice = new SharePrice(share, time, price);
		entityTransaction.begin();
		entityManager.persist(sharePrice);
		entityTransaction.commit();
		log.info("Inserted a new share.");
	}

	public List<SharePrice> list() {
		TypedQuery<SharePrice> query1 = entityManager.createQuery("SELECT sp FROM shares_prices sp", SharePrice.class);
		List<SharePrice> results = query1.getResultList();
		log.info("Searched for shares prices. Found " + results.size() + " entries.");
		return results;
	}

	@SuppressWarnings("unchecked")
	public List<SharePrice> list(Share share) {
		if (share == null)
			return null;
		Query query1 = entityManager
				.createNativeQuery("SELECT share_id,time_start,price FROM shares_prices WHERE share_id = " + share.getShare_id(), SharePrice.class);
		//query1.setParameter("share_id", share.getShare_id());
		List<SharePrice> results = query1.getResultList();
		log.info("Searched for shares prices. Found " + results.size() + " entries.");
		return results;
	}

	public List<SharePrice> list(int share_id) {
		if (share_id == 0)
			return null;
		Query query1 = entityManager
				.createNativeQuery("SELECT share_id,time_start,price FROM shares_prices WHERE share_id =" + share_id
						+ " ORDER BY time_start", SharePrice.class);
		@SuppressWarnings("unchecked")
		List<SharePrice> results = query1.getResultList();
		log.info("Searched for shares prices. Found " + results.size() + " entries.");
		return results;
	}

	public List<SharePrice> listOfCurrentPrices() {
		Date date = new Date();
		long time = date.getTime();
		Query query1 = entityManager.createNativeQuery("SELECT share_id,time_start,price from shares_prices where time_start <=" + time
				+ "and time_start = (select max(time_start) from shares_prices where time_start <" + time +")", SharePrice.class);
		@SuppressWarnings("unchecked")
		List<SharePrice> results = query1.getResultList();
		return results;
	}
	
	public List<SharePrice> listFuture(int share_id) {
		if (share_id == 0)
			return null;
		Date date = new Date();
		Long time = date.getTime();
		Query query1 = entityManager
				.createNativeQuery("SELECT share_id,time_start,price FROM shares_prices WHERE share_id =" + share_id + " AND time_start >" + time
						+ " ORDER BY time_start", SharePrice.class);
		@SuppressWarnings("unchecked")
		List<SharePrice> results = query1.getResultList();
		log.info("Searched for shares prices. Found " + results.size() + " entries.");
		return results;
	}
	
	public List<SharePrice> listPast(int share_id) {
		if (share_id == 0)
			return null;
		Date date = new Date();
		Long time = date.getTime();
		Query query1 = entityManager
				.createNativeQuery("SELECT share_id,time_start,price FROM shares_prices WHERE share_id =" + share_id + " AND time_start <" + time
						+ " ORDER BY time_start", SharePrice.class);
		@SuppressWarnings("unchecked")
		List<SharePrice> results = query1.getResultList();
		log.info("Searched for shares prices. Found " + results.size() + " entries.");
		return results;
	}
	
	
	public SharePrice[] findLatestSharePriceByCompanyNameCurrencyNameOrStockExName(String searchValue){	
		List<SharePrice> allLatestShares = listOfCurrentPrices();
		List<SharePrice> returnedShares = new ArrayList<SharePrice>();
		for (SharePrice sharePrice : allLatestShares) {		
					
			if (sharePrice.getShare().getCompany().getCompany_name().toLowerCase().contains(searchValue.toLowerCase())){
				returnedShares.add(sharePrice);
			}
			else if(sharePrice.getShare().getCurrency().getName().toLowerCase().contains(searchValue.toLowerCase())){
				returnedShares.add(sharePrice);
			}
			else if(sharePrice.getShare().getStockExchange().getName().toLowerCase().contains(searchValue.toLowerCase())){
				returnedShares.add(sharePrice);
			}
			else if(sharePrice.getShare().getCompany().getPlace().getCountry().toLowerCase().contains(searchValue.toLowerCase())){
				returnedShares.add(sharePrice);
			}
		}
		
		
		
		SharePrice[] shareArray = new SharePrice[returnedShares.size()];
		returnedShares.toArray(shareArray);
		return shareArray;
		
	}

	public void update(int share_id, long time, double price) throws SharePriceException {
		SharePriceConcatenation concatenation = new SharePriceConcatenation(share_id, time);
		SharePrice sharePrice = entityManager.find(SharePrice.class, concatenation);
		if (sharePrice == null)
			throw new SharePriceException("SharePriceDAO update method: did not find the share price.");
		entityTransaction.begin();
		sharePrice.setPrice(price);
		entityTransaction.commit();
	}

	public void remove(int share_id, long time) throws SharePriceException {
		SharePriceConcatenation concatenation = new SharePriceConcatenation(share_id, time);
		SharePrice sharePrice = entityManager.find(SharePrice.class, concatenation);
		if (sharePrice == null)
			throw new SharePriceException("SharePriceDAO remove method: did not find the share price.");
		entityTransaction.begin();
		entityManager.remove(sharePrice);
		entityTransaction.commit();
	}

	public double currentPrice(int share_id) throws SharePriceException {
		if (share_id == 0)
			return 0.0;
		List<SharePrice> allLatestShares = listOfCurrentPrices();
		for (SharePrice sharePrice : allLatestShares) {		
					
			if (sharePrice.getShare().getId() == share_id)
				return sharePrice.getPrice();
		}
		throw new SharePriceException("SharePriceDAO: could not find a price for share with id = " + share_id);
	}

}
