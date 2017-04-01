package com.fdmgroup.model.DAO;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.model.ExchangeRate;

public class ExchangeRateDAOTest {
	
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	private ExchangeRateDAO exchangeRateDAO;
	
	@Before
	public void before() {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
		exchangeRateDAO = new ExchangeRateDAO(entityManager);
	}

	@Test
	public void test_listAllRates_ReturnsListOfCorrectSize_WhenCalled() {
		List<ExchangeRate> rates = exchangeRateDAO.listAllRates();
		assertEquals(126, rates.size());
	}
	
	@Test
	public void test_listCurrentRates_ReturnsListOfRecentRates_WhenCalled() {
		List<ExchangeRate> rates = exchangeRateDAO.listCurrentRates();
		assertEquals(6, rates.size());
	}
	
	@Test
	public void test_convert_ReturnsNull_WhenZeroInput() {
		double actual = exchangeRateDAO.convert(0, 0, 0);
		assertEquals(0, actual, 0.01);
	}
	
	@Test
	public void test_convert_ReturnsZero_WhenInvalidCurrencyIdInput() {
		double actual = exchangeRateDAO.convert(9, 9, 1);
		assertEquals(0, actual, 0.01);
	}
	
	@Test
	public void test_convert_ReturnsCorrectValue_WhenValidInput() {
		double actual = exchangeRateDAO.convert(1, 6, 100);
		assertEquals(101.6, actual, 0.01);
	}
	
	@Test
	public void test_convert_ReturnsCorrectValue_WhenValidInputAgain() {
		double actual = exchangeRateDAO.convert(5, 7, 100);
		assertEquals(1033.11, actual, 0.01);
	}

}
