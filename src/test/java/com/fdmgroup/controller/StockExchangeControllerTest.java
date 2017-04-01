package com.fdmgroup.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import com.fdmgroup.model.DAO.BrokerDAO;
import com.fdmgroup.validation.ShareException;

public class StockExchangeControllerTest {
	
	StockExchangeController stockExchangeController = new StockExchangeController();
	HttpServletRequest request = mock(HttpServletRequest.class);
	HttpSession session = mock(HttpSession.class);
	Model model = mock(Model.class);
	
	
	@Before
	public void before(){
		when(request.getSession()).thenReturn(session);
	}
	
	@Test
	public void test_setup_returns_session_whenCalled(){
		HttpSession actual=stockExchangeController.setup(request);
		assertEquals(session,actual);
	}
	
	@Test
	public void test_stockInfoHandler_Returns_sharesPage_withNoValidShareID() throws ShareException{
		String actual = stockExchangeController.stockInfoHandler(model, request);
		assertEquals("sharesPage",actual);
	}
	
	@Test
	public void test_stockInfoHandler_Returns_stock_exchange_withValidShareID() throws ShareException{
		when(request.getParameter("share_id")).thenReturn("1");
		String actual = stockExchangeController.stockInfoHandler(model, request);
		assertEquals("stock_exchange",actual);
	}
	

	
	
	
	
}
