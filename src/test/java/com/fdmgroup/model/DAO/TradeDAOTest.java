package com.fdmgroup.model.DAO;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.model.Broker;
import com.fdmgroup.model.Share;
import com.fdmgroup.model.StockExchange;
import com.fdmgroup.model.Trade;
import com.fdmgroup.model.User;
import com.fdmgroup.validation.HoldingException;

public class TradeDAOTest {
	private EntityManager entityManager;
	private EntityManagerFactory entityManagerFactory;
	private EntityTransaction entityTransaction;
	private TradeDAO tradeDAO;
	private List<Trade> trades;
	private Trade trade;
	private String jPAName = "groupproject";
	private Date date;

	@Before
	public void setup() {
		entityManagerFactory = Persistence.createEntityManagerFactory(jPAName);
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
		tradeDAO = new TradeDAO(entityManager);
	}

	@Test
	public void test_SuccessfulConnectionMade() {
		// ARRANGE
		// ACT
		// ASSERT
		assertNotNull(entityTransaction);
	}

	
	@Test
	public void test_listTradeByShareId_ReturnsNull_WhenNullInput() {
		trades = tradeDAO.listTradesByShareId(0);
		assertNull(trades);
	}

	@Test
	public void test_listTradeByShareId_ReturnsCorrectSizeList_WhenValidInput() {
		trades = tradeDAO.listTradesByShareId(1);
		assertEquals(21, trades.size());
	}

	@Test
	public void test_listTradeBybrokerId_ReturnsNull_WhenNullInput() {
		trades = tradeDAO.listTradesByBrokerId(0);
		assertNull(trades);
	}

	@Test
	public void test_listTradeByBrokerId_ReturnsCorrectSizeList_WhenValidInput() {
		trades = tradeDAO.listTradesByBrokerId(1);
		assertEquals(28, trades.size());
	}

	@Test
	public void test_listTradeByStockExId_ReturnsNull_WhenNullInput() {
		trades = tradeDAO.listTradesByStockExId(0);
		assertNull(trades);
	}

	@Test
	public void test_listTradeByStockExId_ReturnsCorrectSizeList_WhenValidInput() {
		trades = tradeDAO.listTradesByStockExId(1);
		assertEquals(26, trades.size());
	}

	@Test
	public void test_listTradeByShareAmount_ReturnsNull_WhenNullInput() {
		trades = tradeDAO.listTradesByShareAmount(0);
		assertNull(trades);
	}

	@Test
	public void test_listTradeByShareAmount_ReturnsCorrectSizeList_WhenValidInput() {
		trades = tradeDAO.listTradesByShareAmount(10);
		assertEquals(2, trades.size());
	}
	
	@Test
	public void test_listTradeByShareAmountWithTwoParams_ReturnsCorrectSizeList_WhenNullInput() {
		trades = tradeDAO.listTradesByShareAmount(10, null);
		assertEquals(2, trades.size());
	}
	
	@Test
	public void test_listTradeByShareAmountWithTwoParams_ReturnsCorrectSizeList_WhenNoSymbolInput() {
		trades = tradeDAO.listTradesByShareAmount(10, "");
		assertEquals(2, trades.size());
	}
	
	@Test
	public void test_listTradeByShareAmountWithTwoParams_ReturnsCorrectSizeList_WhenValidInputWithGreaterThan() {
		trades = tradeDAO.listTradesByShareAmount(10000, ">");
		assertEquals(16, trades.size());
	}
	
	@Test
	public void test_listTradeByShareAmountWithTwoParams_ReturnsCorrectSizeList_WhenValidInputWithLessThan() {
		trades = tradeDAO.listTradesByShareAmount(10000, "<");
		assertEquals(39, trades.size());
	}
	
	@Test
	public void test_listTradeByPriceTotal_ReturnsNull_WhenNullInput() {
		trades = tradeDAO.listTradesByPriceTotal(0);
		assertNull(trades);
	}

