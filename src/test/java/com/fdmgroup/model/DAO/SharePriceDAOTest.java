package com.fdmgroup.model.DAO;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.model.Share;
import com.fdmgroup.model.SharePrice;
import com.fdmgroup.validation.ShareException;
import com.fdmgroup.validation.SharePriceException;

public class SharePriceDAOTest {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private SharePriceDAO dao;
	private ShareDAO shareDAO;
	
	@Before
	public void before() {
		dao = new SharePriceDAO();
		shareDAO = new ShareDAO();
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction();
	}
	
	
	@Test
	public void test_update_share1time1483056000000to100gbp() throws SharePriceException {
		dao.update(6,1483056000000L,100);
		List<SharePrice> sharePrices = dao.list();
		boolean isThere = false;
		for (SharePrice sharePrice : sharePrices) {
			if (sharePrice.getPrice() > 99 && sharePrice.getPrice() < 101)
				isThere = true;
		}
		assertTrue(isThere);
		dao.update(6,1483056000000L, 206.85);
	}
	
	@Test
	public void test_remove_share1time1483056000000() throws SharePriceException, ShareException {
		int initialSize = dao.list().size();
		dao.remove(1,1483056000000L);
		int newSize = dao.list().size();
		assertEquals(initialSize, newSize+1);
		Share share = entityManager.find(Share.class, 1);
		dao.insert(share, 1483056000000L, 440.9);
	}
	
	@Test
	public void test_listByShare_ReturnsNull_WhenNullInput() {
		List<SharePrice> sharePrices = dao.list(null);
		assertEquals(null, sharePrices);
	}
	
	@Test
	public void test_listByShare_ReturnsCorrectSizeList_WhenValidInput() throws ShareException {
		Share share = shareDAO.getShare(1);
		List<SharePrice> sharePrices = dao.list(share);
		assertEquals(520, sharePrices.size());
	}
	
	@Test
	public void test_listByShareId_ReturnsNull_WhenZeroInput() {
		List<SharePrice> sharePrices = dao.list(0);
		assertEquals(null, sharePrices);
	}
	
	@Test
	public void test_listByShareId_ReturnsCorrectSizeList_WhenValidInput() throws ShareException {
		List<SharePrice> sharePrices = dao.list(1);
		assertEquals(520, sharePrices.size());
	}
	
	@Test
	public void test_listFuture_ReturnsNull_WhenZeroEntered() {
		List<SharePrice> sharePrices = dao.listFuture(0);
		assertEquals(null, sharePrices);
	}
	
	@Test
	public void test_listFuture_ReturnsEmptyList_WhenInvalidIdEntered() {
		List<SharePrice> sharePrices = dao.listFuture(100);
		assertEquals(0, sharePrices.size());
	}
	
	@Test
	public void test_listFuture_ReturnsCorrectSizeList_WhenValidInput() {
		List<SharePrice> sharePrices = dao.listFuture(1);
		assertEquals(252, sharePrices.size());
	}
	
	@Test
	public void test_listPast_ReturnsNull_WhenZeroEntered() {
		List<SharePrice> sharePrices = dao.listPast(0);
		assertEquals(null, sharePrices);
	}
	
	@Test
	public void test_listPast_ReturnsEmptyList_WhenInvalidIdEntered() {
		List<SharePrice> sharePrices = dao.listPast(100);
		assertEquals(0, sharePrices.size());
	}
	
	@Test
	public void test_listPast_ReturnsCorrectSizeList_WhenValidInput() {
		List<SharePrice> sharePrices = dao.listPast(1);
		assertEquals(268, sharePrices.size());
	}
	
	@Test(expected=NullPointerException.class)
	public void test_findLatestSharePriceByCompanyNameCurrencyNameOrStockExName_ReturnsNull_WhenNullInput() {
		dao.findLatestSharePriceByCompanyNameCurrencyNameOrStockExName(null);
	}
	
	@Test
	public void test_findLatestSharePriceByCompanyNameCurrencyNameOrStockExName_ReturnsNull_WhenEmptyInput() {
		SharePrice[] sharePrices = dao.findLatestSharePriceByCompanyNameCurrencyNameOrStockExName("");
		assertEquals(18, sharePrices.length);
	}
	
	@Test
	public void test_findLatestSharePriceByCompanyNameCurrencyNameOrStockExName_ReturnsCorrectSizeList_WhenValidCompanyNameInput() {
		SharePrice[] sharePrices = dao.findLatestSharePriceByCompanyNameCurrencyNameOrStockExName("Alphabet Inc.");
		assertEquals(3, sharePrices.length);
	}
	
	@Test
	public void test_findLatestSharePriceByCompanyNameCurrencyNameOrStockExName_ReturnsCorrectSizeList_WhenValidCurrencyNameInput() {
		SharePrice[] sharePrices = dao.findLatestSharePriceByCompanyNameCurrencyNameOrStockExName("Dollar");
		assertEquals(8, sharePrices.length);
	}
	
	@Test
	public void test_findLatestSharePriceByCompanyNameCurrencyNameOrStockExName_ReturnsCorrectSizeList_WhenValidStockExNameInput() {
		SharePrice[] sharePrices = dao.findLatestSharePriceByCompanyNameCurrencyNameOrStockExName("New York Stock Exchange");
		assertEquals(6, sharePrices.length);
	}
	
	@Test
	public void test_findLatestSharePriceByCompanyNameCurrencyNameOrStockExName_ReturnsCorrectSizeList_WhenValidCountryNameInput() {
		SharePrice[] sharePrices = dao.findLatestSharePriceByCompanyNameCurrencyNameOrStockExName("France");
		assertEquals(2, sharePrices.length);
	}
	
	@Test
	public void test_currentPrice_ReturnsZero_WhenZeroEntered() throws SharePriceException {
		double actual = dao.currentPrice(0);
		assertEquals(0.0, actual, 0.1);
	}
	
	@Test
	public void test_currentPrice_ReturnsCorrectNumber_WhenValidInput() throws SharePriceException {
		double actual = dao.currentPrice(1);
		assertEquals(464.4189, actual, 1);
	}
	
}
