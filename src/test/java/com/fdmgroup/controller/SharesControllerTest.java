package com.fdmgroup.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import com.fdmgroup.model.Basket;
import com.fdmgroup.model.Broker;
import com.fdmgroup.model.BrokerStockExchange;
import com.fdmgroup.model.Share;
import com.fdmgroup.model.StockExchange;
import com.fdmgroup.model.User;
import com.fdmgroup.model.Wallet;
import com.fdmgroup.model.DAO.BrokerStockExchangeDAO;
import com.fdmgroup.validation.HoldingException;
import com.fdmgroup.validation.ShareException;
import com.fdmgroup.validation.SharePriceException;

public class SharesControllerTest {
	
	SharesController sharesController = new SharesController();
	HttpServletRequest request = mock(HttpServletRequest.class);
	HttpSession session = mock(HttpSession.class);
	Model model = mock(Model.class);
	Basket basket = mock(Basket.class);
	private User userExistsInDatabase = new User("ml404", "password", "Matt", "Layton", "ml404@kent.ac.uk", "Admin");
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	
	@Before
	public void before(){
		when(request.getSession()).thenReturn(session);
	}
	
	@Test
	public void test_setup_returns_session_whenCalled(){
		HttpSession actual=sharesController.setup(request);
		assertEquals(session,actual);
	}
	
	@Test
	public void test_SharesHandler_Returns_sharesPage_when_called(){
		String actual = sharesController.sharesHandler(model, request);
		assertEquals("sharesPage",actual);
	}
	
	@Test
	public void test_shareInfHandler_Returns_sharesPage_when_calledWithNoWhens() throws ShareException, SharePriceException{
		String actual = sharesController.shareInfHandler(model, request);
		assertEquals("sharesPage",actual);
	}
	
	@Test
	public void test_shareInfHandler_Returns_sharesPage_when_calledWithWhens() throws ShareException, SharePriceException{
		when(request.getParameter("share_id")).thenReturn("1");
		when(request.getParameter("selection")).thenReturn("1");
		String actual = sharesController.shareInfHandler(model, request);
		assertEquals("shareInf",actual);
	}
	
	@Test
	public void test_shareInfHandler_CallsModelSetAttributeFourTimes() throws ShareException, SharePriceException{
		when(request.getParameter("share_id")).thenReturn("1");
		when(request.getParameter("selection")).thenReturn("1");
		sharesController.shareInfHandler(model, request);
		verify(model, times(5)).addAttribute(anyString(), anyObject());
	}
	
	@Test
	public void test_buyHandler_ReturnsCorrectString() throws ShareException, SharePriceException, HoldingException{
		userExistsInDatabase.setUserId(3);
		when(session.getAttribute("userInf")).thenReturn(userExistsInDatabase);
		when(request.getParameter("share_id")).thenReturn("1");
		when(basket.getQuantity()).thenReturn(420);
		when(session.getAttribute("wallet")).thenReturn(new Wallet());
		String actual = sharesController.buyHandler(model, request, basket);
		assertEquals("accountPage", actual);

	}
	
	@Test
	public void test_buyHandler_CallsModelAttributeTwice() throws ShareException, SharePriceException, HoldingException{
		userExistsInDatabase.setUserId(3);
		when(session.getAttribute("userInf")).thenReturn(userExistsInDatabase);
		when(request.getParameter("share_id")).thenReturn("1");
		when(basket.getQuantity()).thenReturn(420);
		when(session.getAttribute("wallet")).thenReturn(new Wallet());
		sharesController.buyHandler(model, request, basket);
		verify(model, times(6)).addAttribute(anyString(), anyObject());
	}
	
	
	@Test
	public void test_buyHandler_withNoOneLoggedIn_ReturnsLoginPage() throws ShareException, SharePriceException, HoldingException{
		when(session.getAttribute("userInf")).thenReturn(new User("Login", "password", "Matt", "Layton", "ml404@kent.ac.uk", "Admin"));
		String actual = sharesController.buyHandler(model, request, basket);
		assertEquals("loginPage", actual);
	}
	
	@Test
	public void test_sellHandler_CallsModelAttributeTwice() throws ShareException, SharePriceException, HoldingException{
		userExistsInDatabase.setUserId(3);
		when(session.getAttribute("userInf")).thenReturn(userExistsInDatabase);
		when(request.getParameter("share_id")).thenReturn("1");
		when(basket.getQuantity()).thenReturn(420);
		when(session.getAttribute("wallet")).thenReturn(new Wallet());
		sharesController.sellHandler(model, request, basket);
		verify(model, times(7)).addAttribute(anyString(), anyObject());
		sharesController.buyHandler(model, request, basket);
		
	}
	
//	@Test
//	public void test_brokerGeneratorHandler_returnsBrokerWhenNoAvailableBroker() throws ShareException, SharePriceException, HoldingException{
//		
//		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
//		entityManager = entityManagerFactory.createEntityManager();
//		Share share = entityManager.find(Share.class, 1);
//		
//		System.out.println(share.getStockExchange().getStock_ex_id());
//		
//		BrokerStockExchangeDAO brokerExchangeDAO =  new BrokerStockExchangeDAO(entityManager);
//		
//		BrokerStockExchange brokerStockExchange = mock(BrokerStockExchange.class);
//		
//		sharesController.brokerGeneratorHandler(share);
//		verify(brokerExchangeDAO, times(1)).addBrokerStockExchange(brokerStockExchange);
//
//		
//	}
	
	
}
