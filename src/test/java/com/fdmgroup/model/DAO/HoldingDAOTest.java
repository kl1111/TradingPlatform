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

import com.fdmgroup.model.Holding;
import com.fdmgroup.model.HoldingConcatenation;
import com.fdmgroup.model.Share;
import com.fdmgroup.model.User;
import com.fdmgroup.validation.HoldingException;
import com.fdmgroup.validation.ShareException;

public class HoldingDAOTest {
	
	HoldingDAO holdingDAO;
	private EntityManager entityManager;
	private EntityManagerFactory entityManagerFactory;
	EntityTransaction entityTransaction;
	@Before
	public void before() {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		holdingDAO = new HoldingDAO(entityManager);
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
	}
	@After
	public void after(){
		entityTransaction.commit();
	}
	@Test(expected=HoldingException.class)
	public void createNewHolding_ReturnsErrorWhenNoInput() throws HoldingException {
		holdingDAO.editHoldings(null, null, 0, 0);
	}
	
	@Test
	public void createNewHolding_createsNewHoldingInDatabaseWhenCalled() throws HoldingException {
		Holding holding = holdingDAO.editHoldings(entityManager.find(User.class, 1), entityManager.find(Share.class, 2), 100, 0);
		HoldingConcatenation holdingConcatenation = new HoldingConcatenation(holding.getUser_id().getUserId(),holding.getShare_id().getShare_id());
		int actual = entityManager.find(Holding.class, holdingConcatenation).getQuantity();
		holdingDAO.editHoldings(entityManager.find(User.class, 1), entityManager.find(Share.class, 2), -100, 0);
		assertEquals(actual,100);
	}
	@Test
	public void createNewHolding_addsToPreExistingHoldingsIfPossible() throws HoldingException {
		holdingDAO.editHoldings(entityManager.find(User.class, 1), entityManager.find(Share.class, 1), 100, 0);
		holdingDAO.editHoldings(entityManager.find(User.class, 1), entityManager.find(Share.class, 1), 100, 0);
		HoldingConcatenation holdingConcatenation = new HoldingConcatenation(entityManager.find(User.class, 1).getUserId(),entityManager.find(Share.class, 1).getShare_id());
		int actual = entityManager.find(Holding.class, holdingConcatenation).getQuantity();
		holdingDAO.editHoldings(entityManager.find(User.class, 1), entityManager.find(Share.class, 1), -200, 0);
		assertEquals(200,actual);
	}
	@Test(expected=HoldingException.class)
	public void createNewHolding_ReturnsErrorWhenNullUserInput() throws HoldingException {
		holdingDAO.editHoldings(null, entityManager.find(Share.class, 1), 100, 0);
	}
	@Test(expected=HoldingException.class)
	public void createNewHolding_ReturnsErrorWhenCurrentHoldingsWouldGoBelowZero() throws HoldingException {
		holdingDAO.editHoldings(null, entityManager.find(Share.class, 1), -10000000, 0);
	}
	@Test(expected=HoldingException.class)
	public void createNewHolding_ReturnsErrorWhenInvalidUserInput() throws HoldingException {
		holdingDAO.editHoldings(entityManager.find(User.class, -1), entityManager.find(Share.class, 1), 100, 0);
	}
	@Test(expected=HoldingException.class)
	public void createNewHolding_ReturnsNullWhenNullShareInput() throws HoldingException {
		holdingDAO.editHoldings(entityManager.find(User.class, 1), null, 100, 0);
	}
	@Test(expected=HoldingException.class)
	public void createNewHolding_ReturnsErrorWhenInvalidShareInput() throws HoldingException {
		holdingDAO.editHoldings(entityManager.find(User.class, 1), entityManager.find(Share.class, -1), 100, 0);
	}
	@Test(expected=HoldingException.class)
	public void createNewHolding_ReturnsNullWhen0QuantityInput() throws HoldingException {
		holdingDAO.editHoldings(entityManager.find(User.class, 1), entityManager.find(Share.class, 1), 0, 0);
	}
	
	@Test
	public void listOfAllCurrentHoldingsByUser_ReturnsListOfHoldingsForUser_WhenValidInformationEntered() throws HoldingException {
		holdingDAO.editHoldings(entityManager.find(User.class, 1), entityManager.find(Share.class, 2), 100, 0);
		holdingDAO.editHoldings(entityManager.find(User.class, 1), entityManager.find(Share.class, 2), -100, 0);
		holdingDAO.editHoldings(entityManager.find(User.class, 1), entityManager.find(Share.class, 1), 100, 0);
		holdingDAO.editHoldings(entityManager.find(User.class, 1), entityManager.find(Share.class, 1), 100, 0);
		holdingDAO.editHoldings(entityManager.find(User.class, 1), entityManager.find(Share.class, 1), -200, 0);
		List<Holding> actual =holdingDAO.listOfAllCurrentHoldingsByUser(entityManager.find(User.class, 1));
		assertEquals(2,actual.size());
	}
	@Test(expected=HoldingException.class)
	public void listOfAllCurrentHoldingsByUser_ThrowsHoldingException_WhenInalidInformationEntered() throws HoldingException {
		holdingDAO.listOfAllCurrentHoldingsByUser(entityManager.find(User.class, -1));
	}
	@Test(expected=HoldingException.class)
	public void listOfAllCurrentHoldingsByUser_ThrowsHoldingException_WhenNullUserEntered() throws HoldingException {
		holdingDAO.listOfAllCurrentHoldingsByUser(null);
	}
	
}
