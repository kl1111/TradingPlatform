package com.fdmgroup.model.DAO;

import static org.junit.Assert.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.fdmgroup.model.Currency;

public class CurrencyDAOTest {

	private EntityManager entityManager;
	private EntityManagerFactory entityManagerFactory;
	private EntityTransaction entityTransaction;

	private CurrencyDAO currencyDAO;
	private List<Currency> currencies;
	private Currency currency;
	private String jPAName = "groupproject";
	private int dollar = 1;

	@Before
	public void setup() {
		entityManagerFactory = Persistence.createEntityManagerFactory(jPAName);
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
		currencyDAO = new CurrencyDAO(entityManager);
	}

	@Test
	public void test_SuccessfulConnectionMade() {
		// ARRANGE
		// ACT
		// ASSERT
		assertNotNull(entityTransaction);
	}

	@Test
	public void test_ThatgetEntityManagerReturnsEntityManager() {
		// ARRANGE
		// ACT
		EntityManager foundEntityManager = currencyDAO.getEntityManager();
		// ASSERT
		assertNotNull(foundEntityManager);
	}

	@Test
	public void testThatFindCurrencyReturnsNullWhenNothingIsEntered() {
		// ARRANGE
		// ACT
		currency = currencyDAO.findCurrency(0);
		// Assert
		assertEquals(null, currency);
	}

	@Test
	public void testThatFindCurrencyWithInValidCurrencyReturnsNull() {
		// ARRANGE
		// ACT
		currency = currencyDAO.findCurrency(2222);
		// Assert
		assertEquals(null, currency);
	}

	@Test
	public void testThatFindCurrencyWithValidCurrencyIDReturnsCurrency() {
		// ARRANGE
		// ACT
		currency = currencyDAO.findCurrency(dollar);
		String foundCurrencyName = currency.getName();
		String dollar = "Dollar";
		// Assert
		assertEquals(dollar, foundCurrencyName);
	}

	@Test
	public void testThatGetCurrenciesListGetsListOfCurrenciesFromDatabase() {
		// ARRANGE
		// ACT
		currencies = currencyDAO.listCurrencies();
		// ASSERT
		assertEquals(7, currencies.size());
	}

	@Test
	public void testThatMethodCreateCurrencyReturnsNullWhenNullIsEntered() {
		// ARRANGE
		entityTransaction.begin();
		Currency newCurrency = new Currency(null, null);
		// ACT
		currency = currencyDAO.createCurrency(newCurrency);
		entityTransaction.commit();
		// ASSERT
		assertEquals(null, currency);
	}

	@Test
	public void testThatMethodCreateCurrencyReturnsNullWhenCurrencyAlreadyExists() {
		// ARRANGE
		entityTransaction.begin();
		Currency newCurrency = new Currency("$", "Dollar");
		// ACT
		currency = currencyDAO.createCurrency(newCurrency);
		entityTransaction.commit();
		// ASSERT
		assertNull(currency);
	}

	@Test
	public void testThatMethodCreateCurrencyInCurrencyDAOAddsCurrencytoDatabase() {
		// ARRANGE
		entityTransaction.begin();
		Currency newCurrency = new Currency();
		newCurrency.setName("Galleon");
		newCurrency.setSymbol_id("G");
		// ACT
		currencyDAO.createCurrency(newCurrency);
		entityTransaction.commit();
		currency = currencyDAO.findCurrency(newCurrency.getCurrency_id());
		// Assert
		String actual = currency.getName();
		assertEquals( "Galleon", actual);
		entityTransaction.begin();
		currencyDAO.deleteCurrency(newCurrency.getCurrency_id());
		entityTransaction.commit();
	}

	@Test
	public void testThatMethodDeleteCurrencyInCurrencyDAODeletesCurrencyFromDatabase() {
		// ARRANGE
		Currency currency = currencyDAO.createCurrency(new Currency("VND", "Vietnamese Dong"));
		// ACT
		entityTransaction.begin();
		currencyDAO.deleteCurrency(currency.getCurrency_id());
		entityTransaction.commit();
		currency = currencyDAO.findCurrency(currency.getCurrency_id());
		// ASSERT
		assertNull(currency);
	}

	@Test
	public void testThatMethodUpdateCurrencyInCurrencyNameDAOReturnsNullWhenNothingEntered() {
		// ARRANGE
		// ACT
		entityTransaction.begin();
		Currency updatedCurrency = currencyDAO.updateCurrencyName(null);
		entityTransaction.commit();
		// ASSERT
		assertNull(updatedCurrency);
	}

	@Test
	public void testThatMethodUpdateCurrencyInCurrencyDAOChangesNameOfEuroInDatabase() {
		// ARRANGE
		Currency newCurrency = new Currency("EUR", "Not Euro");
		// ACT
		entityTransaction.begin();
		currencyDAO.updateCurrencyName(newCurrency);
		entityTransaction.commit();
		currency = currencyDAO.findCurrency(2);
		String foundCurrencyName = currency.getName();
		// ASSERT
		assertEquals("Not Euro", foundCurrencyName);
	}
	
	@Test
	public void testThatMethodUpdateCurrencyInCurrencySymbolDAOReturnsNullWhenNothingEntered() {
		// ARRANGE
		// ACT
		entityTransaction.begin();
		Currency updatedCurrency = currencyDAO.updateCurrencySymbol(null);
		entityTransaction.commit();
		// ASSERT
		assertNull(updatedCurrency);
	}
	@Test
	public void testThatMethodUpdateCurrencyInCurrencyDAOChangesSymbolOfBritishPenceInDatabase() {
		// ARRANGE
		Currency newCurrency = new Currency("P", "British Pence");
		// ACT
		entityTransaction.begin();
		currencyDAO.updateCurrencySymbol(newCurrency);
		entityTransaction.commit();
		currency = currencyDAO.findCurrency(4);
		String foundCurrencyName = currency.getSymbol();
		// ASSERT
		assertEquals("P", foundCurrencyName);
	}

	@After
	public void after() {
		entityManager.close();
		entityManagerFactory.close();
	}
}
