package com.fdmgroup.model.DAO;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.*;

import com.fdmgroup.model.Place;
import com.fdmgroup.model.StockExchange;
import com.fdmgroup.validation.InvalidStockExchangeException;

public class StockExchangeDAOTest {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private StockExchangeDAO stockExchangeDAO;
	
	@Before
	public void before(){
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		stockExchangeDAO = new StockExchangeDAO(entityManager);
	}
	
	//find by ID
	@Test
	public void test_findStockExchangeName_StockExchangeID2() {
		StockExchange actualStockExchange = new StockExchange();
		actualStockExchange = stockExchangeDAO.findStockExchange(2);
		
		String actualName = actualStockExchange.getName();
		String expectedName = "New York Stock Exchange";
		
		assertEquals(expectedName, actualName);
	}
		
	//find by name
	@Test
	public void test_findStockExchangeID_StockExchangeNameEuronextParis(){
		StockExchange actualStockExchange = new StockExchange();
		actualStockExchange = stockExchangeDAO.findStockExchangeByName("Euronext Paris");
		
		int actualID = actualStockExchange.getStock_ex_id();
		int expectedID = 4;
		
		assertEquals(expectedID, actualID);
	}
	
	//find by symbol
	@Test
	public void test_findStockExchangeID_StockExchangeSymbolEP(){
		StockExchange actualStockExchange = new StockExchange();
		actualStockExchange = stockExchangeDAO.findStockExchangeBySymbol("EP");
		
		int actualID = actualStockExchange.getStock_ex_id();
		int expectedID = 4;
		
		assertEquals(expectedID, actualID);
	}
	
	//Update name & symbol
	@Test
	public void test_UpdateLondonStockExchangeToLondonPrimaryStockExchange(){		
		stockExchangeDAO.updateStockExchange(1, "London Primary Stock Exchange", "LPSE");
		StockExchange stockExchange = new StockExchange();
		stockExchange = stockExchangeDAO.findStockExchange(1);
		
		String actualName = stockExchange.getName();
		String expectedName = "London Primary Stock Exchange";
		
		assertEquals(expectedName, actualName);
	}
	
	//@Test
	public void test_CheckIfOriginalSizeOfListOfStockExchangesIsFive(){		
		StockExchange[] listOfStockExchanges = stockExchangeDAO.ListOfStockExchanges();
		
		int expectedSize = 5;
		int actualSize = listOfStockExchanges.length;
		
		assertEquals(expectedSize, actualSize);
	}
	
	//Remove
	//@Test
	public void test_CheckIfSizeOfListOfStockExchangesDecreased_RemoveOneStockExchange() throws InvalidStockExchangeException {
		
		
		StockExchange stockExchange = stockExchangeDAO.createStockExchange("London Secondary Stock Exchange", "LSSE", entityManager.find(Place.class, 1));		
		int expected = stockExchangeDAO.ListOfStockExchanges().length;
		
		stockExchangeDAO.removeStockExchange(stockExchange.getStock_ex_id());
		
		
		
		assertEquals(expected, expected - 1);
	}
	
	//Add
	@Test
	public void test_CheckIfSizeOfListOfStockExchangesIncreasedByOne_AddNewStockExchange() throws InvalidStockExchangeException{
		StockExchange[] listOfStockExchanges = stockExchangeDAO.ListOfStockExchanges();
		int expectedSize = listOfStockExchanges.length;
		StockExchange stockExchange = stockExchangeDAO.createStockExchange("London Secondary Stock Exchange", "LSSE", entityManager.find(Place.class, 1));		
		StockExchange[] newListOfStockExchanges = stockExchangeDAO.ListOfStockExchanges();
		int actualSize = newListOfStockExchanges.length;
		assertEquals(expectedSize, actualSize-1);
		stockExchangeDAO.removeStockExchange(stockExchange.getStock_ex_id());
	}
	
	@After
	public void after() {
		entityManager.close();
		entityManagerFactory.close();
	}
}