	@Test
	public void test_listTradeByPriceTotal_ReturnsCorrectSizeList_WhenValidInput() {
		trades = tradeDAO.listTradesByPriceTotal(1200);
		assertEquals(2, trades.size());
	}
	
	@Test
	public void test_listTradeByPriceTotalWithTwoParams_ReturnsCorrectSizeList_WhenValidInputWithGreaterThan() {
		trades = tradeDAO.listTradesByPriceTotal(1200000, "<");
		assertEquals(34, trades.size());
	}
	
	@Test
	public void test_listTradeByPriceTotalWithTwoParams_ReturnsCorrectSizeList_WhenValidInputWithLessThan() {
		trades = tradeDAO.listTradesByPriceTotal(1200000, ">");
		assertEquals(21, trades.size());
	}
	
	@Test
	public void test_listTradeByPriceTotalWithTwoParams_ReturnsCorrectSizeList_WhenNullInput() {
		trades = tradeDAO.listTradesByPriceTotal(1200, null);
		assertEquals(2, trades.size());
	}
	
	@Test
	public void test_listTradeByPriceTotalWithTwoParams_ReturnsCorrectSizeList_WhenNoSymbolInput() {
		trades = tradeDAO.listTradesByPriceTotal(1200, "");
		assertEquals(2, trades.size());
	}

	@Test
	public void test_listTradeByYear_ReturnsNull_WhenNoYearEntered() {
		trades = tradeDAO.listTradesByYear(0);
		assertNull(trades);
	}

	@Test
	public void test_listTradeByYear_ReturnsCorrectSizeList_WhenValidInput() {
		trades = tradeDAO.listTradesByYear(2017);
		assertEquals(6, trades.size());
	}

	@Test
	public void test_listTradeByMonth_ReturnsNull_WhenNoYearEntered() {
		trades = tradeDAO.listTradesByMonth(null);
		assertNull(trades);
	}

	@Test
	public void test_listTradeByMonth_ReturnsCorrectSizeList_WhenValidInput() {
		trades = tradeDAO.listTradesByMonth("Jan");
		assertEquals(6, trades.size());
	}

	@Test
	public void test_listTradeByLast2Weeks_ReturnsCorrectSizeList() {
		trades = tradeDAO.listTradesByLast2Weeks();
		assertEquals(7, trades.size());
	}
	
	

	@Test
	public void test_listTradeByLastWeek_ReturnsCorrectSizeList() {
		trades = tradeDAO.listTradesByLastWeek();
		assertEquals(5, trades.size());
	}

	@Test
	public void test_listTradeByToday_ReturnsCorrectSizeList() {
		trades = tradeDAO.listTradesByToday();
		assertEquals(3, trades.size());
	}

	@Test
	public void test_listTradeByTwoParameters_ReturnsNull_WhenNullInput() {
		trades = tradeDAO.listTradesByTwoParameters(null, null, 0, 0);
		assertNull(trades);
	}

	@Test
	public void test_listTradeByTwoParameters_ReturnsNull_WhenZeroInput() {
		trades = tradeDAO.listTradesByTwoParameters("String", "String", 0, 0);
		assertNull(trades);
	}
	
	@Test
	public void test_listTradeByTwoParameters_ReturnsEmptyList_WhenInvalidInput() {
		trades = tradeDAO.listTradesByTwoParameters("share_id", "String", 1, 5);
		assertEquals(null, trades);
	}
	
	@Test
	public void test_listTradeByTwoParameters_ReturnsCorrectSizeList_WhenValidInput() {
		
		trades = tradeDAO.listTradesByTwoParameters("share_id", "broker_id", 1, 1);
		assertEquals(13, trades.size());
		
		
	}

