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

public class SearchControllerTest {
	
	SearchController searchController = new SearchController();
	HttpServletRequest request = mock(HttpServletRequest.class);
	HttpSession session = mock(HttpSession.class);
	Model model = mock(Model.class);
	
	
	@Before
	public void before(){
		when(request.getSession()).thenReturn(session);
	}
	
	@Test
	public void test_setup_returns_session_whenCalled(){
		HttpSession actual=searchController.setup(request);
		assertEquals(session,actual);
	}
	
	@Test
	public void test_SubmitSearch_Returns_sharesPage_when_called() throws Exception{
		String actual = searchController.submitSearch(model, "", request);
		assertEquals("sharesPage",actual);
	}
	
	
	
	
	
	
}
