package com.fdmgroup.model.DAO;

import static org.junit.Assert.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.fdmgroup.model.Broker;
import com.fdmgroup.model.BrokerStockExchange;
import com.fdmgroup.model.Place;
import com.fdmgroup.model.StockExchange;
import com.fdmgroup.model.User;

public class BrokerStockExchangeDAOTest {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	private BrokerStockExchange brokerStockExchange;
	private BrokerStockExchangeDAO brokerStockExchangeDAO;

	@Before
	public void before() {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
		brokerStockExchangeDAO = new BrokerStockExchangeDAO(entityManager);
	}

	@Test
	public void test_SuccessfulConnectionMade() {
		// ARRANGE
		// ACT
		// ASSERT
		assertNotNull(entityTransaction);
	}

	@Test
	public void test_addBrokerStockExchangeReturnsNullWhenNullInput() {
		// ARRANGE
		// ACT
		brokerStockExchange = brokerStockExchangeDAO.addBrokerStockExchange(null);
		// ASSERT
		assertNull(brokerStockExchange);
	}

	@Test(expected = RollbackException.class)
	public void test_addBrokerStockExchangeThrowsExceptionWhenInValidBrokerInput() {
		// ARRANGE
		// ACT
		
		Broker broker = new Broker(new User("a","a","a","a","a", "a"),"a","a");
		broker.setBroker_id(44);
		BrokerStockExchange newBrokerStockExchange = new BrokerStockExchange(broker,entityManager.find(StockExchange.class, 3));
		entityTransaction.begin();
		brokerStockExchange = brokerStockExchangeDAO.addBrokerStockExchange(newBrokerStockExchange);
		entityTransaction.commit();
		// ASSERT
	}

	@Test(expected = RollbackException.class)
	public void test_addBrokerStockExchangeThrowsExceptionWhenInValidStockExchangeInput() {
		// ARRANGE
		// ACT
		
		StockExchange stockExchange = new StockExchange("a","a",new Place("a","a"));
		stockExchange.setStock_ex_id(99);
		BrokerStockExchange newBrokerStockExchange = new BrokerStockExchange(entityManager.find(Broker.class, 1),stockExchange);
		entityTransaction.begin();
		brokerStockExchange = brokerStockExchangeDAO.addBrokerStockExchange(newBrokerStockExchange);
		entityTransaction.commit();
		// ASSERT
	}

	@Test(expected = RollbackException.class)
	public void test_addBrokerStockExchangeThrowsExceptionWhenAlreadyExistingBrokerStockExchangeInput() {
		// ARRANGE
		// ACT
		BrokerStockExchange newBrokerStockExchange = new BrokerStockExchange(entityManager.find(Broker.class, 15),entityManager.find(StockExchange.class, 2));
		entityTransaction.begin();
		brokerStockExchange = brokerStockExchangeDAO.addBrokerStockExchange(newBrokerStockExchange);
		entityTransaction.commit();
		// ASSERT
	}

	@Test
	public void test_addBrokerStockExchangeReturnsBrokerStockExchangeWhenValidBrokerStockExchangeInput() {
		// ARRANGE
		// ACT
		BrokerStockExchange newBrokerStockExchange = new BrokerStockExchange(entityManager.find(Broker.class, 15),entityManager.find(StockExchange.class, 1));
		entityTransaction.begin();
		brokerStockExchange = brokerStockExchangeDAO.addBrokerStockExchange(newBrokerStockExchange);
		entityTransaction.commit();
		// ASSERT
		assertEquals(newBrokerStockExchange, brokerStockExchange);
		entityTransaction.begin();
		brokerStockExchangeDAO.removeBrokerStockExchange(newBrokerStockExchange);
		entityTransaction.commit();
	}

	@Test
	public void test_removeBrokerStockExchangeReturnsNullWhenNullIsInput() {
		// ARRANGE
		// ACT
		entityTransaction.begin();
		brokerStockExchange = brokerStockExchangeDAO.removeBrokerStockExchange(null);
		entityTransaction.commit();
		// ASSERT
		assertNull(brokerStockExchange);
	}

