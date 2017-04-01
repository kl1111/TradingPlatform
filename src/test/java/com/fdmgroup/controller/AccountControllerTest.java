package com.fdmgroup.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import com.fdmgroup.model.PasswordUpdate;
import com.fdmgroup.model.User;

public class AccountControllerTest {
	
	HomeController homeController = new HomeController();
	HttpServletRequest request = mock(HttpServletRequest.class);
	HttpSession session = mock(HttpSession.class);
	Model model = mock(Model.class);
	PasswordUpdate password = mock(PasswordUpdate.class);
	private User userExistsInDatabase = new User("ml404", "password", "Matt", "Layton", "ml404@kent.ac.uk", "Admin");
	
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
	
	@Test
	public void test_tradeHistory_CallsModelAttributeThrice(){
		//ARRANGE
		//ACT
		userExistsInDatabase.setUserId(3);
		AccountController accountController = new AccountController();
		when(session.getAttribute("userInf")).thenReturn(userExistsInDatabase);
		accountController.tradeHistory(model, request);
		//ASSERT
		verify(model, times(3)).addAttribute(anyString(), anyObject());
		
	}
	
	@Test
	public void test_passwordUpdate_CallsModelAttribute6timesWhenIncorrectPassword(){
		//ARRANGE
		//ACT
		userExistsInDatabase.setUserId(3);
		AccountController accountController = new AccountController();
		when(session.getAttribute("userInf")).thenReturn(userExistsInDatabase);	
		accountController.passwordUpdate(model, request, password);
		//ASSERT
		verify(model, times(6)).addAttribute(anyString(), anyObject());
	}
	
	@Test
	public void test_passwordUpdate_CallsModelAttribute6timesWhenPasswordDontMatch(){
		//ARRANGE
		//ACT
		userExistsInDatabase.setUserId(3);
		AccountController accountController = new AccountController();
		when(session.getAttribute("userInf")).thenReturn(userExistsInDatabase);	
		when(password.getPasswordold()).thenReturn("password");	
		when(password.getPasswordnew()).thenReturn("password");	
		accountController.passwordUpdate(model, request, password);
		//ASSERT
		verify(model, times(6)).addAttribute(anyString(), anyObject());
	}
	
	@Test
	public void test_passwordUpdate_CallsModelAttribute6timesWhenPasswordMatch(){
		//ARRANGE
		//ACT
		userExistsInDatabase.setUserId(3);
		AccountController accountController = new AccountController();
		when(session.getAttribute("userInf")).thenReturn(userExistsInDatabase);	
		when(password.getPasswordold()).thenReturn("password");	
		when(password.getConfirm()).thenReturn("password");
		when(password.getPasswordnew()).thenReturn("password");	
		
		accountController.passwordUpdate(model, request, password);
		//ASSERT
		verify(model, times(6)).addAttribute(anyString(), anyObject());
	}
}
