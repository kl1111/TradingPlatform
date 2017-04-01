package com.fdmgroup.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

public class HomeControllerTest {
	
	HomeController homeController = new HomeController();
	HttpServletRequest request = mock(HttpServletRequest.class);
	HttpSession session = mock(HttpSession.class);
	Model model = mock(Model.class);
	
	
	@Before
	public void before(){
		when(request.getSession()).thenReturn(session);
	}
	
	@Test
	public void test_setup_returns_session_whenCalled(){
		HttpSession actual=homeController.setup(request);
		assertEquals(session,actual);
	}
	
	@Test
	public void test_HomepageHandler_Returns_homePage_when_called(){
		String actual = homeController.HomepageHandler(model, request);
		assertEquals("homePage",actual);
	}
	
	@Test
	public void test_HomepageRouter_Returns_homePage_when_called(){
		String actual = homeController.homeRouter(model, request);
		assertEquals("homePage",actual);
	}
	
	
	
	
}
