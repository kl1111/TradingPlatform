package com.fdmgroup.model.DAO;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.model.Company;
import com.fdmgroup.model.Currency;
import com.fdmgroup.model.Share;
import com.fdmgroup.model.StockExchange;
import com.fdmgroup.validation.ShareException;

public class ShareDAOTest {
	
	int entriesBefore;
	ShareDAO shareDAO;
	private EntityManager entityManager;
	private EntityManagerFactory entityManagerFactory;
	@Before
	public void before() {
		shareDAO = new ShareDAO();
		entriesBefore = shareDAO.list().size();
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	@Test
	public void test_insert() throws ShareException {
		Company company = entityManager.find(Company.class,6);
		Currency currency = entityManager.find(Currency.class, 2);
		StockExchange stockExchange = entityManager.find(StockExchange.class, 1);
		Share share = shareDAO.insert(company,currency,stockExchange);
		assertEquals(entriesBefore + 1, shareDAO.list().size());
		//shareDAO.removeShare(entityManager.find(Company.class, 6), entityManager.find(Currency.class, 2));
	}
	
	
	@Test
	public void test_list_includes81and14() throws ShareException {
		List<Share> shares = shareDAO.list();
		boolean eightOne = false;
		boolean oneFour = false;
		for (Share share : shares) {
			if (share.getCompany().getCompany_id() == 8 && share.getCurrency().getCurrency_id() == 1)
				eightOne = true;
			if (share.getCompany().getCompany_id() == 1 && share.getCurrency().getCurrency_id() == 4)
				oneFour = true;
		}
		assertTrue(eightOne);
		assertTrue(oneFour);
	}
	
	@Test
	public void test_update_21to23() throws ShareException {
		shareDAO.update(3,entityManager.find(Company.class,2),entityManager.find(Currency.class, 3));
		boolean twoThree = false;
		List<Share> shares = shareDAO.list();
		for (Share share : shares) {
			if (share.getCompany().getCompany_id() == 2 && share.getCurrency().getCurrency_id() == 3)
				twoThree = true;
			
		}
		assertTrue(twoThree);
		shareDAO.update(3,entityManager.find(Company.class,2),entityManager.find(Currency.class, 1));
	}

	@Test(expected=ShareException.class)
	public void test_listthrowsInsertshareCurrencynotfound() throws ShareException {
		Company company = entityManager.find(Company.class,6);
		StockExchange stockExchange = entityManager.find(StockExchange.class, 1);
		Share share = shareDAO.insert(company, null, stockExchange);

	}
	
	
	@Test(expected=ShareException.class)
	public void test_listthrowsInsertshareCompanynotfound() throws ShareException {
		Currency currency = entityManager.find(Currency.class, 2);
		StockExchange stockExchange = entityManager.find(StockExchange.class, 1);
		Share share = shareDAO.insert(null, currency, stockExchange);
	
	}
	
	@Test(expected=ShareException.class)
	public void test_listthrowsInsertshareStockExchangenotfound() throws ShareException {
		Company company = entityManager.find(Company.class,6);
		Currency currency = entityManager.find(Currency.class, 2);
		Share share = shareDAO.insert(company, currency, null);
		
	}
	
	
	
	
}