	@Test
	public void test_removeBrokerStockExchange_RemovesBrokerFromDatabase_WhenInputIsValid() {
		// ARRANGE
		// ACT
		BrokerStockExchange newBrokerStockExchange = new BrokerStockExchange(entityManager.find(Broker.class, 1),entityManager.find(StockExchange.class, 2));
		entityTransaction.begin();
		brokerStockExchange = brokerStockExchangeDAO.removeBrokerStockExchange(newBrokerStockExchange);
		entityTransaction.commit();
		// ASSERT
		assertEquals(newBrokerStockExchange, brokerStockExchange);
		BrokerStockExchange brokerStockExchangeToAdd = new BrokerStockExchange(entityManager.find(Broker.class, 1),entityManager.find(StockExchange.class, 2));
		entityTransaction.begin();
		brokerStockExchangeDAO.addBrokerStockExchange(brokerStockExchangeToAdd);
		entityTransaction.commit();
	}
	@Test
	public void test_removeBrokerStockExchangeReturnsNullWhenInputIsInvalid() {
		// ARRANGE
		// ACT
		BrokerStockExchange newBrokerStockExchange = new BrokerStockExchange(new Broker(new User("a","a","a","a","a", "a"),"a","a"),new StockExchange("a","a",new Place("a","a")));
		entityTransaction.begin();
		brokerStockExchange = brokerStockExchangeDAO.removeBrokerStockExchange(newBrokerStockExchange);
		entityTransaction.commit();
		// ASSERT
		assertEquals(null, brokerStockExchange);
	}

	
	@Test
	public void test_removeBrokerStockExchangeReturnNullFromDatabaseAndReturnsNullWhenInputIsInvalid() {
		// ARRANGE
		// ACT
		BrokerStockExchange newBrokerStockExchange = new BrokerStockExchange(new Broker(new User("a","a","a","a","a", "a"),"a","a"),new StockExchange("a","a",new Place("a","a")));
		entityTransaction.begin();
		brokerStockExchange = brokerStockExchangeDAO.removeBrokerStockExchange(newBrokerStockExchange);
		entityTransaction.commit();
		// ASSERT
		assertNull(brokerStockExchange);
	}
	
	@Test
	public void test_listStockExchangesForBrokerReturnsNullWhenzeroInput() {
		// ARRANGE
		// ACT
		List<StockExchange> stockExchange = brokerStockExchangeDAO.listStockExchangesForBroker(0);
		// ASSERT
		assertNull(stockExchange);
	}

	@Test
	public void test_listStockExchangesForBrokerReturnsNullWhenInvalidInput() {
		// ARRANGE
		// ACT
		List<StockExchange> stockExchange = brokerStockExchangeDAO.listStockExchangesForBroker(50);
		// ASSERT
		assertNull(stockExchange);
	}

	@Test
	public void test_listStockExchangesForBrokerReturnsCorrectSizeListWhenValidInput() {
		// ARRANGE
		// ACT
		List<StockExchange> stockExchange = brokerStockExchangeDAO.listStockExchangesForBroker(1);
		// ASSERT
		assertEquals(4, stockExchange.size());
	}

	@Test
	public void test_listBrokerForStockExchangeReturnsNullWhenzeroInput() {
		// ARRANGE
		// ACT
		List<Broker> brokers = brokerStockExchangeDAO.listBrokersForStockExchange(0);
		// ASSERT
		assertNull(brokers);
	}

	@Test
	public void test_listBrokerForStockExchangeReturnsNullWhenInvalidInput() {
		// ARRANGE
		// ACT
		List<Broker> brokers = brokerStockExchangeDAO.listBrokersForStockExchange(66);
		// ASSERT
		assertNull(brokers);
	}

	@Test
	public void test_listBrokerForStockExchangeReturnsCorrectSizeListWhenValidInput() {
		// ARRANGE
		// ACT
		List<Broker> brokers = brokerStockExchangeDAO.listBrokersForStockExchange(1);
		// ASSERT
		assertEquals(5, brokers.size());
	}

	@After
	public void after() {
		entityManager.close();
		entityManagerFactory.close();
	}
}
