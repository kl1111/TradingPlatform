package com.fdmgroup.model.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.log4j.Logger;

import com.fdmgroup.model.Company;
import com.fdmgroup.model.Currency;
import com.fdmgroup.model.Share;
import com.fdmgroup.model.StockExchange;
import com.fdmgroup.validation.ShareException;

public class ShareDAO {

	static Logger log = Logger.getLogger(PlaceDAO.class);
	EntityManagerFactory entityManagerFactory;
	EntityManager entityManager;
	EntityTransaction entityTransaction;

	public ShareDAO() {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject"); // DB
																						// stuff
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}

	public List<Share> list() {
		Query query1 = entityManager.createNativeQuery("SELECT * FROM shares", Share.class);
		@SuppressWarnings("unchecked")
		List<Share> results = query1.getResultList();
		log.info("Searched for shares. Found " + results.size() + " entries.");
		return results;
	}

	public Share insert(Company company, Currency currency, StockExchange stockex) throws ShareException {
		if (company == null)
			throw new ShareException("Insert share: Company not found.");
		if (currency == null)
			throw new ShareException("Insert share: Currency not found.");
		if (stockex == null)
			throw new ShareException("Insert share: Stock Exchange not found.");
		Share share = new Share(company, currency, stockex);
		entityTransaction.begin();
		entityManager.merge(share);
		entityTransaction.commit();
		log.info("Inserted a new share.");
		return share;
	}

	public void update(int share_id, Company company, Currency currency) {
		Share share = entityManager.find(Share.class, share_id);
		if (share != null) {
			entityTransaction.begin();
			share.setCompany(company);
			share.setCurrency(currency);
			entityManager.merge(share);
			entityTransaction.commit();
		}
	}

	public Share getShare(int share_id) throws ShareException {
		Share share = entityManager.find(Share.class, share_id);
		if (share == null) {
			throw new ShareException("getShare: Share not found.");
		}
		return share;
	}

//	public Share findShare(Company company, Currency currency, StockExchange stockExchange) throws ShareException {
//		Share share = (Share) entityManager
//				.createNativeQuery("SELECT share_id, company_id, currency_id FROM shares WHERE company_id="
//						+ company.getCompany_id() + " AND currency_id=" + currency.getCurrency_id()
//						+ " AND stock_ex_id=" + stockExchange.getStock_ex_id(), Share.class)
//				.getSingleResult();
//		if (share == null)
//			throw new ShareException("findShare: Share not found.");
//		return share;
//	}


}