	@Test
	public void testThatCreateTradeReturnsNullWhenParametersnull() throws HoldingException {
		// ARRANGE
		// ACT
		entityTransaction.begin();
		trade = tradeDAO.addTrade(null);
		entityTransaction.commit();
		
		// Assert
		assertNull(trade);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testThatCreateTradeReturnsTradeWhenValidParametersAreEntered() throws HoldingException {
		// ARRANGE
		// ACT
		HoldingDAO holdingDAO = new HoldingDAO(entityManager);
		date = new Date(2016, 11, 11);
		trade = new Trade(entityManager.find(Share.class, 1), entityManager.find(Broker.class, 1), entityManager.find(User.class,1),entityManager.find(StockExchange.class, 1), date, 1, 1, "buy");
		trade.setTrade_id(88);		
		entityTransaction.begin();
		Trade newTrade = tradeDAO.addTrade(trade);	
		entityTransaction.commit();
		Share share = newTrade.getShare_id();
		int shareId = share.getId();
		int expected = tradeDAO.listTradesByShareId(shareId).size();
		entityTransaction.begin();
		tradeDAO.removeTrade(newTrade.getTrade_id());
		holdingDAO.editHoldings(entityManager.find(User.class, 1), entityManager.find(Share.class, 1), -1, 0);
		entityTransaction.commit();
		int actual = tradeDAO.listTradesByShareId(shareId).size();
		// Assert
		assertEquals(expected -1, actual);
	}


//	@Test
//	public void test_removeTrade_ReturnsNullWhenNullEntered() {
//		entityTransaction.begin();
//		Trade actual = tradeDAO.removeTrade(0);
//		entityTransaction.commit();
//		// Assert
//		assertNull(actual);
//	}

 	@SuppressWarnings("deprecation")
	@Test
	public void test_removeTradeWithValidParameters_RemovesTradeFromDatabase() throws HoldingException {	
		HoldingDAO holdingDAO = new HoldingDAO(entityManager);
		date = new Date(2016, 11, 11);
		trade = new Trade(entityManager.find(Share.class, 1), entityManager.find(Broker.class, 1), entityManager.find(User.class,1), entityManager.find(StockExchange.class, 1), date, 1, 1, "buy");
		trade.setTrade_id(89);
		entityTransaction.begin();
		Trade addedTrade = tradeDAO.addTrade(trade);
		entityTransaction.commit();
		int expected = tradeDAO.listTradesByShareId(1).size();
		entityTransaction.begin();
		tradeDAO.removeTrade(addedTrade.getTrade_id());
		holdingDAO.editHoldings(entityManager.find(User.class, 1), entityManager.find(Share.class, 1), -1, 0);
		entityTransaction.commit();
		int result = tradeDAO.listTradesByShareId(1).size();
		//assert
		assertEquals(expected -1, result);
		
	}

	@Test
	public void test_findTradeWithNull_ReturnsNull() {
		// Arrange
		// Act
		trade = tradeDAO.findTrade(0);
		// Assert
		assertNull(trade);
	}
	
	@Test
	public void test_findTradeWithinvalidNumber_ReturnsNull() {
		// Arrange
		// Act
		trade = tradeDAO.findTrade(100000000);
		// Assert
		assertNull(trade);
	}
	
	@Test
	public void test_findTradeWithValidNumber_ReturnsCompany() {
		// Arrange
		// Act
		trade = tradeDAO.findTrade(1);
		// Assert
		assertNotNull(trade);
	}
	
//	@Test
//	public void test_listTradesOfShareUserOwns_ReturnsNull_WhenZeroInput() {
//		trades = tradeDAO.listTradesOfShareUserOwns(0);
//		assertNull(trades);
//	}
//	
//	@Test
//	public void test_listTradesOfShareUserOwns_ReturnsNull_WhenInvalidIdInput() {
//		trades = tradeDAO.listTradesOfShareUserOwns(40);
//		assertNull(trades);
//	}
//	
//	@Test
//	public void test_listTradesOfShareUserOwns_ReturnsCorrectSizeList_WhenValidInput() {
//		trades = tradeDAO.listTradesOfShareUserOwns(7);	
//		assertEquals(1, trades.size());
//	}
	
	
	@After
	public void after() {
		entityManager.close();
		entityManagerFactory.close();
	}
}
