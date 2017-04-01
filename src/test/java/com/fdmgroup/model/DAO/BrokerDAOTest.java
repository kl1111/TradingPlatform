package com.fdmgroup.model.DAO;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.model.Broker;
import com.fdmgroup.model.User;
import com.fdmgroup.model.DAO.BrokerDAO;

public class BrokerDAOTest {
	
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	private BrokerDAO brokerDAO;
	
	@Before
	public void before() {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
		brokerDAO = new BrokerDAO(entityManager);
	}

	@Test
	public void test_newBroker_ReturnsNull_WhenAnyInputIsNull() {
		Broker actual = brokerDAO.newBroker(entityManager.find(User.class,0), null, null);
		assertEquals(null, actual);
	}
	
	@Test
	public void test_newBroker_ReturnsBroker_WhenValidInput() {
		Broker actual = brokerDAO.newBroker(entityManager.find(User.class,1), "Darryl", "Jackson");
		assertEquals("Darryl", actual.getFirstName());
	}
	
	@Test
	public void test_listBrokers_ReturnsCorrectSizeList_WhenCalled() {
		int actual = brokerDAO.listBrokers().size();
		assertEquals(15, actual);
	}
	
	@Test
	public void test_removeBroker_ReturnsNull_WhenNullInput() {
		Broker actual = brokerDAO.removeBroker(null);
		assertEquals(null, actual);
	}
	
	@Test
	public void test_removeBroker_ReturnsBroker_WhenValidBrokerInput() {
		Broker broker = new Broker(entityManager.find(User.class,7), "John", "Smith");
		Broker actual = brokerDAO.removeBroker(broker);
		assertEquals(broker, actual);
	}
	
	@Test
	public void test_findBroker_ReturnsNull_WhenNullInput() {
		Broker actual = brokerDAO.findBroker(0);
		assertEquals(null, actual);
	}
	
	@Test
	public void test_findBroker_ReturnsNull_WhenInvalidInput() {
		Broker actual = brokerDAO.findBroker(40);
		assertEquals(null, actual);
	}
	
	@Test
	public void test_findBroker_ReturnsCorrectBroker_WhenValidInput() {
		Broker actual = brokerDAO.findBroker(1);
		assertEquals("John", actual.getFirstName());
	}

}
