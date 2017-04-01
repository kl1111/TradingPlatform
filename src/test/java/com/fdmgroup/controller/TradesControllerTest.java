package com.fdmgroup.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import com.fdmgroup.model.User;
import com.fdmgroup.model.DAO.BrokerDAO;
import com.fdmgroup.model.DAO.TradeDAO;
import com.fdmgroup.validation.HoldingException;
import com.fdmgroup.validation.ShareException;
import com.fdmgroup.validation.SharePriceException;

public class TradesControllerTest {
	
	TradesController tradesController = new TradesController();
	HttpServletRequest request = mock(HttpServletRequest.class);
	HttpSession session = mock(HttpSession.class);
	Model model = mock(Model.class);
	TradeDAO tradeDAO;
	
	
	@Before
	public void before(){
		when(request.getSession()).thenReturn(session);
	}
	
	@Test
	public void test_setup_returns_session_whenCalled(){
		HttpSession actual=tradesController.setup(request);
		assertEquals(session,actual);
	}
	
	@Test
	public void test_listTrades_ReturnsAccountPageAndAddAttributeIsCalledCorrectly_WhenNullInput(){
		when(request.getParameter("selection")).thenReturn(null);
		String actual = tradesController.listTrades(model, request);
		verify(model, times(1)).addAttribute("message", "No Input");
		assertEquals("accountPage",actual);
	}
	
	@Test
	public void test_listTrades_ReturnsAccountPageAndAddAttributeIsCalledCorrectly_WhenCase1Input(){
		when(request.getParameter("selection")).thenReturn("1");
		when(request.getParameter("param_id")).thenReturn("1");
		String actual = tradesController.listTrades(model, request);
		verify(model, times(1)).addAttribute("message", "Listing Trades by Shares");
		verify(model, times(1)).addAttribute(eq("listOfTrades"), anyObject());
		assertEquals("accountPage",actual);
	}
	
	@Test
	public void test_listTrades_ReturnsAccountPageAndAddAttributeIsCalledCorrectly_WhenCase2Input(){
		when(request.getParameter("selection")).thenReturn("2");
		when(request.getParameter("param_id")).thenReturn("1");
		String actual = tradesController.listTrades(model, request);
		verify(model, times(1)).addAttribute("message", "Listing Trades by Brokers");
		verify(model, times(1)).addAttribute(eq("listOfTrades"), anyObject());
		assertEquals("accountPage",actual);
	}
	
	@Test
	public void test_listTrades_ReturnsAccountPageAndAddAttributeIsCalledCorrectly_WhenCase3Input(){
		when(request.getParameter("selection")).thenReturn("3");
		when(request.getParameter("param_id")).thenReturn("1");
		String actual = tradesController.listTrades(model, request);
		verify(model, times(1)).addAttribute("message", "Listing Trades by Users");
		verify(model, times(1)).addAttribute(eq("listOfTrades"), anyObject());
		assertEquals("accountPage",actual);
	}
	
	@Test
	public void test_listTrades_ReturnsAccountPageAndAddAttributeIsCalledCorrectly_WhenCase4Input(){
		when(request.getParameter("selection")).thenReturn("4");
		when(request.getParameter("param_id")).thenReturn("1");
		String actual = tradesController.listTrades(model, request);
		verify(model, times(1)).addAttribute("message", "Listing Trades by stock_ex_id");
		verify(model, times(1)).addAttribute(eq("listOfTrades"), anyObject());
		assertEquals("accountPage",actual);
	}
	
	@Test
	public void test_listTrades_ReturnsAccountPageAndAddAttributeIsCalledCorrectly_WhenCase5Input(){
		when(request.getParameter("selection")).thenReturn("5");
		when(request.getParameter("param_id")).thenReturn("1");
		String actual = tradesController.listTrades(model, request);
		verify(model, times(1)).addAttribute("message", "Listing Trades by priceTotal");
		verify(model, times(1)).addAttribute(eq("listOfTrades"), anyObject());
		assertEquals("accountPage",actual);
	}
	
	@Test
	public void test_listTrades_ReturnsAccountPageAndAddAttributeIsCalledCorrectly_WhenCase6Input(){
		when(request.getParameter("selection")).thenReturn("6");
		when(request.getParameter("param_id")).thenReturn("1");
		String actual = tradesController.listTrades(model, request);
		verify(model, times(1)).addAttribute("message", "Listing Trades by Year");
		verify(model, times(1)).addAttribute(eq("listOfTrades"), anyObject());
		assertEquals("accountPage",actual);
	}
	
	@Test
	public void test_listTrades_ReturnsAccountPageAndAddAttributeIsCalledCorrectly_WhenCase7Input(){
		when(request.getParameter("selection")).thenReturn("7");
		when(request.getParameter("param_id")).thenReturn("1");
		String actual = tradesController.listTrades(model, request);
		verify(model, times(1)).addAttribute("message", "Listing Trades by Month");
		verify(model, times(1)).addAttribute(eq("listOfTrades"), anyObject());
		assertEquals("accountPage",actual);
	}
	
	@Test
	public void test_listTrades_ReturnsAccountPageAndAddAttributeIsCalledCorrectly_WhenCase8Input(){
		when(request.getParameter("selection")).thenReturn("8");
		when(request.getParameter("param_id")).thenReturn("1");
		String actual = tradesController.listTrades(model, request);
		verify(model, times(1)).addAttribute("message", "Listing Trades by last 2 Weeks");
		verify(model, times(1)).addAttribute(eq("listOfTrades"), anyObject());
		assertEquals("accountPage",actual);
	}
	
	@Test
	public void test_listTrades_ReturnsAccountPageAndAddAttributeIsCalledCorrectly_WhenCase9Input(){
		when(request.getParameter("selection")).thenReturn("9");
		when(request.getParameter("param_id")).thenReturn("1");
		String actual = tradesController.listTrades(model, request);
		verify(model, times(1)).addAttribute("message", "Listing Trades in last Week");
		verify(model, times(1)).addAttribute(eq("listOfTrades"), anyObject());
		assertEquals("accountPage",actual);
	}
	
	@Test
	public void test_listTrades_ReturnsAccountPageAndAddAttributeIsCalledCorrectly_WhenCase10Input(){
		when(request.getParameter("selection")).thenReturn("10");
		when(request.getParameter("param_id")).thenReturn("1");
		String actual = tradesController.listTrades(model, request);
		verify(model, times(1)).addAttribute("message", "Listing Trades made today");
		verify(model, times(1)).addAttribute(eq("listOfTrades"), anyObject());
		assertEquals("accountPage",actual);
	}
	
	@Test
	public void test_listTrades_ReturnsAccountPageAndAddAttributeIsCalledCorrectly_WhenCase11Input(){
		when(request.getParameter("selection")).thenReturn("11");
		when(request.getParameter("param_id")).thenReturn("1");
		String actual = tradesController.listTrades(model, request);
		verify(model, times(1)).addAttribute("message", "Found Trade");
		assertEquals("accountPage",actual);
	}
	
	@Test
	public void test_listTrades_ReturnsAccountPageAndAddAttributeIsCalledCorrectly_WhenCase12Input(){
		when(request.getParameter("selection")).thenReturn("12");
		when(request.getParameter("param_id")).thenReturn("1");
		String actual = tradesController.listTrades(model, request);
		verify(model, times(1)).addAttribute("message", "No Selection");
		assertEquals("accountPage",actual);
	}
	
	
	

}
